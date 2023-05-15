package util;

import commands.AbstractCommand;
import connection.SerializationManager;
import connection.ServerResponse;
import factories.CommandFactory;
import interfaces.Command;
import managers.CollectionManager;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestProcessor {
    private ConnectionManager connectionManager;
    private CollectionManager collectionManager;
    private ServerSocket serverSocket;
    private Logger logger;

    public RequestProcessor(ConnectionManager connectionManager, CollectionManager collectionManager, Logger logger) {
        this.connectionManager = connectionManager;
        this.collectionManager = collectionManager;
        this.serverSocket = connectionManager.getSocket();
        this.logger = logger;
    }

    public void run() throws InterruptedException {

        while (true){

            try {
                Socket socket = serverSocket.accept();
                logger.info("Accepted new connection \"" + socket.getLocalAddress() + "\"");

                while (true){
                    byte[] arr = connectionManager.readRequest(socket);
                if (arr == null) {
//                    collectionManager.save();
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

                    String commandRes;
                    try {
                        commandRes = ((Command) command).execute();
                    }
                    catch (IOException e){
                        logger.error("Error command \"" + command.getClass().getSimpleName() + "\" executing.");
                        continue;
                    }

                    ServerResponse response = new ServerResponse(commandRes);
                    connectionManager.sendResponse(socket, response);
                }

            }
            catch (IOException | ClassNotFoundException e){
                logger.error("Error user command executing. Connection refused.");
            }
        }
    }
}