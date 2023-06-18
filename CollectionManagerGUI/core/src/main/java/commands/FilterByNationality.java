package commands;

import data.Country;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.IOException;

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
    public void setArg(String arg) throws IOException {
        super.setArgument(InputManager.readNationality(arg).toString());
    }

    @Override
    public String execute(String username) {
        return super.getCollectionManager().filterByNationality(username, Country.valueOf(super.getArgument()));
    }
}
