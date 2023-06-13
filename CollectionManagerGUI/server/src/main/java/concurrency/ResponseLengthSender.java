package concurrency;

import connection.SerializationManager;
import connection.ServerResponse;
import org.slf4j.Logger;
import util.LoggerProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.RecursiveAction;

public class ResponseLengthSender extends RecursiveAction {
    private Socket socket;
    private Logger logger;
    private Integer replyLength;
    private String user;

    public ResponseLengthSender(Socket socket, String user, Integer replyLength) {
        this.socket = socket;
        this.logger = LoggerProvider.getLogger();
        this.replyLength = replyLength;
        this.user = user;
    }

    @Override
    protected void compute() {
        Thread.currentThread().setName(user + " len sender");
        OutputStream os;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            logger.error("Error user command executing. Connection refused.");
            return;
        }

        try {
            logger.info("Sending amount of packages to user");
            ServerResponse response = new ServerResponse(replyLength);
            os.write(SerializationManager.serialize(response));
        }
        catch (IOException e) {
            logger.warn("Force shutdown: " + socket.getLocalAddress() + "\n");
            try {
                socket.close();
            } catch (IOException ex) {
                logger.error("Error user command executing. Connection refused.");
            }
        }
    }
}
