package commands;

import data.Country;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.IOException;

/**
 * Class for "remove_all_by_nationality" command. Command removes all persons from current collection
 * that have given nationality.
 * */
public class RemoveAllByNationality extends AbstractCommand implements CommandWithArg {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public RemoveAllByNationality(CollectionManager collectionManager) {
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
    public void execute() throws IOException {
        super.getCollectionManager().removeAllByNationality(Country.valueOf(super.getArgument()));
    }
}
