package interfaces;

import data.Person;

import java.io.BufferedReader;
import java.io.IOException;

/** Interface contains methods for commands, that should build some object before executing.*/
public interface AssemblableCommand {
    /**
     * AssemlableCommand interface method for building Person object from default input stream to add it to collection.
     * @see Person
     * */
    void buildObject();

    /**
     * AssemlableCommand interface method for building Person object from given stream to add it to collection.
     * @see Person
     * */
    void buildObjectFromScript(BufferedReader reader) throws IOException;
}
