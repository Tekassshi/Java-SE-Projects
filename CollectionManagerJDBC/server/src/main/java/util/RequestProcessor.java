package util;

import commands.AbstractCommand;
import connection.SerializationManager;
import connection.ServerResponse;
import interfaces.Command;
import managers.CollectionManager;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static managers.CollectionManager.ANSI_GREEN;
import static managers.CollectionManager.ANSI_RESET;

public class RequestProcessor {
    private ConnectionManager connectionManager;
    private CollectionManager collectionManager;
    private ServerSocket serverSocket;
    private Logger logger;

    private Set<String> longReplyCommands = new HashSet<>();

    public RequestProcessor(ConnectionManager connectionManager, CollectionManager collectionManager, Logger logger) {
        this.connectionManager = connectionManager;
        this.collectionManager = collectionManager;
        this.serverSocket = connectionManager.getSocket();
        this.logger = logger;
    }

    public void run() throws InterruptedException {
        if (serverSocket == null) return;

        logger.info("Request processor was started successfully.\n");
        System.out.println("Server is currently running on port: " + ANSI_GREEN + serverSocket.getLocalPort()
                + ANSI_RESET + "\n");

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
                            longReplyCommands.contains(commandName)){

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

    public void addLongRepCommands(String ... callName){
        for (String c: callName)
            longReplyCommands.add(c);
    }
}