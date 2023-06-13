package managers;

import comparators.DefaultComparator;
import comparators.HeightComparator;
import data.Country;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import data.Person;
import data.UserCollection;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.time.ZonedDateTime;
import java.util.*;

/** Class which contains current many fields for working with current collection. It also contains
 * all methods for executing user commands.
 * @see UserCollection
 * @see ClientManager
 * */
public class CollectionManager {
    private Deque<Person> collection = new ArrayDeque<>();
    private UserCollection collection_wrapper = new UserCollection();
    private String collection_path;
    private boolean is_path_exist = false;

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Long id = Long.valueOf(1);

    /**
     * Method for checking environmental variable to get path for collection and set it to class field.
     * */
    public void getCollectionPath() throws IOException {
        String path = System.getenv("Lab5_collection");
        File out_file;

        if (path == null || !Files.exists(Path.of(path))) {
            // Unix
            collection_path = System.getProperty("user.dir") + "/Collection.xml";
            // Windows
            // collection_path = System.getProperty("user.dir") + "\\Collection.xml";

            try {
                out_file = new File(collection_path);
                out_file.createNewFile();
                collection_wrapper.setInit_date(ZonedDateTime.now());

            } catch (IOException e) {
                System.out.println(ANSI_RED + "\nError creating new file. Try again.\n" + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "Wrong environmental variable value, or it's doesn't exist." + ANSI_RESET);
            System.out.println(ANSI_GREEN + "New collection was created: \"" + collection_path + "\"\n" + ANSI_RESET);
            return;
        }

        collection_path = path;
        is_path_exist = true;

        out_file = new File(collection_path);

        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(out_file.toPath(), perms);

        System.out.println(ANSI_GREEN + "Path to collection was successfully loaded: \""
                + collection_path + "\"\n" + ANSI_RESET);
    }

    /**
     * Method for executing "help" user command.
     * @see commands.Help
     * */
    public void help() {
        StringBuilder s = new StringBuilder(
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
                        "\"save\" - сохранить коллекцию в файл\n" +
                        "\"exit\" - завершить программу (без сохранения в файл)\n");

        System.out.println("\n--- Reference for all commands ---\n");
        System.out.println(s);
    }

    /**
     * Method for executing "show" user command.
     * @see commands.Show
     * */
    public void show() {
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        System.out.println("\n--- Collection ---\n");

        for (Person person : collection) {

            System.out.println("Id: " + person.getId());
            System.out.println("Name: " + person.getName());
            System.out.println("Coordinates: x = \"" + person.getCoordinates().getX() + "\", y = \"" +
                    person.getCoordinates().getY() + "\"");
            System.out.println("Creation date: " + person.getCreationDate());
            System.out.println("Height: " + person.getHeight());
            System.out.println("Weight: " + person.getWeight());
            System.out.println("Eye color: " + person.getEyeColor());
            System.out.println("Nationality: " + person.getNationality());
            System.out.println("Location: x = \"" + person.getLocation().getX() + "\", y = \"" +
                    person.getLocation().getY() + "\", z = \"" + person.getLocation().getZ() + "\"");
            System.out.println("");
        }
    }

    /**
     * Method is used to output given "Person" object to stream.
     * @see Person
     * */
    public void showPerson(Person person){
        System.out.println("\nPerson\n");
        System.out.println("Id: " + person.getId());
        System.out.println("Name: " + person.getName());
        System.out.println("Coordinates: x = \"" + person.getCoordinates().getX() + "\", y = \"" +
                person.getCoordinates().getY() + "\"");
        System.out.println("Creation date: " + person.getCreationDate());
        System.out.println("Height: " + person.getHeight());
        System.out.println("Weight: " + person.getWeight());
        System.out.println("Eye color: " + person.getEyeColor());
        System.out.println("Nationality: " + person.getNationality());
        System.out.println("Location: x = \"" + person.getLocation().getX() + "\", y = \"" +
                person.getLocation().getY() + "\", z = \"" + person.getLocation().getZ() + "\"");
        System.out.println("");
    }

    /**
     * Method for executing "add" user command.
     * @see commands.Add
     * @param person "Person" class object to add to current collection.
     * */
    public void add(Person person) {
        person.setId(id);
        id++;

        collection.add(person);
        defaultSort();
        System.out.println(ANSI_GREEN + "Person was added successfully!\n" + ANSI_RESET);
    }

    /**
     * Method for executing "add_if_min" user command.
     * @see commands.AddIfMin
     * @param person "Person" class object to add to current collection.
     * */
    public void addIfMin(Person person) {
        person.setId(id);
        id += 1;

        collection.add(person);
        defaultSort();

        if (person != collection.peekFirst()) {
            collection.remove(person);
            System.out.println(ANSI_RED + "\nYour element value is bigger or the same " +
                    "than min element in collection" + ANSI_RESET);
            System.out.println(ANSI_RED + "Element will not be recorded.\n" + ANSI_RESET);
        }
        else {
            System.out.println(ANSI_GREEN + "\nElement was recorded successfully!\n" + ANSI_RESET);
        }
    }

    /**
     * Method for executing "update" user command.
     * @see commands.UpdateId
     * @param person "Person" class object for updating in node with same "id" field in current collection.
     * */
    public void updateId(Person person) {
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        Person to_remove = null;
        for (Person tmp:collection){
            if (tmp.getId() == person.getId()){
                to_remove = tmp;
                break;
            }
        }
        ZonedDateTime old = to_remove.getCreationDate();
        person.setCreationDate(old);

        collection.remove(to_remove);
        collection.add(person);

        defaultSort();
        System.out.println(ANSI_GREEN + "\nPerson with id = " + person.getId() + " was successfully updated!\n");
    }

    /**
     * Method for executing "remove_by_id" user command.
     * @see commands.RemoveById
     * @param id node "id" field value that we should remove from current collection.
     * */
    public void removeById(int id) {
        int is_exist = 0;

        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        for (Person person : collection) {
            if (person.getId() == id) {
                collection.remove(person);
                is_exist = 1;
                break;
            }
        }
        if (is_exist == 0) {
            System.out.println(ANSI_RED + "\nPerson with given id value doesn't exist!" + ANSI_RESET);
            System.out.println(ANSI_RED + "Try again.\n" + ANSI_RESET);
        } else {
            defaultSort();
            System.out.println(ANSI_GREEN + "\nPerson with id = " + id + " was successfully removed!\n" + ANSI_RESET);
        }
    }

    /**
     * Method for executing "clear" user command.
     * @see commands.Clear
     * */
    public void clear() {
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        collection.removeAll(collection);
        id = Long.valueOf(1);
        System.out.println(ANSI_GREEN + "\nCollection was successfully cleared.\n" + ANSI_RESET);
    }

    /**
     * Method for executing "save" user command.
     * @see commands.Save
     * */
    public void save() {

        if (!Files.isWritable(Path.of(collection_path))) {
            System.out.println(ANSI_RED + "File is unwritable!" + ANSI_RESET);
            File file = new File("Collection_new.xml");

            try {
                file.createNewFile();
                collection_path = System.getProperty("user.dir") + "/Collection_new.xml";
                System.out.println(ANSI_GREEN + "Your collection data will be saved to new file " +
                        "\"Collection_new.xml\"" + ANSI_RESET);
            }
            catch (IOException e) {
                System.out.println(ANSI_RED + "\nError creating new file. Try again.\n" + ANSI_RESET);
                return;
            }
        }

        try (FileOutputStream fos = new FileOutputStream(collection_path);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {


            XmlMapper mapper = new XmlMapper();
            mapper.registerModule(new JavaTimeModule());

            mapper.getFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            collection_wrapper.setCollection(collection);
            mapper.writeValue(writer, collection_wrapper);

            System.out.println(ANSI_GREEN + "\nYour collection was successfully saved.\n" + ANSI_RESET);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for loading collection from XML file.
     * */
    public void load() {
        if (!is_path_exist)
            return;

        File file = new File(collection_path);
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());

        UserCollection tmp;

        try {
            tmp = mapper.readValue(file, UserCollection.class);
        }
        catch (InvalidFormatException e){
            System.out.println(ANSI_RED + "Wrong data type in \"" + collection_path + "\"." + ANSI_RESET);
            System.out.println(ANSI_RED + e.getLocation().toString().replaceAll("\\[|\\]", "")
                    + ANSI_RESET + "\n");
            System.out.println(ANSI_GREEN + "A new empty collection has been created.\n" + ANSI_RESET);
            tmp = new UserCollection();
            tmp.setInit_date(ZonedDateTime.now());
            collection = tmp.getCollection();
            collection_wrapper = tmp;
            return;
        }
        catch (IOException e){
            System.out.println(ANSI_RED + "Wrong XML format \"" + collection_path + "\"." + ANSI_RESET);
            System.out.println(ANSI_GREEN + "A new empty collection has been created.\n" + ANSI_RESET);
            tmp = new UserCollection();
            tmp.setInit_date(ZonedDateTime.now());
            collection = tmp.getCollection();
            collection_wrapper = tmp;
            return;
        }

        collection = tmp.getCollection();
        collection_wrapper = tmp;

        System.out.println("\nChecking loaded collection data...\n");
        checkId();
        setNextId();
        defaultSort();
    }

    /**
     * Method for sorting current collection using "DefaultComparator" comparator class.
     * @see DefaultComparator
     * */
    private void defaultSort(){
        List<Person> tmp = new ArrayList<>(collection);
        Collections.sort(tmp, new DefaultComparator());
        collection = new ArrayDeque<>(tmp);
    }

    /**
     * Method for sorting current collection using "HeightComparator" comparator class.
     * @see HeightComparator
     * */
    private void sortByHeight(){
        List<Person> tmp = new ArrayList<>(collection);
        Collections.sort(tmp, new HeightComparator());
        collection = new ArrayDeque<>(tmp);
    }

    /**
     * Method for same "id" field existence check. Removes nodes from current collection that have an already
     * existing "id" field value.
     * */
    private void checkId(){
        Map<Long, Integer> map = new HashMap<>();
        Long max = Long.valueOf(0);
        List<Person> to_delete = new LinkedList<>();

        for (Person person : collection){
            Long tmp_id = person.getId();
            map.put(tmp_id, map.getOrDefault(tmp_id, 0) + 1);
            max = Math.max(max, tmp_id);

            if (map.get(person.getId()) > 1) {
                System.out.println(ANSI_RED + "Person with id: " + tmp_id + " is already exists." + ANSI_RESET);
                System.out.println(ANSI_RED + "Node will be removed.\n" + ANSI_RESET);
                to_delete.add(person);
            }
        }

        for (Person person: to_delete)
            collection.remove(person);
    }

    /**
     * Method count a number of nodes in current collection and set "id" value that should be set for new next node.
     * */
    private void setNextId(){
        Long max = Long.valueOf(0);

        for (Person person : collection){
            max = Math.max(max, person.getId());
        }
        id = max + 1;
    }

    /**
     * Method for executing "head" user command.
     * @see commands.Head
     * */
    public void head(){
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }
        showPerson(collection.peekFirst());
    }

    /**
     * Method for executing "remove_greater" user command.
     * @see commands.RemoveGreater
     * @param person "Person" class object after which we should remove all nodes.
     * */
    public void removeGreater(Person person) {
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }
        person.setId(id);
        id += 1;

        collection.add(person);
        defaultSort();

        boolean should_delete = false;

        List<Person> to_delete = new ArrayList<>();
        for (Person tmp : collection){
            if (tmp == person)
                should_delete = true;
            if (should_delete)
                to_delete.add(tmp);
        }
        for (Person tmp:to_delete)
            collection.remove(tmp);

        System.out.println(ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET);
    }

    /**
     * Method for executing "remove_all_by_nationality" user command.
     * @see commands.RemoveAllByNationality
     * @param nationality "Country" class object with which we should remove all nodes in current collection.
     * */
    public void removeAllByNationality(Country nationality){
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        List<Person> to_delete = new ArrayList<>();
        for (Person tmp : collection){
            if (tmp.getNationality().equals(nationality))
                to_delete.add(tmp);
        }
        for (Person tmp:to_delete)
            collection.remove(tmp);

        System.out.println(ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET);
    }

    /**
     * Method for executing "filter_by_nationality" user command.
     * @see commands.FilterByNationality
     * @param nationality "Country" class object with which we should output all nodes in current collection.
     * */
    public void filterByNationality(Country nationality){
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }

        for (Person person:collection){
            if (person.getNationality().equals(nationality))
                showPerson(person);
        }
    }

    /**
     * Method for executing "print_field_descending_height" user command.
     * @see commands.PrintFieldDescendingHeight
     * */
    public void printFieldDescendingHeight(){
        if (collection.size() == 0) {
            System.out.println(ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET);
            return;
        }
        sortByHeight();
        System.out.println();
        for (Person person:collection){
            System.out.println("id \"" + person.getId() + "\", height = " + person.getHeight());
        }
        System.out.println();
    }

    /** Utility method for checking given id on existence in current collection.
     * @param id "id" field value to checking.
     * */
    public boolean isIdExist(int id){
        for (Person person:collection){
            if (person.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * Method for executing "info" user command.
     * @see commands.Info
     * */
    public void info(){
        System.out.println(ANSI_GREEN + "\n--- Collection info ---" + ANSI_RESET);
        System.out.println("Date of initialization: " + collection_wrapper.getInit_date());
        System.out.println("Collection type: ArrayDeque<Person>");
        System.out.println("Number of elements: " + collection.size() + "\n");
    }
}