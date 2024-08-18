package commands;

import managers.CollectionManager;

/**
 * Abstract command class, which is parent for all commands.
 * Contains main methods and fields to receive and execute commands.
 * */
public abstract class AbstractCommand {
    private CollectionManager collectionManager;
    private String argument;
    private Object object;

    protected final String GREEN_BOLD = "\033[1;32m";
    protected final String ANSI_RED = "\u001B[31m";
    protected final String ANSI_RESET = "\u001B[0m";

    /**
     * Main constructor that accept a collection manager object
     * @param collectionManager object of CollectionManager to execute command.
     * @see CollectionManager
     * */
    public AbstractCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * Getter that returns CollectionManager object of given command.
     * @return CollectionManager object
     * @see CollectionManager
     * */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Setter that set Collection manager object for given command
     * @param collectionManager object of CollectionManager
     * @see CollectionManager
     * */
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Getter that returns an argument object of given command.
     * @return argument String
     * */
    public String getArgument() {
        return argument;
    }

    /**
     * Setter that set Collection manager object for given command
     * @param argument string argument
     * */
    public void setArgument(String argument) {
        this.argument = argument;
    }

    /**
     * Getter that returns an object of given command.
     * @return Object for command.
     * Using only in commands that implements AssemblableCommand interface.
     * @see interfaces.AssemblableCommand
     * */
    public Object getObject() {
        return object;
    }

    /**
     * Setter that set object for given command.
     * @param object command object
     * Using only in commands that implements AssemblableCommand interface.
     * @see interfaces.AssemblableCommand
     * */
    public void setObject(Object object) {
        this.object = object;
    }
}
