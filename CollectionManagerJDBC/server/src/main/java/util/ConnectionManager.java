package util;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionManager {
    private static Logger logger;
    private static ServerSocket socket = null;

    public ConnectionManager(Logger logger){
        this.logger = logger;
    }

    public synchronized ServerSocket getSocket(){
        if (socket != null)
            return socket;

        ServerSocket serverSocket;
        int port = 49152;
        while (true){
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket;
                return serverSocket;
            }
            catch (IOException e) {
                if (port == 65535) {
                    logger.error("All ports are busy. Server will be closed");
                    return null;
                }
                logger.warn("Port " + port + " is busy.");
                port++;
            }
        }
    }
}