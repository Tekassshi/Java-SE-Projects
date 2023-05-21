package util;

import commands.LogIn;
import commands.SignUp;
import connection.ClientRequest;
import connection.SerializationManager;
import connection.ServerResponse;

import java.io.IOException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;

public class AuthorizationManager {
    private String username;
    private String password;
    private DatabaseManager dbManager;
    private ConnectionManager connectionManager;
    private Socket socket;
    private Logger logger;

    public AuthorizationManager(ConnectionManager connectionManager, Socket socket, DatabaseManager dbManager,
                                Logger logger){
        this.dbManager = dbManager;
        this.connectionManager = connectionManager;
        this.socket = socket;
        this.logger = logger;
    }

    public void processAuthorization() throws IOException, ClassNotFoundException, SQLException {
        while (true) {

            byte[] arr = connectionManager.readRequest(socket);
            ClientRequest request = (ClientRequest) SerializationManager.deserialize(arr);
            Object obj = request.getObj();

            boolean result;
            if (obj instanceof LogIn) {
                LogIn command = (LogIn) obj;

                username = command.getUsername();
                password = command.getPassword();

                result = logIn(username, password);
                command.setResult(result);
                ServerResponse response = new ServerResponse(command);
                connectionManager.sendResponse(socket, response);

                if (result == true) {
                    logger.info("User \"" + username + "\" - authorization successful.");
                    username = command.getUsername();
                    password = command.getPassword();
                    return;
                }
                logger.info("User \"" + username + "\" - authorization failed.");
            }
            else if (obj instanceof SignUp) {
                SignUp command = (SignUp) obj;
                result = signUp(command.getUsername(), command.getPassword());
                command.setResult(result);
                ServerResponse response = new ServerResponse(command);
                connectionManager.sendResponse(socket, response);

                if (result == true) {
                    logger.info("User \"" + username + "\" - signed up.");
                    username = command.getUsername();
                    password = command.getPassword();
                    return;
                }
            }
        }
    }

    public boolean logIn(String username, String password) throws SQLException {
        if (!isUsernameExists(username))
            return false;

        String salt = getSalt(username);
        String hashedPassSalt = DigestUtils.md5Hex(password + salt);
        String dbPasswordHash = getPasswordHash(username);

        if (!dbPasswordHash.equals(hashedPassSalt))
            return false;
        return true;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}