package interfaces;

import managers.CollectionManager;
import java.io.IOException;

/** Interface contains basic methods for all commands types.*/
public interface Command {
    /**
     * Method to executing current command using CollectionManager object and Person built object.
     * @see CollectionManager
     * */
    void execute() throws IOException;
}
