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
        {"Remove", "Remove"},
        {"Update", "Update"},
        //Commands nested windows

        //Add
        {"Add new person", "AddNewPerson"},
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

            // Info
            {"infoTitle", "Collection Info"},

            // Help
            {"helpLabel", "Reference for all available commands"},
            {"helpMsg", "\"help\" - display help information about available commands.\n" +
                    "\"info\" - display information about the collection (type, initialization date, number of " +
                    "elements, etc.) in the standard output stream.\n" +
                    "\"show\" - display all elements of the collection in string representation in the standard " +
                    "output stream.\n" +
                    "\"add\" - add a new element to the collection.\n" +
                    "\"update id\" - update the value of the collection element with the specified id.\n" +
                    "\"remove_by_id id\" - remove an element from the collection by its id.\n" +
                    "\"clear\" - clear the collection.\n" +
                    "\"execute_script file_name\" - read and execute a script from the specified file. The script " +
                    "contains commands in the same format as the user enters them in interactive mode.\n" +
                    "\"head\" - display the first element of the collection.\n" +
                    "\"add_if_min\" - add a new element to the collection if its value is smaller than the smallest " +
                    "element in the collection.\n" +
                    "\"remove_greater\" - remove all elements from the collection that are greater than the " +
                    "specified one.\n" +
                    "\"remove_all_by_nationality nationality\" - remove all elements from the collection whose " +
                    "nationality field value is equivalent to the specified one.\n" +
                    "\"filter_by_nationality nationality\" - display elements whose nationality field value is " +
                    "equal to the specified one.\n" +
                    "\"print_field_descending_height\" - display the values of the height field of all " +
                    "elements in descending order."},

        // Execute Script
        {"File doesn't exist!", "File doesn't exist!"},
        {"Wrong data in script. Process will be terminated.", "Wrong data in script. Process will be terminated."},
        {"Recursion detected. Process will be terminated.", "Recursion detected. Process will be terminated."},

            // Command buttons
            {"Add", "Add"},
            {"Clear", "Clear"},
            {"Execute", "Execute"},
            {"Filter", "Filter"},
            {"Remove", "Remove"},
            {"Update", "Update"},


        // Static labels
            {"Add new person", "Add new person"},
            {"Add new person if min", "Add new person if min"},
            {"Remove greater persons", "Remove greater person"},
            {"Add new person if min", "Add new person if min"},
            {"Do you want to clear all your records?", "Do you want to clear all your records?"},
            {"Execute script", "Execute script"},
            {"Script filename", "Script filename"},
            {"File not found.", "File not found."},
            {"File doesn't exist!", "File doesn't exist!"},
            {"Filter by nationality", "Filter by nationality"},
            {"Remove by ID", "Remove by ID"},
            {"Remove by nationality", "Remove by nationality"},
            {"Update ID", "Update ID"},

            // Error messages
            {"Server error. Please, try later.", "Server error. Please, try later."},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Wrong username or password format (From 5 to 15 symbols and they " +
                            "should contains only english letters or digits.)"},
            {"Wrong username or password!","Wrong username or password!"},
            {"Wrong id value! (Id should be > 0 and contain only digits)", "Wrong id value! (Id should be > 0 and " +
                    "contain only digits)"},
            // Server responses
            {"Server response", "Server response"},
            {"\nCollection is empty!\n", "\nCollection is empty!\n"},
            {"Person was added successfully!\n", "Person was added successfully!\n"},
            {"Error updating, inserting or loading data from database.\n", "Error updating, inserting or loading " +
                    "data from database.\n"},
            {"\nYour element value is bigger or the same than min element in collection\nElement will not be recorded.\n",
                    "\nYour element value is bigger or the same than min element in collection\nElement will not be " +
                            "recorded.\n"},
            {"\nCollection is empty!\n", "\nCollection is empty!\n"},
            {"\nId, that you want to update, doesn't exist!\nTry again\n", "\nId, that you want to update, doesn't " +
                    "exist!\nTry again\n"},
            {"\nYou can edit only your own records!\nTry again.\n", "\nYou can edit only your own records!" +
                    "\nTry again.\n"},
            {"\nPerson with given id was successfully updated!\n", "\nPerson with given id was successfully updated!\n"},
            {"\nPerson with given id value doesn't exist!Try again.\n", "\nPerson with given id value doesn't exist!" +
                    "Try again.\n"},
            {"\nPerson with given id was successfully removed!\n", "\nPerson with given id was successfully removed!\n"},
            {"\nAll your own records successfully cleared.\n", "\nAll your own records successfully cleared.\n"},
            {"\nYour own records have been successfully removed!\n", "\nYour own records have been successfully " +
                    "removed!\n"},
            {"Collection type:", "Collection type:"},
            {"Number of elements:", "Number of elements:"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}