package commands;

import data.Person;
import data.PersonParamsContainer;
import interfaces.AssemblableCommand;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Class for "update" command. Updates all person fields from collection with given "id" value.
 * */
public class UpdateId extends AbstractCommand implements CommandWithArg, AssemblableCommand {

    private SocketChannel sc = null;

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
        super.setArgument(InputManager.readId(arg));
    }

    @Override
    public void buildObject(PersonParamsContainer container) {
//        System.out.println("\nEnter data to update id = " + super.getArgument() + "\n");
//
//        Person person = new Person();
//
//        person.setId(Integer.parseInt(super.getArgument()));
//        person.setName(InputManager.readName());
//        person.setCoordinates(InputManager.readCoordinates());
//        person.setHeight(InputManager.readHeight());
//        person.setWeight(InputManager.readWeight());
//        person.setEyeColor(InputManager.readEyeColor());
//        person.setNationality(InputManager.readNationality());
//        person.setLocation(InputManager.readLocation());
//
//        super.setObject(person);
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
