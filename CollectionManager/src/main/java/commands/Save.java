package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "save" command. Command saves current collection to XMl file.
 * */
public class Save extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Save(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        super.getCollectionManager().save();
    }
}
