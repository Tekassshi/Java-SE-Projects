package commands;

import data.*;
import interfaces.AssemblableCommand;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;

/**
 * Class for "update" command. Updates all person fields from collection with given "id" value.
 * */
public class UpdateId extends AbstractCommand implements CommandWithArg, AssemblableCommand {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public UpdateId(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * @param arg person "id" value in String representation.
     * */
    @Override
    public void setArg(String arg) {
        super.setArgument(arg);
    }

    @Override
    public void buildObject(PersonParamsContainer container) {
        Person person = new Person();

        person.setId(container.getId());
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
        System.out.println("\nUpdating element with id = " + super.getArgument() + "\n");
        Person person = new Person();

        person.setId(Integer.parseInt(super.getArgument()));
        person.setName(InputManager.readNameScript(reader));
        person.setCoordinates(InputManager.readCoordinatesScript(reader));
        person.setHeight(InputManager.readHeightScript(reader));
        person.setWeight(InputManager.readWeightScript(reader));
        person.setEyeColor(InputManager.readEyeColorScript(reader));
        person.setNationality(InputManager.readNationalityScript(reader));
        person.setLocation(InputManager.readLocationScript(reader));

        super.setObject(person);
    }

    @Override
    public String execute(String username) {
        return super.getCollectionManager().updateId(username, (Person) super.getObject());
    }
}
