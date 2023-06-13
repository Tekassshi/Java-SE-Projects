package concurrency;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import connection.ClientRequest;
import connection.SerializationManager;
import connection.ServerResponse;
import managers.CollectionManager;
import org.slf4j.Logger;
import util.LoggerProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;

public class RequestReader implements Runnable {
    private CollectionManager collectionManager;
    private Socket socket;
    private Logger logger;
    private String secret;
    private String user;

    public RequestReader(Socket socket, CollectionManager collectionManager, String user, String secret) {
        this.collectionManager = collectionManager;
        this.socket = socket;
        this.secret = secret;
        this.user = user;
        logger = LoggerProvider.getLogger();
    }

    @Override
    public void run() {
        ForkJoinPool forkJoinExecutorsPool = ThreadPoolFactory.getForkJoinExecutorsPool();
        ForkJoinPool forkJoinSendersPool = ThreadPoolFactory.getForkJoinSendersPool();
        Thread.currentThread().setName(user + " reader");

        try {
            int usrID = collectionManager.getUserId(user);
            while (true) {

                InputStream is = socket.getInputStream();
                byte[] arr = new byte[5000];
                ClientRequest request;

                try {
                    logger.info("Reading client request.");
                    is.read(arr);

                    if (SerializationManager.isEmpty(arr)){
                        logger.info("Client \"" + user + "\" disconnected.\n");
                        socket.close();
                        return;
                    }

                    request = (ClientRequest) SerializationManager.deserialize(arr);

                    if (!validateToken(usrID, request.getToken(), secret)) {
                        throw new JWTVerificationException(null);
                    }
                }
                catch (IOException e){
                    logger.warn("Force shutdown: " + user + "\n");
                    socket.close();
                    return;
                }
                catch (ClassNotFoundException e){
                    logger.warn("Error deserializing client request.\n");
                    return;
                }
                catch (JWTVerificationException e) {
                    logger.warn("Client \"" + user + "\" token timeout. Connection closed.\n");

                    ServerResponse response = new ServerResponse("\nSession timeout. Connection closed. " +
                            "Please, log in again.\n");
                    ResponseSender responseSenderTask =
                            new ResponseSender(socket, user, response, false);
                    forkJoinSendersPool.execute(responseSenderTask);
                    Thread.sleep(1000);
                    socket.close();
                    return;
                }

                RequestExecutor requestExecutorTask = new RequestExecutor(socket, collectionManager, user, request);
                forkJoinExecutorsPool.execute(requestExecutorTask);
            }
        }
        catch (IOException | InterruptedException e) {
            logger.error("Error user command executing. Connection closed.");
        } catch (SQLException e) {
            logger.error("Error database access. Connection closed.");
        }
    }

    private boolean validateToken(int usrId, String token, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("tekassh1-server")
                    .withClaim("usr-id", usrId)
                    .build();

            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException e) {
            return false;
        }
    }
}