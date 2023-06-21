package commands;

import interfaces.Command;
import managers.CollectionManager;

public class UpdateCollection extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public UpdateCollection(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String username) {
        return super.getCollectionManager().show();
    }
}