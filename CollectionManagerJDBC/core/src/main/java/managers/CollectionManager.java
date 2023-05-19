package managers;

import commands.FilterByNationality;
import comparators.DefaultComparator;
import comparators.HeightComparator;
import data.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
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

    private Person getPersonFromRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        long coord_x = rs.getLong("coord_x");
        long coord_y = rs.getLong("coord_y");

        Timestamp creation_date_ts = rs.getTimestamp("creation_date");
        LocalDateTime localDateTime = creation_date_ts.toLocalDateTime();
        ZonedDateTime creation_date = localDateTime.atZone(ZoneId.systemDefault());

        Integer height = rs.getInt("height");
        Float weight = rs.getFloat("weight");
        Color eye_color = Color.valueOf(rs.getString("color_name"));
        Country nationality = Country.valueOf(rs.getString("country_name"));
        Integer location_x = rs.getInt("location_x");
        Float location_y = rs.getFloat("location_y");
        Double location_z = rs.getDouble("location_z");

        Coordinates coordinates = new Coordinates(coord_x, coord_y);
        Location location = new Location(location_x, location_y, location_z);

        Person person = new Person.Builder()
                .id(id)
                .name(name)
                .coordinates(coordinates)
                .dateTime(creation_date)
                .height(height)
                .weight(weight)
                .eyeColor(eye_color)
                .nationality(nationality)
                .location(location)
                .build();

        return person;
    }

    private ArrayDeque<Person> getResultSetData(ResultSet rs) throws SQLException, IllegalArgumentException {
        ArrayDeque<Person> result = new ArrayDeque<>();

        while (rs.next()) {
            Person person = getPersonFromRow(rs);
            result.add(person);
        }
        return result;
    }

    private ResultSet getCompleteDataFromDb() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(
                "SELECT * FROM " +
                        "collection " +
                        "JOIN color on collection.eye_color = color.color_id " +
                        "JOIN country on collection.nationality = country.country_id");
        return set;
    }

    public void loadCollection() throws SQLException {
        collection = getResultSetData(getCompleteDataFromDb());
        defaultSort();
        LOGGER.info("Collection was loaded successfully");
    }

    private int getColorKeyFromTable(String color) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT color_id FROM color WHERE color_name = ?;");

        statement.setString(1, color);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int getCountryKeyFromTable(String country) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT country_id FROM country WHERE country_name = ?;");

        statement.setString(1, country);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private void insertPersonQuery(Person person) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO collection(name, coord_x, coord_y, creation_date, height, weight, eye_color, " +
                        "nationality, location_x, location_y, location_z)" +
                        "values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        int color_id = getColorKeyFromTable(person.getEyeColor().toString());
        int nationality = getCountryKeyFromTable(person.getNationality().toString());

        statement.setString(1, person.getName());
        statement.setLong(2, person.getCoordinates().getX());
        statement.setLong(3, person.getCoordinates().getY());

        ZonedDateTime tmp = person.getCreationDate();
        statement.setTimestamp(4, Timestamp.valueOf(tmp.toLocalDateTime()));

        statement.setInt(5, person.getHeight());
        statement.setFloat(6, person.getWeight());
        statement.setInt(7, color_id);
        statement.setInt(8, nationality);
        statement.setInt(9, person.getLocation().getX());
        statement.setFloat(10, person.getLocation().getY());
        statement.setDouble(11, person.getLocation().getZ());

        statement.execute();
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
        try {
            insertPersonQuery(person);
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "Person was added successfully!\n" + ANSI_RESET;
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
        }
    }

    /**
     * Method for executing "add_if_min" user command.
     * @see commands.AddIfMin
     * @param person "Person" class object to add to current collection.
     * */
    public String addIfMin(Person person) {

        Person toCompare = collection.peekFirst();
        DefaultComparator defaultComparator = new DefaultComparator();
        int compareRes = defaultComparator.compare(person, toCompare);

        if (compareRes >= 0) {
            String s = "";
            s += ANSI_RED + "\nYour element value is bigger or the same " +
                    "than min element in collection\n" + ANSI_RESET;
            s += ANSI_RED + "Element will not be recorded.\n" + ANSI_RESET;
            return s;
        }

        try {
            insertPersonQuery(person);
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "Person was added successfully!\n" + ANSI_RESET;
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
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

        try {
            if (!isIdExist((int) person.getId())) {
                String s = ANSI_RED + "\nId, that you want to update, doesn't exist!" + ANSI_RESET;
                s += ANSI_RED + "\nTry again\n" + ANSI_RESET;
                return s;
            }
            updatePersonQuery(person);
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "\nPerson with id = " + person.getId() + " was successfully updated!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
        }
    }

    private void updatePersonQuery(Person person) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE collection SET " +
                        "name = ?," +
                        "coord_x = ?," +
                        "coord_y = ?," +
                        "creation_date = ?," +
                        "height = ?," +
                        "weight = ?," +
                        "eye_color = ?," +
                        "nationality = ?," +
                        "location_x = ?," +
                        "location_y = ?," +
                        "location_z = ? " +
                        "WHERE id = ?;"
        );

        int color_id = getColorKeyFromTable(person.getEyeColor().toString());
        int nationality = getCountryKeyFromTable(person.getNationality().toString());

        statement.setString(1, person.getName());
        statement.setLong(2, person.getCoordinates().getX());
        statement.setLong(3, person.getCoordinates().getY());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        statement.setTimestamp(4, currentTime);

        statement.setInt(5, person.getHeight());
        statement.setFloat(6, person.getWeight());
        statement.setInt(7, color_id);
        statement.setInt(8, nationality);
        statement.setInt(9, person.getLocation().getX());
        statement.setFloat(10, person.getLocation().getY());
        statement.setDouble(11, person.getLocation().getZ());
        statement.setInt(12, (int) person.getId());

        statement.executeUpdate();
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

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM collection WHERE id = ?;"
            );
            statement.setInt(1, id);
            int res = statement.executeUpdate();

            if (res == 0) {
                String s = "";
                s += ANSI_RED + "\nPerson with given id value doesn't exist!" + ANSI_RESET;
                s += ANSI_RED + "Try again.\n" + ANSI_RESET;
                return s;
            }
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "\nPerson with id = " + id + " was successfully removed!\n" + ANSI_RESET;
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
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

        try {
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE collection RESTART IDENTITY");
            loadCollection();
            return ANSI_GREEN + "\nCollection was successfully cleared.\n" + ANSI_RESET;
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
        }
    }

    /**
     * Method for sorting current collection using "DefaultComparator" comparator class.
     * @see DefaultComparator
     * */
    private void defaultSort(){
        collection = collection.stream().sorted(new DefaultComparator())
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * Method for executing "head" user command.
     * @see commands.Head
     * */
    public String head(){
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

        try {
            DefaultComparator comparator = new DefaultComparator();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM collection WHERE id = ?;");

            for (Person toCompare: collection){
                if (comparator.compare(person, toCompare) < 0) {
                    statement.setInt(1, (int) toCompare.getId());
                    statement.execute();
                }
            }
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET;
        }
        catch (SQLException e){
            LOGGER.warn("Error with database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
        }
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

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM collection WHERE nationality = ?;");

            int nationalityInd = getCountryKeyFromTable(nationality.toString());
            statement.setInt(1, nationalityInd);
            statement.execute();
            loadCollection();
            defaultSort();
            return ANSI_GREEN + "\nPersons have been successfully removed!\n" + ANSI_RESET;
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return ANSI_RED + "Error updating, inserting or loading data from database.\n" + ANSI_RESET;
        }
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
    public boolean isIdExist(Integer id) throws SQLException{
       PreparedStatement statement = connection.prepareStatement(
               "SELECT FROM collection where id = ?;");
       statement.setInt(1, id);
       ResultSet set = statement.executeQuery();

       if (!set.isBeforeFirst())
           return false;
       else
           return true;
    }

    /**
     * Method for executing "info" user command.
     * @see commands.Info
     * */
    public String info(){
        String s = "";
        s += ANSI_GREEN + "\n--- Collection info ---\n" + ANSI_RESET;
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