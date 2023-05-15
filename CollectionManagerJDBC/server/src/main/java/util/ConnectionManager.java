package util;

import commands.AbstractCommand;
import commands.FilterByNationality;
import commands.PrintFieldDescendingHeight;
import commands.Show;
import data.Country;
import data.Person;
import connection.ServerResponse;
import managers.CollectionManager;
import connection.SerializationManager;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionManager {
    private Logger LOGGER;
    private boolean isLongReply = true;
    private ServerSocket socket = null;

    public ConnectionManager(Logger logger){
        this.LOGGER = logger;
    }

    public ServerSocket getSocket(){
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
                    LOGGER.error("All ports are busy. Server will be closed");
                    return null;
                }
                LOGGER.warn("Port " + port + " is busy.");
                port++;
            }
        }
    }

    public byte[] readRequest(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();

        byte[] arr = new byte[10000];

        try {
            is.read(arr);
        }
        catch (IOException e){
            LOGGER.info("Reading a request");
            LOGGER.warn("Force shutdown: " + socket.getLocalAddress() + "\n");
            socket.close();
            return null;
        }

        if (SerializationManager.isEmpty(arr)){
            LOGGER.info("Client \"" + socket.getLocalAddress() + "\" disconnected.\n");
            socket.close();
            return null;
        }
        return arr;
    }

    public void sendResponse(Socket socket, ServerResponse response) throws IOException {
        OutputStream os = socket.getOutputStream();

        try {
            if (!isLongReply)
                LOGGER.info("Sending reply to the user");
            os.write(SerializationManager.serialize(response));
        }
        catch (IOException e) {
            LOGGER.warn("Force shutdown: " + socket.getLocalAddress() + "\n");
            socket.close();
        }
    }

    public void sendResponseLength(Socket socket, Integer replyLength) throws IOException {
        OutputStream os = socket.getOutputStream();

        try {
            LOGGER.info("Sending amount of packages to user");
            ServerResponse response = new ServerResponse(replyLength);
            os.write(SerializationManager.serialize(response));
        }
        catch (IOException e) {
            LOGGER.warn("Force shutdown: " + socket.getLocalAddress() + "\n");
            socket.close();
        }
    }

    public void sendLongResponse(AbstractCommand command, CollectionManager collectionManager, Socket socket)
            throws IOException, InterruptedException {

        ArrayList<Person> tmp = null;

        if (command instanceof Show)
            tmp = new ArrayList<>(collectionManager.getCollection());
        else if (command instanceof FilterByNationality)
            tmp = collectionManager.getFilteredNationalityCollection(Country.valueOf(command.getArgument()));
        else if (command instanceof PrintFieldDescendingHeight) {
            tmp = collectionManager.getDescendingHeightCollection();
        }

        int collectionSize = tmp.size();
        int numOfPackages = (int) Math.ceil(collectionSize / 40.0);
        int packageCounter = 0;
        int objectCounter = 0;

        sendResponseLength(socket, numOfPackages);

        Long startTime = System.currentTimeMillis();
        LOGGER.info("Sending reply to the user");

        while (packageCounter < numOfPackages){
            String commandRes = "";
            int start = 40 * packageCounter;
            int end = start + 40;

            for (int i = start; i < end; i++){
                if (objectCounter == collectionSize)
                    break;

                commandRes += collectionManager.showPerson(tmp.get(i));
                objectCounter++;
            }
            packageCounter++;

            ServerResponse response = new ServerResponse(commandRes);
            sendResponse(socket, response);
            Thread.sleep(100);
        }
        Long sendingTime = System.currentTimeMillis() - startTime;
        isLongReply = false;
        LOGGER.info(packageCounter + " packages sent in " + sendingTime + " ms");
    }
}