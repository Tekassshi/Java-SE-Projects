import commands.ExecuteScript;
import commands.Help;
import factories.CommandFactory;
import interfaces.Command;
import managers.InputManager;
import readers.ConsoleReader;
import readers.ScriptReader;
import util.AuthorizationManager;
import util.ConnectionManager;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static InputStreamReader isr = new InputStreamReader(System.in);
    private static BufferedReader reader = new BufferedReader(isr);

    private static boolean isRequestProcessed = true;
    private static Queue<Command> commandQueue = new LinkedList<>();
    private static AuthorizationManager authorizationManager;

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();

        System.out.println(GREEN_BOLD + "\n--- Welcome to collection manager! ---\n" + ANSI_RESET);
        int port = InputManager.readPort();
        ConnectionManager connectionManager = new ConnectionManager(selector, sc, port);
        boolean connectionStatus = connectionManager.tryConnect();
        if (!connectionStatus)
            return;

        authorizationManager = new AuthorizationManager(connectionManager);
        connectionManager.setAuthorizationManager(authorizationManager);
        authorizationManager.processAuthorization();

        System.out.println("(type \"help\" - to get reference, \"exit\" - to terminate)\n");
        CommandFactory.setCollectionManager(null);

        while (true) {
            if (selector.select() > 0) {
                Boolean doneStatus = processReadySet(connectionManager, selector.selectedKeys());
                if (doneStatus)
                    break;
            }
        }
        sc.close();
    }

    public static Boolean processReadySet(ConnectionManager connectionManager, Set readySet) throws IOException {

        SelectionKey key = null;
        var iterator = readySet.iterator();

        while (iterator.hasNext()) {
            key = (SelectionKey) iterator.next();
            iterator.remove();
        }
        if (key.isReadable()) {

            if (!connectionManager.readResponse()) {
                return true;
            } else {
                if (ConnectionManager.isLongReply()) {
                    isRequestProcessed = false;
                } else {
                    isRequestProcessed = true;
                    ConnectionManager.setIsFirstPackage(true);
                }
                return false;
            }
        }
        if (key.isWritable() && isRequestProcessed) {
            Object command;

            if (!commandQueue.isEmpty()) {
                command = commandQueue.poll();
            } else
                command = ConsoleReader.readCommand(reader);

            if (command == null)
                return false;

            if (command instanceof String && command.equals("exit"))
                return true;
            else if (command instanceof Help) {
                String s = ((Command) command).execute(null);
                System.out.println(s);
                return false;
            } else if (command instanceof ExecuteScript) {
                String path = ((ExecuteScript) command).getArgument();
                boolean result = ScriptReader.ReadScript(path, commandQueue);
                if (result == false)
                    commandQueue.clear();
                return false;
            }

            boolean status = connectionManager.sendRequest((Command) command, authorizationManager.getToken());

            if (!status)
                return true;

            isRequestProcessed = false;
        }
        return false;
    }
}