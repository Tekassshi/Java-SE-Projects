package commands;

import data.Country;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

/**
 * Class for "filter_by_nationality" command. Command outputs all nodes in collection with given nationality.
 * */
public class FilterByNationality extends AbstractCommand implements CommandWithArg {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public FilterByNationality(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * @param arg Nationality enum value in String representation.
     * */
    @Override
    public void setArg(String arg) {
        super.setArgument(InputManager.readNationality(arg));
    }

    @Override
    public void execute() {
        super.getCollectionManager().filterByNationality(Country.valueOf(super.getArgument()));
    }
}
