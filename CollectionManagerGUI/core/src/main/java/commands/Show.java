package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "show" command. Command outputs all nodes from current collection.
 * */
public class Show extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String username) {
        return super.getCollectionManager().show();
    }
}
