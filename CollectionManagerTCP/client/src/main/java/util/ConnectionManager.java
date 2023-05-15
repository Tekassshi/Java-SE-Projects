package util;

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

                    System.out.print("\rTrying connect to server" + dot.repeat(counter));
                    Thread.sleep(500);
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

    public boolean sendRequest(Command command) {
        System.out.println();

        ByteBuffer bb = ByteBuffer.wrap(SerializationManager.serialize(command));

        while (true){
            try {
                sc.write(bb);
                return true;
            }
            catch (Exception e){
                if (!tryConnect())
                    return false;
            }
        }
    }

    public boolean readResponse() {
        ByteBuffer bb = ByteBuffer.allocate(10000);

        while (true) {
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

                if (isLongReply) {
                    isLongReply = false;
                    System.out.println(ANSI_RED + "\nError while reading last response. Try again.\n");
                    return true;
                }
            }
        }
    }

    public static void setIsFirstPackage(boolean isFirstPackage) {
        ConnectionManager.isFirstPackage = isFirstPackage;
    }

    public static boolean isLongReply() {
        return isLongReply;
    }
}