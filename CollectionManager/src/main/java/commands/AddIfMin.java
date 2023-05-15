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
 * Class for "add_if_min" command. Command adding new person to collection if it's lower than current
 * min object in collection.
 * */
public class AddIfMin extends AbstractCommand implements Command, AssemblableCommand {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public AddIfMin(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute() {
        super.getCollectionManager().addIfMin((Person) super.getObject());
    }

    @Override
    public void buildObject() {
        System.out.println("\n--- Adding a new person to collection if min ---\n");

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
}
