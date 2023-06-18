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
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

import org.slf4j.Logger;

/** Class which contains current many fields for working with current collection. It also contains
 * all methods for executing user commands.
 * @see UserCollection
 * */
public class CollectionManager {
    private Deque<Person> collection = new LinkedBlockingDeque<>();
    private Connection connection;

    private Logger LOGGER;

    public CollectionManager(Connection connection, Logger logger){
        this.connection = connection;
        this.LOGGER = logger;
    }

    private synchronized Person getPersonFromRow(ResultSet rs) throws SQLException {
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

        String username = rs.getString("username");

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
                .username(username)
                .build();

        return person;
    }

    private synchronized ArrayDeque<Person> getResultSetData(ResultSet rs) throws SQLException, IllegalArgumentException {
        ArrayDeque<Person> result = new ArrayDeque<>();

        while (rs.next()) {
            Person person = getPersonFromRow(rs);
            result.add(person);
        }
        return result;
    }

    private synchronized ResultSet getCompleteDataFromDb() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(
                "SELECT * FROM " +
                        "collection " +
                        "JOIN color on collection.eye_color = color.color_id " +
                        "JOIN country on collection.nationality = country.country_id " +
                        "JOIN users on collection.user_id = users.id;");
        return set;
    }

    public synchronized void loadCollection() throws SQLException {
        collection = getResultSetData(getCompleteDataFromDb());
        defaultSort();
        LOGGER.info("Collection data was updated successfully");
    }

    public synchronized int getUserId(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE username = ?;"
        );
        statement.setString(1, username);
        ResultSet set = statement.executeQuery();
        set.next();
        return set.getInt("id");
    }

    private synchronized int getColorKeyFromTable(String color) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT color_id FROM color WHERE color_name = ?;");

        statement.setString(1, color);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private synchronized int getCountryKeyFromTable(String country) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT country_id FROM country WHERE country_name = ?;");

        statement.setString(1, country);
        ResultSet rs = statement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private synchronized void insertPersonQuery(String username, Person person) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO collection(name, coord_x, coord_y, creation_date, height, weight, eye_color, " +
                        "nationality, location_x, location_y, location_z, user_id)" +
                        "values " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

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
        statement.setInt(12, getUserId(username));

        statement.execute();
    }

    /**
     * Method for executing "show" user command.
     * @see commands.Show
     * */
    public synchronized String show() {
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
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
    public synchronized String showPerson(Person person){
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
                person.getLocation().getY() + "\", z = \"" + person.getLocation().getZ() + "\"\n";
        s += "Created by: " + person.getUsername() + "\n\n";
        return s;
    }

    /**
     * Method for executing "add" user command.
     * @see commands.Add
     * @param person "Person" class object to add to current collection.
     * */
    public synchronized String add(String username, Person person) {
        try {
            insertPersonQuery(username, person);
            loadCollection();
            defaultSort();
            return "Person was added successfully!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    /**
     * Method for executing "add_if_min" user command.
     * @see commands.AddIfMin
     * @param person "Person" class object to add to current collection.
     * */
    public synchronized String addIfMin(String username, Person person) {

        Person toCompare = collection.peekFirst();
        DefaultComparator defaultComparator = new DefaultComparator();
        int compareRes = defaultComparator.compare(person, toCompare);

        if (compareRes >= 0) {
            String s = "";
            s += "\nYour element value is bigger or the same " +
                    "than min element in collection\n";
            s += "Element will not be recorded.\n";
            return s;
        }

        try {
            insertPersonQuery(username, person);
            loadCollection();
            defaultSort();
            return "Person was added successfully!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    private synchronized boolean isOwner(String username, int id) throws SQLException {
        int userId = getUserId(username);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM collection WHERE user_id = ? AND id = ?;"
        );

        statement.setInt(1, userId);
        statement.setInt(2, id);
        ResultSet rs = statement.executeQuery();

        if (rs.isBeforeFirst())
            return true;
        return false;
    }

    /**
     * Method for executing "update" user command.
     * @see commands.UpdateId
     * @param person "Person" class object for updating in node with same "id" field in current collection.
     * */
    public synchronized String updateId(String username, Person person) {
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }

        try {
            if (!isIdExist((int) person.getId())) {
                String s = "\nId, that you want to update, doesn't exist!";
                s += "\nTry again\n";
                return s;
            }
            else if (!isOwner(username, (int) person.getId())) {
                String s = "\nYou can edit only your own records!";
                s += "\nTry again.\n";
                return s;
            }

            updatePersonQuery(person);
            loadCollection();
            defaultSort();
            return "\nPerson with id = " + person.getId() + " was successfully updated!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error inserting or load data from database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    private synchronized void updatePersonQuery(Person person) throws SQLException {
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
    public synchronized String removeById(String username, int id) {
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }

        try {
            if (!isOwner(username, id)) {
                String s = "\nYou can edit only your own records!";
                s += "\nTry again.\n";
                return s;
            }

            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM collection WHERE id = ?;"
            );
            statement.setInt(1, id);
            int res = statement.executeUpdate();

            if (res == 0) {
                String s = "";
                s += "\nPerson with given id value doesn't exist!";
                s += "Try again.\n";
                return s;
            }

            loadCollection();
            defaultSort();
            return "\nPerson with id = " + id + " was successfully removed!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    /**
     * Method for executing "clear" user command.
     * @see commands.Clear
     * */
    public synchronized String clear(String username) {
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM collection WHERE user_id = ?;"
            );
            statement.setInt(1, getUserId(username));

            statement.execute();
            loadCollection();
            return "\nAll your own records successfully cleared.\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    /**
     * Method for sorting current collection using "DefaultComparator" comparator class.
     * @see DefaultComparator
     * */
    private synchronized void defaultSort(){
        collection = collection.stream().sorted(new DefaultComparator())
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * Method for executing "head" user command.
     * @see commands.Head
     * */
    public synchronized String head(){
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }
        return showPerson(collection.peekFirst());
    }

    /**
     * Method for executing "remove_greater" user command.
     * @see commands.RemoveGreater
     * @param person "Person" class object after which we should remove all nodes.
     * */
    public synchronized String removeGreater(String username, Person person) {
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }

        try {
            DefaultComparator comparator = new DefaultComparator();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM collection WHERE id = ?;");

            for (Person toCompare: collection){
                if (comparator.compare(person, toCompare) < 0 && toCompare.getUsername().equals(username)) {
                    statement.setInt(1, (int) toCompare.getId());
                    statement.execute();
                }
            }
            loadCollection();
            defaultSort();
            return "\nYour own records have been successfully removed!\n";
        }
        catch (SQLException e){
            LOGGER.warn("Error with database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    /**
     * Method for executing "remove_all_by_nationality" user command.
     * @see commands.RemoveAllByNationality
     * @param nationality "Country" class object with which we should remove all nodes in current collection.
     * */
    public synchronized String removeAllByNationality(String username, Country nationality){
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
        }

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM collection WHERE nationality = ? AND user_id = ?;");

            int nationalityInd = getCountryKeyFromTable(nationality.toString());
            statement.setInt(1, nationalityInd);
            statement.setInt(2, getUserId(username));
            statement.execute();
            loadCollection();
            defaultSort();
            return "\nYour own records have been successfully removed!\n";
        }
        catch (SQLException e) {
            LOGGER.warn("Error with database.");
            return "Error updating, inserting or loading data from database.\n";
        }
    }

    /**
     * Method for executing "filter_by_nationality" user command.
     * @see FilterByNationality
     * @param nationality "Country" class object with which we should output all nodes in current collection.
     * */
    public synchronized String filterByNationality(String username, Country nationality){
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
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
    public synchronized String printFieldDescendingHeight(){
        if (collection.size() == 0) {
            return "\nCollection is empty!\n";
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
    public synchronized boolean isIdExist(Integer id) throws SQLException{
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
    public synchronized String info(){
        String s = "";
        s += "Collection type: ArrayDeque<Person>" + "\n";
        s += "Number of elements: " + collection.size() + "\n\n";
        return s;
    }

    public synchronized ArrayList<Person> getFilteredNationalityCollection(Country nationality){
        ArrayList<Person> tmp = new ArrayList<>();
        collection.stream().filter(x -> x.getNationality().equals(nationality)).forEach(x -> tmp.add(x));

        return tmp;
    }

    public synchronized ArrayList<Person> getDescendingHeightCollection(){
        ArrayList<Person> tmp = new ArrayList<>(collection);
        collection.stream().sorted(new HeightComparator()).forEach(x -> tmp.add(x));

        return tmp;
    }

    public synchronized long getCollectionSize(){
        return collection.size();
    }

    public synchronized ArrayDeque getCollection(){
        return (ArrayDeque) collection;
    }

    public synchronized void setConnection(Connection connection) {
        this.connection = connection;
    }
}