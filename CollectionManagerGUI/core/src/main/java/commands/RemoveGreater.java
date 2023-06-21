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
 * Class for "remove_greater" command. Command removes all persons whose position in collection
 * is greater than given person.
 * */
public class RemoveGreater extends AbstractCommand implements Command, AssemblableCommand {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public RemoveGreater(CollectionManager collectionManager) {
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


    @Override
    public String execute(String username) {
        return super.getCollectionManager().removeGreater(username, (Person) super.getObject());
    }
}