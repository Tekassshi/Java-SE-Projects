package commands;

import interfaces.Command;
import managers.CollectionManager;

import java.io.IOException;

/**
 * Class for "info" command. Command outputs all information about current collection (date of initialization,
 * number of elements, type of collection).
 * */
public class Info extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Info(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() throws IOException {
        super.getCollectionManager().info();
    }
}
