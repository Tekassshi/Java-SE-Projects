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
    private static final String GREEN_BOLD = "\033[1;32m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    private Selector selector;
    private ConnectionManager connectionManager;

    private String token;

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
                    System.out.println(GREEN_BOLD + "\nAuthorization successful!\n" + ANSI_RESET);
                    break;
                }
                else {
                    result = signUp();

                    if (result == false) {
                        System.out.println(ANSI_RED + "\nThis username is already exists. Try again.\n" + ANSI_RESET);
                        continue;
                    }
                    System.out.println(GREEN_BOLD + "\nAuthorization successful!\n" + ANSI_RESET);
                    break;
                }
            }
            catch (IOException | InterruptedException e){
                System.out.println(ANSI_RED + "\nServer error. Try again.\n" + ANSI_RESET);
            }
        }
    }

    public boolean logIn() throws IOException, InterruptedException {
        String username = InputManager.readUsername();
        String password = InputManager.readPassword();

        LogIn command = new LogIn(username, password);
        connectionManager.sendAuthRequest(command);

        return processAuthorizationResponse();
    }

    public boolean signUp() throws IOException{
        System.out.println(ANSI_GREEN + "\nCreating a new account" + ANSI_RESET);
        String username = InputManager.readUsername();
        String password = InputManager.readPassword();

        SignUp command = new SignUp(username, password);
        connectionManager.sendAuthRequest(command);
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