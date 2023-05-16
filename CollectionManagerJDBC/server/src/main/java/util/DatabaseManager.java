package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
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

    public void setConnection(String host, int port, String credentialsFileName) throws IOException, SQLException {
        this.host = host;
        this.port = port;

        url = "jdbc:postgresql://" + host + ":" + port + "/";

        String dir = System.getProperty("user.dir");
        String filepath = dir + File.separator + credentialsFileName;

        File f = new File(filepath);

        FileReader fileReader = new FileReader(f);
        BufferedReader reader = new BufferedReader(fileReader);

        String property;
        while ((property = reader.readLine()) != null) {
            String[] tmp = property.split("=");

            if (tmp[0].equals("psqlUser"))
                user = tmp[1];
            else if (tmp[0].equals("userPass"))
                password = tmp[1];
        }

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(url, properties);
        schema = connection.getSchema();
    }

    public void checkDatabase() throws ConnectException {
        try {
            if (!isColorTableExist())
                createColorTable();
            if (!isCountryTableExist())
                createCountryTable();
            if (!isCollectionTableExist())
                createCollectionTable();
        }
        catch (SQLException e) {
            throw new ConnectException();
        }
    }

    public boolean isColorTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                        "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "color");
        ResultSet set = statement.executeQuery();

        if (!set.isBeforeFirst())
            return false;
        else
            return true;
    }

    public boolean isCountryTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "country");
        ResultSet set = statement.executeQuery();

        if (!set.isBeforeFirst())
            return false;
        else
            return true;
    }

    public boolean isCollectionTableExist() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT FROM information_schema.tables" +
                        " WHERE table_schema = ? AND" +
                        " table_name = ?;");

        statement.setString(1, schema);
        statement.setString(2, "collection");
        ResultSet set = statement.executeQuery();

        if (!set.isBeforeFirst())
            return false;
        else
            return true;
    }

    public void createColorTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS Color (" +
                "color_id serial primary key, " +
                "color_name text );");

        statement.execute(
                "INSERT INTO Color (color_name) " +
                        "VALUES ('GREEN'), ('RED'), ('BLACK'), ('BLUE'), ('YELLOW');");
    }

    public void createCountryTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Country(" +
                        "country_id serial primary key, " +
                        "country_name text );");

        statement.execute(
                "INSERT INTO Country(country_name) " +
                "values ('RUSSIA'), ('FRANCE'), ('THAILAND'), ('NORTH_KOREA');");
    }

    public void createCollectionTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Collection ( " +
                "id serial primary key, " +
                "name text, " +
                "coord_x bigint, " +
                "coord_y bigint, " +
                "creation_date timestamp, " +
                "height real, weight real, " +
                "eye_color int references color, " +
                "nationality int references country, " +
                "location_x int, " +
                "location_y real, " +
                "location_z double precision );");
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}