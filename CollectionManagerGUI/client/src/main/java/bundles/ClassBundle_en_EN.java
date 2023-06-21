package bundles;

import java.util.ListResourceBundle;

public class ClassBundle_en_EN extends ListResourceBundle {

    private static final Object[][] contents = {
        // Lang Button
        {"lang", "EN"},

        // Locale code
        {"locale", "en"},

        // Port choice
        {"Port", "Port"},
        {"Connect", "Connect"},

        // Login
        {"signUpMsg", "Don't have an account?"},
        {"signUp", "Sign Up"},
        {"Username", "Username"},
        {"Password", "Password"},
        {"logIn", "Log In"},

        // Sign Up
        {"Creating a new account", "Creating a new account"},
        {"Repeat password", "Repeat password"},
        {"Sign Up", "Sign Up"},
        {"Welcome, ", "Welcome, "},
        {"This username is already exists", "This username is already exists"},
        {"Passwords are different!", "Passwords are different!"},
        {"RegSucc", "You have successfully registered!"},

        // Welcome page
        {"Next", "Next"},
        {"A new account has been successfully created!", "A new account has been successfully created!"},

        // Main control page
        {"ID", "ID"},
        {"Name", "Name"},
        {"Coord X", "Coord X"},
        {"Coord Y", "Coord Y"},
        {"Creation Date", "Creation Date"},
        {"Height", "Height"},
        {"Weight", "Weight"},
        {"Eye color", "Eye color"},
        {"Nationality", "Nationality"},
        {"Location X", "Location X"},
        {"Location Y", "Location Y"},
        {"Location Z", "Location Z"},
        {"Owner", "Owner"},
        {"Command", "Command"},
        {"Exit", "Exit"},
        {"Filter", "Filter"},
        {"GREEN", "GREEN"},
        {"RED", "RED"},
        {"BLACK", "BLACK"},
        {"BLUE", "BLUE"},
        {"YELLOW", "YELLOW"},
        {"RUSSIA","RUSSIA"},
        {"FRANCE", "FRANCE"},
        {"THAILAND","THAILAND"},
        {"NORTH_KOREA", "NORTH_KOREA"},

        //Commands nested windows

        //Add
        {"AddNewPerson", "AddNewPerson"},
        {"Add", "Add"},
        {"Wrong name format! Name should contain at least 1 symbol and only letters supported",
                "Wrong name format! Name should contain at least 1 symbol and only letters supported"},
        {"Wrong coordinates format! (\"X\" should be integer number, that > -783, \"Y\" should be " +
                "long integer number.)", "Wrong coordinates format! (\"X\" should be integer number, that > -783, " +
                "\"Y\" should be " +
                "long integer number.)"},
        {"Wrong height format! (height should be integer > 0, and only digits supported)",
                "Wrong height format! (height should be integer > 0, and only digits supported)"},
        {"Wrong weight format! (weight should be decimal value > 0)",
                "Wrong weight format! (weight should be decimal value > 0)"},
        {"Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")",
                "Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")"},
        {"Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")",
                "Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")"},
        {"Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                "be Double number.",
                "Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                        "be Double number."},

        // Execute Script
        {"File doesn't exist!", "File doesn't exist!"},
        {"Wrong data in script. Process will be terminated.", "Wrong data in script. Process will be terminated."},
        {"Recursion detected. Process will be terminated.", "Recursion detected. Process will be terminated."},

        // Help
        {"helpLabel", "helpLabel"},
        {"helpMsg","helpMsg"},

        // Static labels
            {"", ""},

            // Server responses
            {"", ""},

            // Error messages
            {"Server error. Please, try later.", "Server error. Please, try later."},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Wrong username or password format (From 5 to 15 symbols and they " +
                            "should contains only english letters or digits.)"},
            {"Wrong username or password!","Wrong username or password!"},
            {"",""},
            {"",""},
            {"",""}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}