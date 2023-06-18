package util;

import commands.ExecuteScript;
import data.UserCollection;
import interfaces.Command;
import managers.CollectionManager;
import readers.ScriptReader;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class CommandManager {

    private boolean isRequestProcessed = true;
    private Queue<Command> commandQueue = new LinkedList<>();
    private AuthorizationManager authorizationManager;
    private ConnectionManager connectionManager;

    private UserCollection userCollection = null;
    private Selector selector;

    public CommandManager(AuthorizationManager authorizationManager, ConnectionManager connectionManager) {
        this.authorizationManager = authorizationManager;
        this.connectionManager = connectionManager;
        this.selector = connectionManager.getSelector();
    }

    public String processUserCommand(Command command) throws IOException {
        if (selector.select() > 0) {
            Set<SelectionKey> readySet = selector.selectedKeys();
            Iterator<SelectionKey> readySetIterator = readySet.iterator();

            SelectionKey key = null;

            while (readySetIterator.hasNext()) {
                key = readySetIterator.next();
                readySetIterator.remove();
            }

            if (key.isWritable()) {
                if (command instanceof ExecuteScript) {
                    String path = ((ExecuteScript) command).getArgument();
                    boolean result = ScriptReader.ReadScript(path, commandQueue);
                    if (!result)
                        commandQueue.clear();
                    return processScriptCommands();
                }
                else
                    connectionManager.sendRequest(command, authorizationManager.getToken());
            }
        }
        return connectionManager.readResponse();
    }

    private String processScriptCommands() throws IOException {
        StringBuilder res = new StringBuilder("");
        int numOfCommands = commandQueue.size();

        for (int i = 0; i < numOfCommands + 1; i++) {
            if (selector.select() > 0) {
                Set<SelectionKey> readySet = selector.selectedKeys();
                Iterator<SelectionKey> readySetIterator = readySet.iterator();

                SelectionKey key = null;

                while (readySetIterator.hasNext()) {
                    key = readySetIterator.next();
                    readySetIterator.remove();
                }
                if (key.isReadable()) {
                    res.append(connectionManager.readResponse());
                }
                if (key.isWritable() && !commandQueue.isEmpty()) {
                    Command command = commandQueue.poll();
                    connectionManager.sendRequest(command, authorizationManager.getToken());
                }
            }
        }
        return res.toString();
    }


}