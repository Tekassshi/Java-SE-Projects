package commands;

import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

/**
 * Class for "remove_by_id" command. Command removes Person node with given "id" value.
 * */
public class RemoveById extends AbstractCommand implements CommandWithArg {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public RemoveById(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * @param arg person "id" value in String representation.
     * */
    @Override
    public void setArg(String arg) {
        super.setArgument(InputManager.readId(arg));
    }

    @Override
    public void execute() {
        super.getCollectionManager().removeById(Integer.parseInt(super.getArgument()));
    }
}
