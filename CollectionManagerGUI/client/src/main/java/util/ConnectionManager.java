package util;

import commands.AuthorizationCommand;
import commands.LogIn;
import commands.SignUp;
import connection.ClientRequest;
import connection.ServerResponse;
import interfaces.Command;
import connection.SerializationManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class ConnectionManager {
    private static final String GREEN_BOLD = "\033[1;32m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private SocketChannel sc;
    private Selector selector;
    private int port;

    private static boolean isLongReply = false;
    private static long packages = 0;
    private static boolean isFirstPackage = true;

    private AuthorizationManager authorizationManager;

    public ConnectionManager(Selector selector, SocketChannel sc, int port){
        this.selector = selector;
        this.sc = sc;
        this.port = port;
    }

    public void setConnection(int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName("localhost"), port);
        sc.connect(address);
        sc.finishConnect();
        System.out.println(ANSI_GREEN + "\nConnection with server was successfully established!" + ANSI_RESET);

        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    public boolean tryConnect() {

        long start = System.currentTimeMillis();
        int counter = 1;
        String dot = ".";

        while (true){
            try {
                setConnection(port);
                return true;
            }
            catch (IOException | AlreadyConnectedException e){
                try {
                    sc.close();
                    sc = SocketChannel.open();

                    if (System.currentTimeMillis() - start > 5000){
                        System.out.print("\rTrying connect to the server" + dot.repeat(counter));
                        Thread.sleep(500);
                    }
                }
                catch (IOException | InterruptedException f) {
                    return false;
                }

                counter = counter == 3 ? 1 : counter + 1;

                if (System.currentTimeMillis() - start > 25000){
                    System.out.println(ANSI_RED + "\rServer is currently unavailable!" + ANSI_RESET);
                    System.out.println(ANSI_RED + "Please, try later.\n" + ANSI_RESET);
                    return false;
                }
            }
        }
    }

    public boolean sendRequest(Command command, String token) {
        ClientRequest request = new ClientRequest(command);
        request.setToken(token);
        ByteBuffer bb = ByteBuffer.wrap(SerializationManager.serialize(request));

        while (true){
            try {
                sc.write(bb);
                return true;
            }
            catch (Exception e){
                if (!tryConnect())
                    return false;
                if (!(command instanceof LogIn || command instanceof SignUp))
                    authorizationManager.processAuthorization();
            }
        }
    }

    public boolean sendAuthRequest(Command command){
        ClientRequest request = new ClientRequest(command);

        ByteBuffer bb = ByteBuffer.wrap(SerializationManager.serialize(request));

        while (true){
            try {
                sc.write(bb);
                return true;
            }
            catch (Exception e){
                if (!tryConnect())
                    return false;
                if (!(command instanceof LogIn || command instanceof SignUp))
                    authorizationManager.processAuthorization();
            }
        }
    }

    public boolean readResponse() {
        ByteBuffer bb = ByteBuffer.allocate(20000);

        try {
            sc.read(bb);
            ServerResponse response = (ServerResponse) SerializationManager.deserialize(bb.array());
            Object responseObj = response.getObj();
            if (responseObj instanceof Integer){
                packages = (Integer) responseObj;
                isLongReply = true;
            }
            if (!isLongReply())
                System.out.println("Server request:\n" + responseObj.toString());
            else {
                if (!isFirstPackage)
                    packages--;
                if (isFirstPackage) {
                    System.out.println("Server request:\n");
                    isFirstPackage = false;
                }
                System.out.println(responseObj.toString());
                if (packages == 0) {
                    isLongReply = false;
                }
            }
            return true;
        }
        catch (Exception e){
            if (!tryConnect())
                return false;

            authorizationManager.processAuthorization();

            if (isLongReply)
                isLongReply = false;

            System.out.println(ANSI_RED + "\nError while reading last response. Try again.\n");
            return true;
        }
    }

    public Object[] readAuthorizationResponse() {
        ByteBuffer bb = ByteBuffer.allocate(5000);

        Object[] res = new Object[2];
        res[0] = false;

        while (true) {
            try {
                sc.read(bb);
                ServerResponse response = (ServerResponse) SerializationManager.deserialize(bb.array());
                AuthorizationCommand responseObj = (AuthorizationCommand) response.getObj();

                if (responseObj.getResult() == false) {
                    return res;
                }
                res[0] = true;
                res[1] = responseObj.getToken();
                return res;
            }
            catch (ClassNotFoundException | NullPointerException | IOException e){
                if (!tryConnect())
                    return res;

                System.out.println(ANSI_RED + "\nError while reading authorization result. Try again.\n");
                return res;
            }
        }
    }

    public Selector getSelector() {
        return selector;
    }

    public static void setIsFirstPackage(boolean isFirstPackage) {
        ConnectionManager.isFirstPackage = isFirstPackage;
    }

    public static boolean isLongReply() {
        return isLongReply;
    }

    public void setAuthorizationManager(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }
}