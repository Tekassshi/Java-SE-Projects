package bundles;

import java.util.ListResourceBundle;

public class ClassBundle_sv_SV extends ListResourceBundle {

    private static final Object[][] contents = {
            // Lang Button
            {"lang", "SV"},

            // Locale code
            {"locale", "sv"},

            // Port choice
            {"Port", "Port"},
            {"Connect", "Anslut"},

            // Login
            {"signUpMsg", "Har du inget konto?"},
            {"signUp", "Registrera dig"},
            {"Username", "Användarnamn"},
            {"Password", "Lösenord"},
            {"logIn", "Logga in"},

            // Sign Up
            {"Creating a new account", "Skapa ett nytt konto"},
            {"Repeat password", "Upprepa lösenord"},
            {"Sign Up", "Registrera"},
            {"Welcome, ", "Välkommen, "},
            {"This username is already exists", "Detta användarnamn finns redan"},
            {"Passwords are different!", "Lösenorden är olika!"},
            {"RegSucc", "Din registrering har tagit emot!"},

            // Welcome page
            {"Next", "Nästa"},
            {"A new account has been successfully created!", "Ett nytt konto har skapats framgångsrikt!"},

            // Main control page
            {"ID", "ID"},
            {"Name", "Namn"},
            {"Coord X", "Koord. X"},
            {"Location X", "Plats X"},
            {"Location Y", "Plats Y"},
            {"Location Z", "Plats Z"},
            {"Coord Y", "Koord. Y"},
            {"Creation Date", "Skapad datum"},
            {"Height", "Längd"},
            {"Weight", "Vikt"},
            {"Eye color", "Ögonfärg"},
            {"Nationality", "Nationalitet"},
            {"Location X", "Plats X"},
            {"Location Y", "Plats Y"},
            {"Location Z", "Plats Z"},
            {"Owner", "Ägare"},
            {"Command", "Kommando"},
            {"Exit", "Utgång"},
            {"Filter", "Filtrera"},
            {"GREEN", "GRÖN"},
            {"RED", "RÖD"},
            {"BLACK", "SVART"},
            {"BLUE", "BLÅ"},
            {"YELLOW", "GUL"},
            {"RUSSIA", "RYSSLAND"},
            {"FRANCE", "FRANKRIKE"},
            {"THAILAND", "THAILAND"},
            {"NORTH_KOREA", "NORDKOREA"},
            {"Remove", "Ta bort"},
            {"Update", "Uppdatera"},

            // Commands nested windows

            // Add
            {"Add new person", "Lägg till nytt objekt"},
            {"Add", "Lägg till"},
            {"Wrong name format! Name should contain at least 1 symbol and only letters supported",
                    "Felaktigt namnformat! Namnet bör innehålla minst 1 symbol och endast bokstäver är tillåtna."},
            {"Wrong coordinates format! (\"X\" should be integer number, that > -783, \"Y\" should be " +
                    "long integer number.)", "Felaktigt koordinatformat! (\"X\" bör vara ett heltal större än -783, " +
                    "\"Y\" bör vara ett långt heltal)."},
            {"Wrong height format! (height should be integer > 0, and only digits supported)",
                    "Felaktigt längdformat! (längden bör vara ett heltal större än 0 och endast siffror är tillåtna)"},
            {"Wrong weight format! (weight should be decimal value > 0)",
                    "Felaktigt vikteformat! (vikten bör vara ett decimaltal större än 0)"},
            {"Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")",
                    "Felaktigt färgvärde! (du kan välja en av värdena \"GRÖN, RÖD, SVART, BLÅ, GUL\")"},
            {"Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")",
                    "Felaktigt nationalitetsvärde! (du kan välja en av värdena \"RYSSLAND, FRANKRIKE, THAILAND, " +
                            "NORDKOREA\")"},
            {"Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                    "be Double number.",
                    "Felaktigt platsformat! (\"X\" bör vara ett flyttal, \"Y\" bör vara ett heltal, \"Z\" bör vara ett " +
                            "tal med dubbel precision)"},

            // Info
            {"infoTitle", "Samlingsinfo"},

            // Help
            {"helpLabel", "Hjälp för tillgängliga kommandon"},
            {"helpMsg", "\"help\" - visa hjälpinformation om tillgängliga kommandon.\n" +
                    "\"info\" - visa information om samlingen (typ, initialiseringsdatum, antal element, etc.) " +
                    "i standardutdataström.\n" +
                    "\"show\" - visa alla element i samlingen i strängrepresentation i standardutdataström.\n" +
                    "\"add\" - lägg till ett nytt element i samlingen.\n" +
                    "\"update id\" - uppdatera värdet för elementet i samlingen med det angivna id:t.\n" +
                    "\"remove_by_id id\" - ta bort ett element från samlingen med dess id.\n" +
                    "\"clear\" - rensa samlingen.\n" +
                    "\"execute_script file_name\" - läs och kör ett skript från den angivna filen. Skriptet " +
                    "innehåller kommandon i samma format som användaren anger dem i interaktivt läge.\n" +
                    "\"head\" - visa det första elementet i samlingen.\n" +
                    "\"add_if_min\" - lägg till ett nytt element i samlingen om dess värde är mindre än det " +
                    "minsta värdet i samlingen.\n" +
                    "\"remove_greater\" - ta bort alla element från samlingen som är större än det angivna värdet.\n" +
                    "\"remove_all_by_nationality nationality\" - ta bort alla element från samlingen vars" +
                    "nationalitetsfält är ekvivalent med det angivna värdet.\n" +
                    "\"filter_by_nationality nationality\" - visa element vars nationalitetsfält är lika med " +
                    "det angivna värdet.\n" +
                    "\"print_field_descending_height\" - visa höjdfältets värden för alla element i fallande ordning."},

            // Execute Script
            {"File doesn't exist!", "Filen finns inte!"},
            {"Wrong data in script. Process will be terminated.", "Felaktiga data i skriptet. Processen avbryts."},
            {"Recursion detected. Process will be terminated.", "Rekursion upptäckt. Processen avbryts."},

            // Command buttons
            {"Add", "Lägg till"},
            {"Clear", "Rensa"},
            {"Execute", "Utför"},
            {"Filter", "Filtrera"},
            {"Remove", "Ta bort"},
            {"Update", "Uppdatera"},

            // Static labels
            {"Add new person", "Lägg till ny person"},
            {"Add new person if min", "Lägg till ny person om den är minsta"},
            {"Remove greater persons", "Ta bort större personer"},
            {"Add new person if min", "Lägg till ny person om den är minsta"},
            {"Do you want to clear all your records?", "Vill du rensa alla dina poster?"},
            {"Execute script", "Kör skript"},
            {"Script filename", "Skriptfilnamn"},
            {"File not found.", "Filen hittades inte."},
            {"File doesn't exist!", "Filen finns inte!"},
            {"Filter by nationality", "Filtrera efter nationalitet"},
            {"Remove by ID", "Ta bort efter ID"},
            {"Remove by nationality", "Ta bort efter nationalitet"},
            {"Update ID", "Uppdatera ID"},

            // Server responses
            {"\nCollection is empty!\n", "\nSamlingen är tom!\n"},
            {"Person was added successfully!\n", "Personen lades till framgångsrikt!\n"},
            {"Error updating, inserting or loading data from database.\n", "Fel vid uppdatering, " +
                    "infogning eller inläsning av data från databasen.\n"},
            {"\nYour element value is bigger or the same than min element in collection\nElement " +
                    "will not be recorded.\n", "\nVärdet på ditt element är större eller lika med det " +
                    "minsta elementet i samlingen.\nElementet kommer inte att sparas.\n"},

            // Server responses
            {"Server response", "Serversvar"},
            {"\nCollection is empty!\n", "\nSamlingen är tom!\n"},
            {"\nId, that you want to update, doesn't exist!\nTry again\n", "\nID:t du vill uppdatera " +
                    "finns inte! Försök igen.\n"},
            {"\nYou can edit only your own records!\nTry again.\n", "\nDu kan bara redigera dina egna poster! " +
                    "Försök igen.\n"},
            {"\nPerson with given id was successfully updated!\n", "\nPersonen med det angivna ID:t " +
                    "uppdaterades framgångsrikt!\n"},
            {"\nPerson with given id value doesn't exist!Try again.\n", "\nPersonen med det angivna " +
                    "ID-värdet finns inte! Försök igen.\n"},
            {"\nPerson with given id was successfully removed!\n", "\nPersonen med det angivna ID:t togs " +
                    "bort framgångsrikt!\n"},
            {"\nAll your own records successfully cleared.\n", "\nAlla dina egna poster rensades framgångsrikt.\n"},
            {"\nYour own records have been successfully removed!\n", "\nDina egna poster har tagits " +
                    "bort framgångsrikt!\n"},
            {"Collection type:", "Samlingens typ:"},
            {"Number of elements:", "Antal element:"},

            // Error messages
            {"Server error. Please, try later.", "Serverfel. Försök igen senare."},
            {"Welcome back, ","Välkommen tillbaka, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Felaktigt användarnamn eller lösenord (5 till 15 tecken som endast får innehålla bokstäver " +
                            "eller siffror)."},
            {"Wrong username or password!","Felaktigt användarnamn eller lösenord!"},
            {"Wrong id value! (Id should be > 0 and contain only digits)", "Felaktigt ID-värde! " +
                    "(ID bör vara > 0 och endast innehålla siffror)"},

    };


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}