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
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    private Selector selector;
    private ConnectionManager connectionManager;

    private String username;
    private String password;

    public AuthorizationManager(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.selector = connectionManager.getSelector();
    }

    public void processAuthorization() {
        while (true) {
            System.out.println("\n-- Authorization --\n");

            int type = InputManager.readAuthType();
            boolean result;

            try {
                if (type == 1) {
                    result = logIn();

                    if (result == false) {
                        System.out.println(ANSI_RED + "\nWrong username or password. Try again.\n" + ANSI_RESET);
                        continue;
                    }
                    break;
                }
                else {
                    result = signUp();

                    if (result == false) {
                        System.out.println(ANSI_RED + "\nThis username is already exists. Try again.\n" + ANSI_RESET);
                        continue;
                    }
                    break;
                }
            }
            catch (IOException | InterruptedException e){
                System.out.println(ANSI_RED + "\nServer error. Try again.\n" + ANSI_RESET);
            }
        }
    }

    public boolean logIn() throws IOException, InterruptedException {
        username = InputManager.readUsername();
        password = InputManager.readPassword();

        LogIn command = new LogIn(username, password);
        connectionManager.sendRequest(username, password, command);

        return processAuthorizationResponse();
    }

    public boolean signUp() throws IOException{
        System.out.println(ANSI_GREEN + "\nCreating a new account" + ANSI_RESET);
        username = InputManager.readUsername();
        password = InputManager.readPassword();

        SignUp command = new SignUp(username, password);
        connectionManager.sendRequest(username, password, command);
        return processAuthorizationResponse();
    }

    private boolean processAuthorizationResponse()
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
                    boolean result = connectionManager.readAuthorizationResponse();

                    if (result == false)
                        return false;
                    return true;
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}