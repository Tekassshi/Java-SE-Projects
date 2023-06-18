package util;

public class UserSessionManager {
    private static ConnectionManager connectionManager = new ConnectionManager();
    private static AuthorizationManager authorizationManager = new AuthorizationManager(connectionManager);
    private static CommandManager commandManager = new CommandManager(authorizationManager, connectionManager);
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
}
