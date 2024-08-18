package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "head" command. Command outputs first element of current collection.
 * */
public class Head extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Head(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        super.getCollectionManager().head();
    }
}
