package util;

import java.util.ResourceBundle;

public class UserSessionManager {
    private static ConnectionManager connectionManager = new ConnectionManager();
    private static AuthorizationManager authorizationManager = new AuthorizationManager(connectionManager);
    private static CommandManager commandManager = new CommandManager(authorizationManager, connectionManager);
    private static ResourceBundle currentBundle = ResourceBundle.getBundle("bundles.ClassBundle_en_EN");

    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static void setConnectionManager(ConnectionManager connectionManager) {
        UserSessionManager.connectionManager = connectionManager;
    }

    public static AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public static void setAuthorizationManager(AuthorizationManager authorizationManager) {
        UserSessionManager.authorizationManager = authorizationManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static void setCommandManager(CommandManager commandManager) {
        UserSessionManager.commandManager = commandManager;
    }

    public static ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    public static void setCurrentBundle(ResourceBundle currentBundle) {
        UserSessionManager.currentBundle = currentBundle;
    }
}
