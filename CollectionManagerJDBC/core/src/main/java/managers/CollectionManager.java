package managers;

import commands.FilterByNationality;
import comparators.DefaultComparator;
import comparators.HeightComparator;
import data.Country;

import data.Person;
import data.UserCollection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;

/** Class which contains current many fields for working with current collection. It also contains
 * all methods for executing user commands.
 * @see UserCollection
 * */
public class CollectionManager {
    private Deque<Person> collection = new ArrayDeque<>();
    private Connection connection;

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Logger LOGGER;

    public CollectionManager(Connection connection, Logger logger){
        this.connection = connection;
        this.LOGGER = logger;
    }

    public void loadCollection() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("" +
                "SELECT * FROM collection");
    }

    /**
     * Method for executing "show" user command.
     * @see commands.Show
     * */
    public String show() {
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        final StringBuilder builder = new StringBuilder("");
        builder.append("\n--- Collection ---\n");
        collection.stream().forEach(x -> builder.append(showPerson(x)));

        return builder.toString();
    }

    /**
     * Method is used to output given "Person" object to stream.
     * @see Person
     * */
    public String showPerson(Person person){
        String s = "";
        s += "Id: " + person.getId() + "\n";
        s += "Name: " + person.getName() + "\n";
        s += "Coordinates: x = \"" + person.getCoordinates().getX() + "\", y = \"" +
                person.getCoordinates().getY() + "\"\n";
        s += "Creation date: " + person.getCreationDate() + "\n";
        s += "Height: " + person.getHeight() + "\n";
        s += "Weight: " + person.getWeight() + "\n";
        s += "Eye color: " + person.getEyeColor() + "\n";
        s += "Nationality: " + person.getNationality() + "\n";
        s += "Location: x = \"" + person.getLocation().getX() + "\", y = \"" +
                person.getLocation().getY() + "\", z = \"" + person.getLocation().getZ() + "\"\n\n";
        return s;
    }

    /**
     * Method for executing "add" user command.
     * @see commands.Add
     * @param person "Person" class object to add to current collection.
     * */
    public String add(Person person) {
//        person.setId(id);
//        id++;

        collection.add(person);
        defaultSort();
        return ANSI_GREEN + "Person was added successfully!\n" + ANSI_RESET;
    }

    /**
     * Method for executing "add_if_min" user command.
     * @see commands.AddIfMin
     * @param person "Person" class object to add to current collection.
     * */
    public String addIfMin(Person person) {
//        person.setId(id);
//        id += 1;

        collection.add(person);
        defaultSort();

        if (person != collection.peekFirst()) {
            collection.remove(person);
            String s = "";
            s += ANSI_RED + "\nYour element value is bigger or the same " +
                    "than min element in collection\n" + ANSI_RESET;
            s += ANSI_RED + "Element will not be recorded.\n" + ANSI_RESET;
            return s;
        }
        else {
            return ANSI_GREEN + "\nElement was recorded successfully!\n" + ANSI_RESET;
        }
    }

    /**
     * Method for executing "update" user command.
     * @see commands.UpdateId
     * @param person "Person" class object for updating in node with same "id" field in current collection.
     * */
    public String updateId(Person person) {
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        if (!isIdExist(person.getId())) {
            String s = ANSI_RED + "\nId, that you want to update, doesn't exist!" + ANSI_RESET;
            s += ANSI_RED + "\nTry again\n" + ANSI_RESET;
            return s;
        }

        Person to_remove = collection.stream().filter(x -> x.getId() ==
                person.getId()).toList().get(0);

        ZonedDateTime old = to_remove.getCreationDate();
        person.setCreationDate(old);

        collection.remove(to_remove);
        collection.add(person);

        defaultSort();
        return ANSI_GREEN + "\nPerson with id = " + person.getId() + " was successfully updated!\n";
    }

    /**
     * Method for executing "remove_by_id" user command.
     * @see commands.RemoveById
     * @param id node "id" field value that we should remove from current collection.
     * */
    public String removeById(int id) {

        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        Map<Boolean, List<Person>> partitions =
                collection.stream().collect(Collectors.partitioningBy(x -> x.getId() == id));
        collection = new ArrayDeque<>(partitions.get(false));

        if (partitions.get(true).size() == 0) {
            String s = "";
            s += ANSI_RED + "\nPerson with given id value doesn't exist!" + ANSI_RESET;
            s += ANSI_RED + "Try again.\n" + ANSI_RESET;
            return s;
        } else {
            defaultSort();
            return ANSI_GREEN + "\nPerson with id = " + id + " was successfully removed!\n" + ANSI_RESET;
        }
    }

    /**
     * Method for executing "clear" user command.
     * @see commands.Clear
     * */
    public String clear() {
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        collection.removeAll(collection);
//        id = Long.valueOf(1);
        return ANSI_GREEN + "\nCollection was successfully cleared.\n" + ANSI_RESET;
    }

    /**
     * Method for sorting current collection using "DefaultComparator" comparator class.
     * @see DefaultComparator
     * */
    private void defaultSort(){
        collection = collection.stream().sorted(new DefaultComparator())
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * Method for same "id" field existence check. Removes nodes from current collection that have an already
     * existing "id" field value.
     * */
    private void checkId(){
        if (collection.size() == 0)
            return;

        Map<Boolean, List<Person>> partitions = collection.stream().
                collect(Collectors.partitioningBy(distinctByKey(Person::getId)));

        partitions.get(false).stream().forEach(x ->
                LOGGER.warn(
                        "Person with id: " + x.getId() + " is already exists.\n" +
                        "Node will be removed.\n"));
        partitions.get(false).stream().forEach(x -> collection.remove(x));
    }

    /**
     * Method count a number of nodes in current collection and set "id" value that should be set for new next node.
     * */
    private void setNextId(){
//        if (collection.size() == 0){
//            id = Long.valueOf(1);
//            return;
//        }
//        id = collection.stream().max(Comparator.comparingLong(Person::getId)).get().getId() + 1;
    }

    /**
     * Method for executing "head" user command.
     * @see commands.Head
     * */
    public String  head(){
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }
        return showPerson(collection.peekFirst());
    }

    /**
     * Method for executing "remove_greater" user command.
     * @see commands.RemoveGreater
     * @param person "Person" class object after which we should remove all nodes.
     * */
    public String removeGreater(Person person) {
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

//        person.setId(id++);

        collection.add(person);

        DefaultComparator comparator = new DefaultComparator();
        collection = collection.stream().sorted(new DefaultComparator()).
                filter(x -> comparator.compare(x, person) < 0).collect(Collectors.toCollection(ArrayDeque::new));
        return ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET;
    }

    /**
     * Method for executing "remove_all_by_nationality" user command.
     * @see commands.RemoveAllByNationality
     * @param nationality "Country" class object with which we should remove all nodes in current collection.
     * */
    public String removeAllByNationality(Country nationality){
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        collection = collection.stream().filter(x -> x.getNationality() != nationality)
                .collect(Collectors.toCollection(ArrayDeque::new));

        return ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET;
    }

    /**
     * Method for executing "filter_by_nationality" user command.
     * @see FilterByNationality
     * @param nationality "Country" class object with which we should output all nodes in current collection.
     * */
    public String filterByNationality(Country nationality){
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        final StringBuilder builder = new StringBuilder("");
        collection.stream().filter(x -> x.getNationality().equals(nationality)).
                forEach(x -> builder.append(showPerson(x)));
        return builder.toString();
    }

    /**
     * Method for executing "print_field_descending_height" user command.
     * @see commands.PrintFieldDescendingHeight
     * */
    public String printFieldDescendingHeight(){
        if (collection.size() == 0) {
            return ANSI_RED + "\nCollection is empty!\n" + ANSI_RESET;
        }

        StringBuilder builder = new StringBuilder("");

        collection.stream().sorted(new HeightComparator())
                .forEach(x -> builder.append("id \"" + x.getId() + "\", height = " + x.getHeight() + "\n"));
        builder.append("\n");

        return builder.toString();
    }

    /** Utility method for checking given id on existence in current collection.
     * @param id "id" field value to checking.
     * */
    public boolean isIdExist(Long id){
       return collection.stream().anyMatch(x -> x.getId() == id);
    }

    /**
     * Method for executing "info" user command.
     * @see commands.Info
     * */
    public String info(){
        String s = "";
        s += ANSI_GREEN + "\n--- Collection info ---\n" + ANSI_RESET;
//        s += "Date of initialization: " + collection_wrapper.getInit_date() + "\n";
        s += "Collection type: ArrayDeque<Person>" + "\n";
        s += "Number of elements: " + collection.size() + "\n\n";
        return s;
    }

    public ArrayList<Person> getFilteredNationalityCollection(Country nationality){
        ArrayList<Person> tmp = new ArrayList<>();
        collection.stream().filter(x -> x.getNationality().equals(nationality)).forEach(x -> tmp.add(x));

        return tmp;
    }

    public ArrayList<Person> getDescendingHeightCollection(){
        ArrayList<Person> tmp = new ArrayList<>(collection);
        collection.stream().sorted(new HeightComparator()).forEach(x -> tmp.add(x));

        return tmp;
    }

    public long getCollectionSize(){
        return collection.size();
    }

    public ArrayDeque getCollection(){
        return (ArrayDeque) collection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}