package abstractions;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;

public abstract class ServerTemplate {

    final public void configureServer(Logger logger) throws SQLException, IOException {
        configureLogger(logger);
        configureDatabase();
        configureCollectionManager();
        configureConnection();
    }

    public abstract void configureLogger(Logger logger);

    abstract public void configureDatabase() throws SQLException, IOException;
    abstract public void configureCollectionManager() throws SQLException;
    abstract public void configureConnection() throws SocketException;
    abstract public void run() throws IOException;
}