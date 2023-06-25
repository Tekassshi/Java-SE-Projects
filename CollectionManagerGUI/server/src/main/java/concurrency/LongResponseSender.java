package concurrency;

import commands.AbstractCommand;
import commands.UpdateCollection;
import connection.ServerResponse;
import data.Person;
import managers.CollectionManager;
import org.slf4j.Logger;
import util.LoggerProvider;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class LongResponseSender extends RecursiveAction {
    private Socket socket;
    private String user;
    private Logger logger;
    private AbstractCommand command;
    private CollectionManager collectionManager;

    public LongResponseSender(Socket socket, String user, CollectionManager collectionManager, AbstractCommand command){
        this.socket = socket;
        this.logger = LoggerProvider.getLogger();
        this.command = command;
        this.collectionManager = collectionManager;
        this.user = user;
    }

    @Override
    protected void compute() {
        Thread.currentThread().setName(user + " long sender");

        ForkJoinPool forkJoinSendersPool = ThreadPoolFactory.getForkJoinSendersPool();
        ForkJoinPool forkJoinExecutorsPoolPool = ThreadPoolFactory.getForkJoinExecutorsPool();

        LongResponseCommandExecutor longResponseCommandExecutorTask =
                new LongResponseCommandExecutor(collectionManager, command);
        ArrayList<Person> tmp = forkJoinExecutorsPoolPool.invoke(longResponseCommandExecutorTask);

        double packageSize = 30;
        if (!(command instanceof UpdateCollection)){
            packageSize = calcOptimalStringPackageSize(20000);
        }
        else {

            if (collectionManager.getCollectionHash() == collectionManager.getUpdatedHash(socket)) {
                ServerResponse response = new ServerResponse(null);
                ResponseSender responseSenderTask = new ResponseSender(socket, user, response, true);
                forkJoinSendersPool.invoke(responseSenderTask);
                return;
            }
        }

        collectionManager.refreshUpdatedHash(socket);

        logger.info("PACKAGE size " + packageSize);

        int collectionSize = tmp.size();
        int numOfPackages = (int) Math.ceil(collectionSize / packageSize);

        int packageCounter = 0;
        int objectCounter = 0;

        if (numOfPackages == 0) {
            numOfPackages = 1;
            ResponseLengthSender responseLengthTask = new ResponseLengthSender(socket, user, numOfPackages);
            forkJoinSendersPool.invoke(responseLengthTask);

            System.out.println("Empty collection send");
            ServerResponse response = new ServerResponse(tmp);
            ResponseSender responseSenderTask = new ResponseSender(socket, user, response, false);
            forkJoinSendersPool.invoke(responseSenderTask);

            return;
        }

        ResponseLengthSender responseLengthTask = new ResponseLengthSender(socket, user, numOfPackages);
        forkJoinSendersPool.invoke(responseLengthTask);

        Long startTime = System.currentTimeMillis();

        if (!(command instanceof UpdateCollection))
            logger.info("Sending reply to the user");

        while (packageCounter < numOfPackages){
            String commandRes = ""; // For long string sending
            ArrayList<Person> pack = new ArrayList<>(); // For long collection sending

            int start = (int) packageSize * packageCounter;
            int end = (int) (start + packageSize);

            for (int i = start; i < end; i++){
                if (objectCounter == collectionSize)
                    break;

                if (command instanceof UpdateCollection) {
                    pack.add(tmp.get(i));
                }
                else {
                    commandRes += collectionManager.showPerson(tmp.get(i));
                }
                objectCounter++;
            }
            packageCounter++;

            ServerResponse response = new ServerResponse(pack);;
            if (!(command instanceof UpdateCollection)) {
                response = new ServerResponse(commandRes);
            }

            ResponseSender responseSenderTask = new ResponseSender(socket, user, response, true);
            forkJoinSendersPool.invoke(responseSenderTask);

            try {
                Thread.currentThread().sleep(60);
            } catch (InterruptedException e) {
                logger.error("Thread death - interrupted error.\n");
                return;
            }
        }
        Long sendingTime = System.currentTimeMillis() - startTime;
        logger.info(packageCounter + " packages sent in " + sendingTime + " ms");
    }

    private int calcOptimalStringPackageSize(int clientBuffSize){
        byte[] arr;
        try {
            arr = collectionManager.head().getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        int personByteSize = arr.length;
        int marginPersonByteSize = (int) (personByteSize + personByteSize * 0.25); // 50% margin

        return clientBuffSize/marginPersonByteSize;
    }
}