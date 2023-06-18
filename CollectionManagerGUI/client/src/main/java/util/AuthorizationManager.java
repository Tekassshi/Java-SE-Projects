package util;

import commands.LogIn;
import commands.SignUp;
import managers.InputManager;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class AuthorizationManager {

    private Selector selector;
    private ConnectionManager connectionManager;

    private String token;

    public AuthorizationManager(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.selector = connectionManager.getSelector();
    }

    public synchronized boolean logIn(String username, String password) throws IOException, IllegalArgumentException {
        InputManager.readUsername(username);
        InputManager.readPassword(password);

        LogIn command = new LogIn(username, password);
        connectionManager.sendAuthRequest(command);

        return processAuthorizationResponse();
    }

    public synchronized boolean signUp(String username, String password) throws IOException, IllegalArgumentException {
        InputManager.readUsername(username);
        InputManager.readPassword(password);

        SignUp command = new SignUp(username, password);
        connectionManager.sendAuthRequest(command);

        return processAuthorizationResponse();
    }

    private synchronized boolean processAuthorizationResponse()
            throws IOException {

        while (true) {
            if (selector.select() > 0) {
                Set<SelectionKey> readySet = selector.selectedKeys();
                Iterator<SelectionKey> readySetIterator = readySet.iterator();

                SelectionKey key = null;

                while (readySetIterator.hasNext()) {
                    key = readySetIterator.next();
                    readySetIterator.remove();
                }
                if (key.isReadable()) {
                    Object[] result = connectionManager.readAuthorizationResponse();

                    boolean success = (boolean) result[0];
                    String token = (String) result[1];

                    if (success == false)
                        return false;

                    this.token = token;
                    return true;
                }
            }
        }
    }

    public String getToken() {
        return token;
    }
}