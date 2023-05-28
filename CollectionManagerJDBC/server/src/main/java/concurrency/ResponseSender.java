package concurrency;

import connection.SerializationManager;
import connection.ServerResponse;
import org.slf4j.Logger;
import util.LoggerProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.RecursiveAction;

public class ResponseSender extends RecursiveAction {
    private Socket socket;
    private Logger logger;
    private ServerResponse response;
    private boolean isLongReply;
    private String user;

    public ResponseSender(Socket socket, String user, ServerResponse response, boolean isLongReply) {
        this.response = response;
        this.socket = socket;
        logger = LoggerProvider.getLogger();
        this.isLongReply = isLongReply;
        this.user = user;
    }

    @Override
    protected void compute() {
        Thread.currentThread().setName(user + " sender");

        OutputStream os;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            logger.error("Error user command executing. Connection refused.");
            return;
        }

        try {
            if (!isLongReply)
                logger.info("Sending reply to the user");
            os.write(SerializationManager.serialize(response));
        }
        catch (IOException e) {
            logger.warn("Force shutdown: " + user + "\n");
            try {
                socket.close();
            } catch (IOException ex) {
                logger.error("Error user command executing. Connection refused.");
            }
        }
    }
}