package commands;

import interfaces.Command;
import managers.CollectionManager;

/**
 * Class for "help" command. Command outputs reference for all available commands.
 * */
public class Help extends AbstractCommand implements Command {
    /**
     *
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public Help(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public String execute(String username) {
        StringBuilder s = new StringBuilder("");
        s.append("\n--- Reference for all commands ---\n");
        s.append(
                "\"help\" - вывести справку по доступным командам\n" +
                        "\"info\" - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, " +
                        "количество элементов и т.д.)\n" +
                        "\"show\" - вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "\"add\" - добавить новый элемент в коллекцию\n" +
                        "\"update id\" - обновить значение элемента коллекции, id которого равен заданному\n" +
                        "\"remove_by_id id\" - удалить элемент из коллекции по его id\n" +
                        "\"clear\" - очистить коллекцию\n" +
                        "\"execute_script file_name\" - считать и исполнить скрипт из указанного файла. " +
                        "В скрипте содержатся команды в таком же виде, " +
                        "в котором их вводит пользователь в интерактивном режиме.\n" +
                        "\"head\" - вывести первый элемент коллекции\n" +
                        "\"add_if_min\" - добавить новый элемент в коллекцию, если его значение меньше, " +
                        "чем у наименьшего элемента этой коллекции\n" +
                        "\"remove_greater\" - удалить из коллекции все элементы, превышающие заданный\n" +
                        "\"remove_all_by_nationality nationality\" - удалить из коллекции все элементы, значение поля " +
                        "nationality которого эквивалентно заданному\n" +
                        "\"filter_by_nationality nationality\" - вывести элементы, значение поля nationality которых " +
                        "равно заданному\n" +
                        "\"print_field_descending_height\" - вывести значения поля " +
                        "height всех элементов в порядке убывания\n" +
                        "\"exit\" - завершить программу (без сохранения в файл)\n");

        return s.toString();
    }
}
