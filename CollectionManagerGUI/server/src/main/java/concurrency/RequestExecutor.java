package concurrency;

import commands.AbstractCommand;
import connection.ClientRequest;
import connection.ServerResponse;
import interfaces.Command;
import managers.CollectionManager;
import org.slf4j.Logger;
import util.LoggerProvider;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RequestExecutor extends RecursiveAction {
    private CollectionManager collectionManager;
    private Logger logger;
    private String user;
    private ClientRequest request;
    private Socket socket;

    private static Set<String> longReplyCommands = Collections.synchronizedSet(new HashSet<>());

    static {
        longReplyCommands.add("Show");
        longReplyCommands.add("FilterByNationality");
        longReplyCommands.add("PrintFieldDescendingHeight");
    }

    public RequestExecutor(Socket socket, CollectionManager collectionManager, String user, ClientRequest request){
        this.socket = socket;
        this.request = request;
        this.user = user;
        this.logger = LoggerProvider.getLogger();
        this.collectionManager = collectionManager;
    }

    @Override
    protected void compute() {
        Thread.currentThread().setName(user + " executor");

        ForkJoinPool forkJoinSendersPool = ThreadPoolFactory.getForkJoinSendersPool();
        AbstractCommand command = (AbstractCommand) request.getObj();
        command.setCollectionManager(collectionManager);

        logger.info("Executing user \"" + command.getClass().getSimpleName() + "\" command");
        String commandName = command.getClass().getSimpleName();

        if (collectionManager.getCollectionSize() > 40 &&
                longReplyCommands.contains(commandName)){

            LongResponseSender longResponseSenderTask =
                    new LongResponseSender(socket, user, collectionManager, command);
            forkJoinSendersPool.invoke(longResponseSenderTask);
            return;
        }

        String commandRes;
        try {
            commandRes = ((Command) command).execute(user);
        }
        catch (IOException e){
            e.printStackTrace();
            logger.error("Error command \"" + command.getClass().getSimpleName() + "\" executing.");
            return;
        }

        ServerResponse response = new ServerResponse(commandRes);
        ResponseSender responseSenderTask = new ResponseSender(socket, user, response, false);
        forkJoinSendersPool.execute(responseSenderTask);
    }
}