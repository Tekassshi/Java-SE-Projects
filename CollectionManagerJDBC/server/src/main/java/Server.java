import factories.CommandFactory;
import managers.CollectionManager;
import java.net.ServerSocket;
import org.slf4j.LoggerFactory;
import util.ConcurrentInput;
import util.ConnectionManager;
import util.RequestProcessor;

import static managers.CollectionManager.ANSI_GREEN;
import static managers.CollectionManager.ANSI_RESET;

public class Server {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        logger.info("Server started\n");

        CollectionManager collectionManager = new CollectionManager(logger);

        ConcurrentInput.createInputThread(collectionManager);

        ConnectionManager connectionManager = new ConnectionManager(logger);

        ServerSocket serverSocket = connectionManager.getSocket();
        if (serverSocket == null) return;
        System.out.println("Server is currently running on port: " + ANSI_GREEN + serverSocket.getLocalPort()
                + ANSI_RESET + "\n");

        CommandFactory.addLongReplyCommands("Show", "PrintFieldDescendingHeight", "FilterByNationality");

        RequestProcessor requestProcessor = new RequestProcessor(connectionManager, collectionManager, logger);
        try {
            requestProcessor.run();
        }
        catch (InterruptedException e){
            logger.error("Server error. Work will be stopped.");
        }
    }
}