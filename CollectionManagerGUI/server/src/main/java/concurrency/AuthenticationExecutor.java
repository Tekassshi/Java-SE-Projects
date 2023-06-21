package concurrency;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.LogIn;
import commands.SignUp;
import connection.ClientRequest;
import connection.SerializationManager;
import connection.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import managers.CollectionManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import util.DatabaseManager;
import util.LoggerProvider;

public class AuthenticationExecutor implements Runnable{

    private DatabaseManager dbManager;
    private CollectionManager collectionManager;

    private Socket socket;
    private Logger logger;

    public AuthenticationExecutor(Socket socket, CollectionManager collectionManager, DatabaseManager dbManager){
        this.dbManager = dbManager;
        this.socket = socket;
        this.logger = LoggerProvider.getLogger();
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("auth thread");
        try {
            ExecutorService fixedThreadPool = ThreadPoolFactory.getFixedThreadPool();
            ForkJoinPool forkJoinPool = ThreadPoolFactory.getForkJoinSendersPool();

            String username;
            String password;
            String[] tokenData;

            while (true) {

                InputStream is = socket.getInputStream();
                byte[] arr = new byte[10000];
                ClientRequest request;
                Object obj;

                try {
                    is.read(arr);
                    logger.info("Reading authorization data");

                    request = (ClientRequest) SerializationManager.deserialize(arr);
                    obj = request.getObj();
                }
                catch (IOException e){
                    logger.warn("Force shutdown: " + socket.getLocalAddress() + "\n");
                    socket.close();
                    return;
                }
                catch (ClassNotFoundException e){
                    logger.warn("Error deserializing client request.\n");
                    return;
                }

                if (SerializationManager.isEmpty(arr)){
                    logger.info("Client \"" + socket.getLocalAddress() + "\" disconnected.\n");
                    socket.close();
                    return;
                }

                boolean result;

                if (obj instanceof LogIn) {
                    LogIn command = (LogIn) obj;

                    username = command.getUsername();
                    password = command.getPassword();

                    result = logIn(username, password);
                    command.setResult(result);

                    tokenData = createJWToken(username);
                    command.setToken(tokenData[0]);

                    ServerResponse response = new ServerResponse(command);

                    ResponseSender responseTask = new ResponseSender(socket, username, response, false);
                    forkJoinPool.invoke(responseTask);

                    if (result == true) {
                        logger.info("User \"" + username + "\" - authentication successful.");
                        break;
                    }
                    logger.info("User \"" + username + "\" - authentication failed.");
                }
                else if (obj instanceof SignUp) {
                    SignUp command = (SignUp) obj;

                    username = command.getUsername();
                    password = command.getPassword();

                    result = signUp(username, password);
                    command.setResult(result);

                    tokenData = createJWToken(username);
                    command.setToken(tokenData[0]);

                    ServerResponse response = new ServerResponse(command);

                    ResponseSender responseTask = new ResponseSender(socket, username, response, false);
                    forkJoinPool.invoke(responseTask);

                    if (result == true) {
                        logger.info("User \"" + username + "\" - signed up.");
                        break;
                    }
                }
            }

            collectionManager.initUpdateHash(socket);
            RequestReader requestReaderTask = new RequestReader(socket, collectionManager, username, tokenData[1]);
            fixedThreadPool.submit(requestReaderTask);
        }
        catch (IOException e) {
            logger.error("Error user command executing. Connection refused.");
            try {
                socket.close();
            } catch (IOException ex) {
                logger.warn("Error while closing socket.");
            }
        } catch (SQLException e) {
            logger.error("Error access to database. Connection refused.");
            try {
                socket.close();
            } catch (IOException ex) {
                logger.error("Error database reconnecting.");
            }
        }
    }

    public boolean logIn(String username, String password) throws SQLException {
        if (!isUsernameExists(username))
            return false;

        String salt = getSalt(username);
        String hashedPassSalt = DigestUtils.md5Hex(password + salt);
        String dbPasswordHash = getPasswordHash(username);

        return dbPasswordHash.equals(hashedPassSalt);
    }

    private boolean signUp(String username, String password) throws SQLException {
        if (isUsernameExists(username))
            return false;

        String salt = RandomStringUtils.random(32);
        String hashedPassSalt = DigestUtils.md5Hex(password + salt);
        insertNewUser(username, hashedPassSalt, salt);
        return true;
    }

    private boolean isUsernameExists(String username) throws SQLException {
        PreparedStatement statement = dbManager.getConnection().prepareStatement(
                "SELECT username FROM users WHERE username = ?;");

        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        if (set.isBeforeFirst())
            return true;
        return false;
    }

    private void insertNewUser(String username, String hashedPassSalt, String salt) throws SQLException {
        PreparedStatement statement = dbManager.getConnection().prepareStatement(
                "INSERT INTO users(username, password_hash, salt)" +
                        "values (?, ?, ?);");
        statement.setString(1, username);
        statement.setString(2, hashedPassSalt);
        statement.setString(3, salt);
        statement.execute();
    }

    private String getSalt(String username) throws SQLException {
        PreparedStatement statement = dbManager.getConnection().prepareStatement(
                "SELECT salt FROM users where username = ?;"
        );
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();

        return set.getString("salt");
    }

    private String getPasswordHash(String username) throws SQLException {
        PreparedStatement statement = dbManager.getConnection().prepareStatement(
                "SELECT password_hash FROM users where username = ?;"
        );
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();

        return set.getString("password_hash");
    }

    private String[] createJWToken(String username) throws SQLException {
        try {
            String secret = RandomStringUtils.random(10);
            Algorithm algorithm = Algorithm.HMAC256(secret);

            int usrId = collectionManager.getUserId(username);

            String header = createJWTHeader();
            String token = JWT.create()
                    .withHeader(header)
                    .withIssuer("tekassh1-server")
                    .withClaim("usr-id", usrId)
                    .withExpiresAt(Instant.now().plusSeconds(1200))
                    .sign(algorithm);

            String[] res = new String[]{token, secret};
            return res;
        }
        catch (JWTCreationException e) {
            return null;
        }
    }

    private String createJWTHeader() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode header = mapper.createObjectNode();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(header);
        }
        catch (JsonProcessingException e) {
            return null;
        }
    }
}