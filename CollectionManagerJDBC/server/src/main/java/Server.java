import managers.CollectionManager;
import org.slf4j.LoggerFactory;
import util.ConnectionManager;
import util.DatabaseManager;
import util.RequestProcessor;

import java.io.IOException;
import java.sql.SQLException;

public class Server {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws SQLException {

        logger.info("Server started\n");
        DatabaseManager dbManager = new DatabaseManager();

        try {
            dbManager.setConnection("localhost", 5432, ".credentials");
            dbManager.checkDatabase();
            logger.info("Connection with database was successfully established.");

            CollectionManager collectionManager = new CollectionManager(dbManager.getConnection(), logger);
            collectionManager.loadCollection();
            logger.info("Collection was successfully loaded from database.");

            ConnectionManager connectionManager = new ConnectionManager(logger);

            RequestProcessor requestProcessor = new RequestProcessor(connectionManager, collectionManager, logger);
            requestProcessor.addLongRepCommands("Show", "FilterByNationality", "PrintFieldDescendingHeight");

            requestProcessor.run();
        }
        catch (IllegalArgumentException e){
            logger.error("Error reading database. Check data for compatibility with current app version. " +
                    "Server stopped.");
            dbManager.closeConnection();
        }
        catch (InterruptedException e){
            logger.error("Request processor error. Server stopped.\n");
            dbManager.closeConnection();
        }
        catch (SQLException e) {
            logger.error("Error access to database. Check \".credentials\" file \nor make sure " +
                    "the database is running. Server stopped.\n");
        }
        catch (IOException e){
            logger.error("Error reading \".credentials\" file or it's doesn't exist. Server stopped.\n");
        }
    }
}