import org.slf4j.Logger;
import util.DatabaseManager;
import util.LoggerProvider;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;

public class Main {

    static Logger logger = LoggerProvider.getLogger();

    public static void main(String[] args) throws SQLException {
        Server server = new Server();

        try {
            server.configureServer(logger);
            server.run();
        }
        catch (SocketException | IllegalArgumentException e) {
            logger.error("Error reading database. Check data for compatibility with current app version. " +
                    "Server stopped.");
            DatabaseManager dbManager = server.getDbManager();
            dbManager.closeConnection();
        }
        catch (SQLException e) {
            logger.error("Error access to database. Check \".credentials\" file \nor make sure " +
                    "the database is running. Server stopped.\n");
        }
        catch (IOException e) {
            logger = server.getLogger();
            logger.error("Error reading \".credentials\" file or it's doesn't exist. Server stopped.\n");
        }
    }
}