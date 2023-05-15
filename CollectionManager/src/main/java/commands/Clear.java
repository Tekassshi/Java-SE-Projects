package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "clear" command. Command clears all collection data.
 * */
public class Clear extends AbstractCommand implements Command {
    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        super.getCollectionManager().clear();
    }
}
