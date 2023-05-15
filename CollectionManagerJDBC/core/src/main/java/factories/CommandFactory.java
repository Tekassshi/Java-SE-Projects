package factories;

import interfaces.Command;
import commands.*;
import commands.Head;
import managers.CollectionManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Factory class, that contains and returns objects of all available commands objects.
 * */
public class CommandFactory {
    /**
     * HashMap which contains name of command as key and object of that command as value.
     * */
    static Map<String, Command> commands = new HashMap<>();

    /**
     * @param value name of command to get.
     * @return class object that implements "Command" interface and matches given key.
     * @see Command
     * */
    public static Command getCommand(String value){
        return commands.getOrDefault(value, null);
    }

    public static Set<String> scripts = new HashSet<>();
    public static Set<String> longReplyCommands = new HashSet<>();

    /**
     * Setter method that setting "CollectionManager" object for all commands
     * @param collectionManager "CollectionManager" class object.
     * @see CollectionManager
     * */
    public static void setCollectionManager(CollectionManager collectionManager) {
        // Loading commands classes to general commands map
        commands.put("help", new Help(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("update", new UpdateId(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("execute_script", new ExecuteScript(collectionManager));
        commands.put("head", new Head(collectionManager));
        commands.put("add_if_min", new AddIfMin(collectionManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager));
        commands.put("remove_all_by_nationality", new RemoveAllByNationality(collectionManager));
        commands.put("filter_by_nationality", new FilterByNationality(collectionManager));
        commands.put("print_field_descending_height", new PrintFieldDescendingHeight(collectionManager));
    }

    public static void addLongReplyCommands(String... callName){
        for (String c: callName)
            longReplyCommands.add(c);
    }
}