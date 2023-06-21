package commands;

import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.*;

/**
 * Class for "execute_script" command. Command executes script given by user. Script contains commands in default form.
 * */
public class ExecuteScript extends AbstractCommand implements CommandWithArg {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public ExecuteScript(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * @param arg filepath to script that should be executed in String representation.
     * */
    @Override
    public void setArg(String arg) throws FileNotFoundException {
        super.setArgument(InputManager.readFile(arg));
    }

    /**
     * Method to executing current command. Runs stream using user file path.
     * @throws IOException if file path is incorrect.
     * */
    @Override
    public String execute(String username) {
        return null;
    }
}