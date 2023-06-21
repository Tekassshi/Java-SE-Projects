package managers;

import data.Color;
import data.Coordinates;
import data.Country;
import data.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Class that contains only static methods to validate user input.
 * */
public class InputManager {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    static InputStreamReader isr = new InputStreamReader(System.in);
    static BufferedReader reader = new BufferedReader(isr);

    public static int readPort(String input) throws NumberFormatException, IOException {
        int port = Integer.parseInt(input);
        if (port <= 1024 || port > 65536)
            throw new NumberFormatException();
        return port;
    }

    public static void readUsername(String username) throws IllegalArgumentException {
        if (!isAuthWord(username))
            throw new IllegalArgumentException();
    }

    public static void readPassword(String password) throws IllegalArgumentException{
        if (!isAuthWord(password))
            throw new IllegalArgumentException();
    }

    public static boolean isAuthWord(String s){
        if (s.length() < 5 || s.length() > 15)
            return false;
        for (int i = 0; i < s.length(); i++){
            if ((s.charAt(i) < 'A' || s.charAt(i) > 'Z') &&
                    (s.charAt(i) < 'a' || s.charAt(i) > 'z') &&
                    (s.charAt(i) < '0' || s.charAt(i) > '9'))
                return false;
        }
        return true;
    }

    /**
     * Utility method for reading "name" field value from default input stream.
     * @return valid "name" field String value.
     * */
    public static String readName(String name) throws IllegalArgumentException{
        if (!isWord(name) || name.length() == 0)
            throw new IllegalArgumentException();
        return name;
    }

    /**
     * Utility method for reading "name" field value from given input stream.
     * @return valid "name" field String value.
     * @throws IOException if given stream contains incorrect data
     * @throws InputMismatchException if given stream contains incorrect data
     * */
    public static String readNameScript(BufferedReader reader) throws IOException, InputMismatchException {
        String name;
        name = reader.readLine();

        if (!isWord(name) || name.length() == 0)
            throw new InputMismatchException();
        return name;
    }

    /**
     * Utility method for checking input String for containing only letters.
     * @param word String that we should to check.
     * @return boolean result of checking.
     * */
    public static boolean isWord(String word){
        for (int i = 0; i < word.length(); i++){
            if (!Character.isLetter(word.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * Utility method for reading "coordinates" field value from default input stream.
     * @return valid "coordinates" field "Coordinates" value.
     * */
    public static Coordinates readCoordinates(String xInput, String yInput) throws NumberFormatException, IOException {
        int x = Integer.parseInt(xInput);
        if (x <= -783)
            throw new NumberFormatException();
        Long y = Long.parseLong(yInput);
        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    /**
     * Utility method for reading "coordinates" field value from given input stream.
     * @return valid "coordinates" field "Coordinates" value.
     * @throws IOException if given stream contains incorrect data
     * @throws NumberFormatException if given stream contains incorrect data
     * */
    public static Coordinates readCoordinatesScript(BufferedReader reader) throws IOException, NumberFormatException {

        int x = Integer.parseInt(reader.readLine());

        if (x <= -783)
            throw new NumberFormatException();

        Long y = Long.parseLong(reader.readLine());

        Coordinates coordinates = new Coordinates(x, y);
        return coordinates;
    }

    /**
     * Utility method for reading "height" field value from default input stream.
     * @return valid "height" field Integer value.
     * */
    public static Integer readHeight(String inputHeight) throws NumberFormatException, IOException {
        Integer height = Integer.parseInt(inputHeight);
        if (height <= 0)
            throw new IOException();
        return height;
    }

    /**
     * Utility method for reading "height" field value from given input stream.
     * @return valid "height" field "Integer" value.
     * @throws IOException if given stream contains incorrect data
     * */
    public static Integer readHeightScript(BufferedReader reader) throws IOException {
        Integer height = Integer.parseInt(reader.readLine());
        if (height <= 0)
            throw new IOException();
        return height;
    }

    /**
     * Utility method for reading "weight" field value from default input stream.
     * @return valid "weight" field Float value.
     * */
    public static Float readWeight(String inputWeight) throws NumberFormatException, IOException {
        Float weight = Float.parseFloat(inputWeight.replaceAll(",", "."));

        if(weight == Float.POSITIVE_INFINITY || weight == Float.NEGATIVE_INFINITY)
            throw new NumberFormatException();
        if (weight <= 0)
            throw new IOException();

        return weight;
    }

    /**
     * Utility method for reading "weight" field value from given input stream.
     * @return valid "weight" field "Float" value.
     * @throws IOException if given stream contains incorrect data
     * */
    public static Float readWeightScript(BufferedReader reader) throws IOException {
        Float weight = Float.parseFloat(reader.readLine().replaceAll(",", "."));

        if (weight <= 0)
            throw new IOException();

        return weight;
    }

    /**
     * Utility method for reading "eyeColor" field value from default input stream.
     * @return valid "eyeColor" field, Color enum value.
     * */
    public static Color readEyeColor(String inputEyeColor) throws IOException, IllegalArgumentException,
            NullPointerException{
        Color eye_color;
        String tmp = inputEyeColor.toUpperCase();
        if (!tmp.equals(""))
            eye_color = Color.valueOf(tmp);
        else
            eye_color = null;
        return eye_color;
    }

    /**
     * Utility method for reading "eyeColor" field value from given input stream.
     * @return valid "eyeColor" field "Color" value.
     * @throws IOException if given stream contains incorrect data
     * */
    public static Color readEyeColorScript(BufferedReader reader) throws IOException {
        Color eye_color;
        String tmp = reader.readLine().toUpperCase();

        if (!tmp.equals(""))
            eye_color = Color.valueOf(tmp);
        else
            eye_color = null;

        return eye_color;
    }

    /**
     * Utility method for reading "nationality" field value from default input stream.
     * @return valid "nationality" field Country class value.
     * */
    public static Country readNationality(String inputNationality) throws IOException, IllegalArgumentException,
            NullPointerException {
        Country nationality = Country.valueOf(inputNationality.toUpperCase());
        return nationality;
    }

    /**
     * Utility method for reading "nationality" field value from given input stream.
     * @return valid "nationality" field "Country" value.
     * @throws IOException if given stream contains incorrect data
     * */
    public static Country readNationalityScript(BufferedReader reader) throws IOException {
        Country nationality = Country.valueOf(reader.readLine().toUpperCase());
        return nationality;
    }

    /**
     * Utility method for reading "location" field value from default input stream.
     * @return valid "location" field, Location class object.
     * */
    public static Location readLocation(String xLoc, String yLoc, String zLoc) throws NumberFormatException,
            IOException {

        Integer x = Integer.parseInt(xLoc);
        if(x == Float.POSITIVE_INFINITY || x == Float.NEGATIVE_INFINITY)
            throw new NumberFormatException();

        Float y = Float.parseFloat(yLoc.replaceAll(",", "."));
        Double z = Double.parseDouble(zLoc.replaceAll(",", "."));

        Location location = new Location(x, y, z);
        return location;
    }

    /**
     * Utility method for reading "location" field value from given input stream.
     * @return valid "location" field "Location" value.
     * @throws IOException if given stream contains incorrect data
     * */
    public static Location readLocationScript(BufferedReader reader) throws IOException {

        Integer x = Integer.parseInt(reader.readLine());

        Float y = Float.parseFloat(reader.readLine().replaceAll(",", "."));

        Double z = Double.parseDouble(reader.readLine().replaceAll(",", "."));

        Location location = new Location(x, y, z);
        return location;
    }

    /**
     * Utility method for reading "id" field value from given String value.
     * @param id value that we should to validate.
     * @return valid "id" field in String representation.
     * */
    public static String readId(String id){
        int out = 0;
        int f = 0;
        while (true){
            try {
                if (f == 1) {
                    System.out.print("Enter id value: ");
                    id = reader.readLine();
                }
                out = Integer.parseInt(id);

                if (out <= 0)
                    throw new NumberFormatException();

                return id;
            }
            catch (IOException | NumberFormatException e){
                System.out.println(ANSI_RED + "\nWrong id value!\n(Id should be > 0 and contain " +
                        "only digits)" + ANSI_RESET);
                System.out.println(ANSI_RED + "Try again.\n" + ANSI_RESET);
                f = 1;
            }
        }
    }

    /**
     * Utility method for validating file on given file path.
     * @param file_name file_path that we should to validate.
     * @return valid file path value.
     * */
    public static String readFile(String file_name) throws FileNotFoundException {
        String file_dir = System.getProperty("user.dir") + "/";
        String file_path = file_dir + file_name;

        File file = new File(file_path);

        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        return file_path;
    }
}