import abstractions.ServerTemplate;
import concurrency.AuthenticationExecutor;
import concurrency.ThreadPoolFactory;
import managers.CollectionManager;
import org.slf4j.Logger;
import util.ConnectionManager;
import util.DatabaseManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

public class Server extends ServerTemplate {
    private DatabaseManager dbManager;
    private CollectionManager collectionManager;
    private ConnectionManager connectionManager;
    private ServerSocket serverSocket;
    private Logger logger;

    public static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    @Override
    public void configureLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void configureDatabase() throws SQLException, IOException {
        dbManager = new DatabaseManager();

        // dbManager.setConnection("localhost", 5432, ".credentials");
        dbManager.setConnection(".credentials");

        dbManager.checkDatabase();
        logger.info("Connection with database was successfully established.");
    }

    @Override
    public void configureCollectionManager() throws SQLException {
        collectionManager = new CollectionManager(dbManager.getConnection(), logger);
        collectionManager.loadCollection();
        logger.info("Collection was successfully loaded from database.");
    }

    @Override
    public void configureConnection() throws SocketException {
        connectionManager = new ConnectionManager(logger);

        serverSocket = connectionManager.getSocket();
        if (serverSocket == null)
            throw new SocketException();
    }

    @Override
    public void run() throws IOException {
        ExecutorService fixedThreadPool = ThreadPoolFactory.getFixedThreadPool();

        logger.info("Request processor was started successfully.\n");
        System.out.println("Server is currently running on port: " + ANSI_GREEN + serverSocket.getLocalPort()
                + ANSI_RESET + "\n");

        while (true) {
            Socket client = serverSocket.accept();

            logger.info("Accepted new connection \"" + client.getLocalAddress() + "\"");
            AuthenticationExecutor authenticationTask =
                    new AuthenticationExecutor(client, collectionManager, dbManager);
            fixedThreadPool.submit(authenticationTask);
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }
}