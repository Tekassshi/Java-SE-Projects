package commands;

import interfaces.Command;
import managers.CollectionManager;

import java.io.IOException;

/**
 * Class for "print_field_descending_height" command. Command outputs value of height field of all collection elements
 * in descending order.
 * */
public class PrintFieldDescendingHeight extends AbstractCommand implements Command {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public PrintFieldDescendingHeight(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String username) throws IOException {
        return super.getCollectionManager().printFieldDescendingHeight();
    }
}
