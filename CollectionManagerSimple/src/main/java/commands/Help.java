package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "help" command. Command outputs reference for all available commands.
 * */
public class Help extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Help(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        super.getCollectionManager().help();
    }
}
