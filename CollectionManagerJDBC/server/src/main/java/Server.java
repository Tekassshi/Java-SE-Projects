import commands.AbstractCommand;
import connection.ServerResponse;
import factories.CommandFactory;
import interfaces.Command;
import managers.CollectionManager;
import connection.SerializationManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConcurrentInput;
import util.ConnectionManager;

import static managers.CollectionManager.ANSI_GREEN;
import static managers.CollectionManager.ANSI_RESET;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        logger.info("Server started\n");

        ConnectionManager connectionManager = new ConnectionManager();
        ServerSocket serverSocket = connectionManager.findPort();
        if (serverSocket == null) return;

        CommandFactory.setLongReplyCommands();
        CollectionManager collectionManager = new CollectionManager(logger);
        ConcurrentInput.createInputThread(collectionManager);
        ConnectionManager.setLogger(logger);

        System.out.println("\nServer is currently running on port: " + ANSI_GREEN + serverSocket.getLocalPort()
                + ANSI_RESET + "\n");

        while (true){
            Socket socket = serverSocket.accept();
            logger.info("Accepted new connection \"" + socket.getLocalAddress() + "\"");

            while (true){
                byte[] arr = connectionManager.readRequest(socket);
                if (arr == null) {
                    collectionManager.save();
                    break;
                }

                Object obj = SerializationManager.deserialize(arr);

                AbstractCommand command = (AbstractCommand) obj;
                command.setCollectionManager(collectionManager);

                logger.info("Executing user \"" + command.getClass().getSimpleName() + "\" command");

                String commandName = command.getClass().getSimpleName();
                if (collectionManager.getCollectionSize() > 40 &&
                        CommandFactory.longReplyCommands.contains(commandName)){

                    connectionManager.sendLongResponse(command, collectionManager, socket);
                    continue;
                }

                String commandRes = ((Command) command).execute();
                ServerResponse response = new ServerResponse(commandRes);
                connectionManager.sendResponse(socket, response);
            }
        }
    }
}