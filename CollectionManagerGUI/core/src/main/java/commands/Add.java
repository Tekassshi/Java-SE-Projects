package commands;

import data.*;
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
    public void buildObject(PersonParamsContainer container) {

        Person person = new Person();

        person.setName(container.getName());
        Coordinates coordinates = new Coordinates(Long.parseLong(container.getxCoord()),
                Long.parseLong(container.getyCoord()));
        person.setCoordinates(coordinates);
        person.setCreationDate(ZonedDateTime.now());
        person.setHeight(Integer.parseInt(container.getHeight()));
        person.setWeight(Float.parseFloat(container.getWeight()));
        person.setEyeColor(Color.valueOf(container.getEyeColor()));
        person.setNationality(Country.valueOf(container.getNationality().toUpperCase()));
        Location location = new Location(
                Integer.parseInt(container.getxLooc()),
                Float.parseFloat(container.getyLooc()),
                Double.parseDouble(container.getzLooc()));
        person.setLocation(location);

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
    public String execute(String username) throws IOException {
        return super.getCollectionManager().add(username, (Person) super.getObject());
    }
}