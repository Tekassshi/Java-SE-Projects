package util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private Connection connection;
    private String url;
    private String schema;

    private String host;
    private int port;
    private String user;
    private String password;
    private static Logger logger = LoggerProvider.getLogger();

    public synchronized void setConnection(String credentialsFileName) throws IOException,
            SQLException {

        String dir = System.getProperty("user.dir");
        String filepath = dir + File.separator + credentialsFileName;

        File f = new File(filepath);

        FileReader fileReader = new FileReader(f);
        BufferedReader reader = new BufferedReader(fileReader);
        String property;
        try {
            while ((property = reader.readLine()) != null) {
                String[] tmp = property.split("=");

                if (tmp[0].equals("psqlHost"))
                    host = tmp[1];
                else if (tmp[0].equals("psqlPort"))
                    port = Integer.parseInt(tmp[1]);
                else if (tmp[0].equals("psqlUser"))
                    user = tmp[1];
                else if (tmp[0].equals("psqlPass"))
                    password = tmp[1];
            }
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            throw new SQLException();
        }

        url = "jdbc:postgresql://" + host + ":" + port + "/";
//        String dbName = "studs";
//        url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);

        if (user == null || password == null) {
            logger.error("Invalid database user or password.");
            throw new SQLException();
        }

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(url, properties);
        schema = connection.getSchema();
    }

    public synchronized void checkDatabase() throws SQLException {
        try {
            logger.info("Checking tables in database for existence.");
            if (!isColorTableExist()) {
                createColorTable();
                logger.warn("Table \"color\" didn't exist. It was created.");
            } {
                logger.info("Table \"color\" exists.");
            }
            if (!isCountryTableExist()) {
                logger.warn("Table \"country\" didn't exist. It was created.");
                createCountryTable();
            }
            else {
                logger.info("Table \"country\" exists.");
            }
            if (!isUsersTableExist()) {
                logger.warn("Table \"users\" didn't exist. It was created.");
                createUsersTable();
            }
            else {
                logger.info("Table \"users\" exists.");
            }
            if (!isCollectionTableExist()) {
                logger.warn("Table \"collection\" didn't exist. It was created.");
                createCollectionTable();
            }
            else {
                logger.info("Table \"collection\" exists.");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isColorTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "color");
        ResultSet set = statement.executeQuery();

        return (!set.isBeforeFirst() ? false : true);
    }

    public synchronized boolean isCountryTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "country");
        ResultSet set = statement.executeQuery();

        return (!set.isBeforeFirst() ? false : true);
    }

    public synchronized boolean isCollectionTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "collection");
        ResultSet set = statement.executeQuery();

        return (!set.isBeforeFirst() ? false : true);
    }

    public synchronized boolean isUsersTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "users");
        ResultSet set = statement.executeQuery();

        return (!set.isBeforeFirst() ? false : true);
    }

    public synchronized void createColorTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS Color (" +
                "color_id serial primary key, " +
                "color_name text );");

        statement.execute(
                "INSERT INTO Color (color_name) " +
                        "VALUES ('GREEN'), ('RED'), ('BLACK'), ('BLUE'), ('YELLOW');");
    }

    public synchronized void createCountryTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Country(" +
                        "country_id serial primary key, " +
                        "country_name text );");

        statement.execute(
                "INSERT INTO Country(country_name) " +
                "values ('RUSSIA'), ('FRANCE'), ('THAILAND'), ('NORTH_KOREA');");
    }

    public synchronized void createCollectionTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Collection ( " +
                        "id serial PRIMARY KEY, " +
                        "name text, " +
                        "coord_x bigint, " +
                        "coord_y bigint, " +
                        "creation_date timestamp, " +
                        "height int, " +
                        "weight real, " +
                        "eye_color int REFERENCES color, " +
                        "nationality int REFERENCES country, " +
                        "location_x int, " +
                        "location_y real, " +
                        "location_z double precision, " +
                        "user_id int REFERENCES users );");
    }

    public synchronized void createUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE users ( " +
                        "id serial primary key, " +
                        "username text unique, " +
                        "password_hash text, " +
                        "salt text );"
        );
    }

    public synchronized void closeConnection() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}