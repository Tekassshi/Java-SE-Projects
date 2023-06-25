package util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import commands.AuthorizationCommand;
import connection.ClientRequest;
import connection.ServerResponse;
import data.Person;
import interfaces.Command;
import connection.SerializationManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ConnectionManager {
    public static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private SocketChannel sc;
    private Selector selector;
    private int port;

    private static boolean isLongReply = false;
    private static long packages = 0;
    private static boolean isFirstPackage = true;

    public ConnectionManager() {
        try {
            selector = Selector.open();
            sc = SocketChannel.open();
        }
        catch (IOException e) {
            System.out.println("ConnectionManager init error!");
        }
    }

    private synchronized void setConnection(int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName("localhost"), port);
        sc.connect(address);
        sc.finishConnect();
        System.out.println(ANSI_GREEN + "\nConnection with server was successfully established!" + ANSI_RESET);

        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    public synchronized boolean tryConnect(int port) {
        this.port = port;

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

    public synchronized void sendRequest(Command command, String token) throws IOException {
        ClientRequest request = new ClientRequest(command);
        request.setToken(token);
        ByteBuffer bb = ByteBuffer.wrap(SerializationManager.serialize(request));

        try {
            sc.write(bb);
        }
        catch (Exception e){
            if (!tryConnect(port))
               throw new IOException();
        }
    }

    public synchronized boolean sendAuthRequest(Command command) throws IOException{
        ClientRequest request = new ClientRequest(command);

        ByteBuffer bb = ByteBuffer.wrap(SerializationManager.serialize(request));

        while (true){
            try {
                sc.write(bb);
                return true;
            }
            catch (Exception e){
                if (!tryConnect(port))
                    throw new IOException();
            }
        }
    }

    public synchronized String readResponse() throws IOException, JWTVerificationException {
        StringBuilder res = new StringBuilder("");
        isFirstPackage = true;

        long start = System.currentTimeMillis();

        while (true) {
            ByteBuffer bb = ByteBuffer.allocate(20000);

            if (selector.select() > 0) {
                Set<SelectionKey> readySet = selector.selectedKeys();
                Iterator<SelectionKey> readySetIterator = readySet.iterator();

                SelectionKey key = null;

                while (readySetIterator.hasNext()) {
                    key = readySetIterator.next();
                    readySetIterator.remove();
                }

                try {
                    if (System.currentTimeMillis() - start > 5000)
                        throw new IOException();

                    if (!key.isReadable())
                        continue;

                    sc.read(bb);
                    ServerResponse response = (ServerResponse) SerializationManager.deserialize(bb.array());
                    Object responseObj = response.getObj();

                    if (responseObj instanceof Integer){
                        packages = (Integer) responseObj;
                        isLongReply = true;
                    }

                    if (responseObj instanceof JWTVerificationException) {
                        throw new JWTVerificationException(null);
                    }

                    if (isLongReply) {
                        start = System.currentTimeMillis();

                        if (!isFirstPackage)
                            packages--;
                        if (isFirstPackage) {
                            isFirstPackage = false;
                        }

                        res.append(responseObj.toString() + "\n");

                        if (packages == 0) {
                            isLongReply = false;
                            return res.toString();
                        }
                    }
                    else
                        return responseObj.toString();
                }
                catch (JWTVerificationException e) {
                    sc.close();
                    throw new JWTVerificationException(null);
                }
                catch (ClassNotFoundException e) {
                    System.out.println(ANSI_RED + "\nError while reading last response. Try again.\n");
                }
                catch (IOException e){
                    if (!tryConnect(port))
                        throw new IOException();

                    if (isLongReply)
                        isLongReply = false;
                }
            }
        }
    }

    public synchronized Object[] readAuthorizationResponse() throws IOException {
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
            catch (ClassCastException | ClassNotFoundException | NullPointerException | IOException e){
                if (!tryConnect(port))
                    throw new IOException();

                System.out.println(ANSI_RED + "\nError while reading authorization result. Try again.\n");
                return res;
            }
        }
    }

    public synchronized ArrayList<Person> readUpdatedCollectionResponse() throws JWTVerificationException, IOException {
        ArrayList<Person> collection = new ArrayList<>();
        isFirstPackage = true;

        long start = System.currentTimeMillis();

        while (true) {
            ByteBuffer bb = ByteBuffer.allocate(20000);

            if (selector.select() > 0) {
                Set<SelectionKey> readySet = selector.selectedKeys();
                Iterator<SelectionKey> readySetIterator = readySet.iterator();

                SelectionKey key = null;

                while (readySetIterator.hasNext()) {
                    key = readySetIterator.next();
                    readySetIterator.remove();
                }

                try {
                    if (System.currentTimeMillis() - start > 5000)
                        throw new IOException();

                    if (!key.isReadable())
                        continue;

                    sc.read(bb);

                    ServerResponse response = (ServerResponse) SerializationManager.deserialize(bb.array());
                    Object responseObj = response.getObj();

                    if (responseObj == null)
                        return null;

                    if (responseObj instanceof JWTVerificationException) {
                        throw new JWTVerificationException(null);
                    }

                    if (responseObj instanceof Integer){
                        packages = (Integer) responseObj;
                        isLongReply = true;
                    }

                    if (isLongReply) {
                        start = System.currentTimeMillis();

                        if (!isFirstPackage)
                            packages--;
                        if (isFirstPackage) {
                            isFirstPackage = false;
                            continue;
                        }

                        collection.addAll((ArrayList) responseObj);

                        if (packages == 0) {
                            isLongReply = false;
                            return collection;
                        }
                    }
                    else {
                        collection.addAll((ArrayList) responseObj);
                        return collection;
                    }
                }
                catch (JWTVerificationException e) {
                    sc.close();
                    throw new JWTVerificationException(null);
                }
                catch (ClassNotFoundException e) {
                    System.out.println(ANSI_RED + "\nError while reading last response. Try again.\n");
                }
                catch (IOException e){
                    if (!tryConnect(port))
                        throw new IOException();

                    if (isLongReply)
                        isLongReply = false;
                }
            }
        }
    }

    public Selector getSelector() {
        return selector;
    }
}