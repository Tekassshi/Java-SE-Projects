import managers.CollectionManager;
import org.slf4j.LoggerFactory;
import util.ConcurrentInput;
import util.ConnectionManager;
import util.DatabaseManager;
import util.RequestProcessor;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;

public class Server {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

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

            ConcurrentInput.createInputThread(collectionManager); // fix dependencies

            RequestProcessor requestProcessor = new RequestProcessor(connectionManager, collectionManager, logger);
            requestProcessor.addLongRepCommands("Show", "FilterByNationality", "PrintFieldDescendingHeight");

            requestProcessor.run();
        }
        catch (InterruptedException e){
            logger.error("Request processor error. Server will be stopped.\n");
        }
        catch (ConnectException e){
            logger.error("Error connecting to database. Server will be stopped.\n");
        }
        catch (SQLException e) {
            logger.error("Error connecting to database. Check authorization data. Server will be stopped.\n");
        }
        catch (IOException e){
            logger.error("Error reading \".properties\" file or it's doesn't exist. Server will be stopped.\n");
        }
        finally {
            try {
                dbManager.closeConnection();
            }
            catch (SQLException e){
                logger.error("Database connection closing error.\n");
            }
        }
    }
}