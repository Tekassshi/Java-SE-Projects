package commands;

import data.Person;
import interfaces.AssemblableCommand;
import interfaces.Command;
import managers.CollectionManager;
import managers.InputManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * Class for "add" command. Command adding new person to collection.
 * */
public class Add extends AbstractCommand implements Command, AssemblableCommand {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Add(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void buildObject() {
        System.out.println("\n--- Adding a new person to collection ---\n");

        Person person = new Person();

        person.setName(InputManager.readName());
        person.setCoordinates(InputManager.readCoordinates());
        person.setCreationDate(ZonedDateTime.now());
        person.setHeight(InputManager.readHeight());
        person.setWeight(InputManager.readWeight());
        person.setEyeColor(InputManager.readEyeColor());
        person.setNationality(InputManager.readNationality());
        person.setLocation(InputManager.readLocation());

        super.setObject(person);
    }

    @Override
    public void buildObjectFromScript(BufferedReader reader) throws IOException {
        Person person = new Person();

        person.setName(InputManager.readNameScript(reader));
        person.setCoordinates(InputManager.readCoordinatesScript(reader));
        person.setCreationDate(ZonedDateTime.now());
        person.setHeight(InputManager.readHeightScript(reader));
        person.setWeight(InputManager.readWeightScript(reader));
        person.setEyeColor(InputManager.readEyeColorScript(reader));
        person.setNationality(InputManager.readNationalityScript(reader));
        person.setLocation(InputManager.readLocationScript(reader));

        super.setObject(person);
    }

    /**
     * Method to executing current command using CollectionManager object and Person built object.
     * @see CollectionManager
     * @see Person
     * */
    @Override
    public void execute() throws IOException {
        super.getCollectionManager().add((Person) super.getObject());
    }
}