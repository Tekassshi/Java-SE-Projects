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
            {"Coord X", "Koordinat X"},
            {"Coordinate X", "Koordinat X"},
            {"Coordinate Y", "Koordinat Y"},
            {"Location X", "Plats X"},
            {"Location Y", "Plats Y"},
            {"Location Z", "Plats Z"},
            {"Coord Y", "Koordinat Y"},
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


            // Commands nested windows

            // Add
            {"AddNewPerson", "Lägg till nytt objekt"},
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

            // Execute Script
            {"File doesn't exist!", "Filen finns inte!"},
            {"Wrong data in script. Process will be terminated.", "Felaktiga data i skriptet. Processen avbryts."},
            {"Recursion detected. Process will be terminated.", "Rekursion upptäckt. Processen avbryts."},

            // Help
            {"helpLabel", "Hjälp för tillgängliga kommandon"},
            {"helpMsg",
                    "\n\n\"help\" - visa hjälp för tillgängliga kommandon\n" +
                            "\"info\" - visa information om samlingen (typ, initialiseringsdatum, antal objekt etc.)\n" +
                            "\"show\" - visa alla objekt i samlingen i textformat\n" +
                            "\"add\" - lägg till ett nytt objekt i samlingen\n" +
                            "\"update id\" - uppdatera värdet för ett objekt i samlingen med ett specifikt ID\n" +
                            "\"remove_by_id id\" - ta bort ett objekt från samlingen baserat på dess ID\n" +
                            "\"clear\" - rensa samlingen\n" +
                            "\"execute_script filnamn\" - läs in och kör ett skript från en specifik fil. " +
                            "Skriptet bör innehålla kommandon på samma sätt som de matas in av användaren i interaktivt " +
                            "läge.\n" +
                            "\"head\" - visa det första objektet i samlingen\n" +
                            "\"add_if_min\" - lägg till ett nytt objekt i samlingen om dess värde är mindre än " +
                            "värdet hos det minsta objektet i samlingen\n" +
                            "\"remove_greater\" - ta bort alla objekt i samlingen som är större än ett specifikt värde" +
                            "\n\"remove_all_by_nationality nationality\" - ta bort alla objekt i samlingen där " +
                            "nationalitetsvärdet matchar ett specifikt värde\n" +
                            "\"filter_by_nationality nationality\" - visa alla objekt i samlingen där " +
                            "nationalitetsvärdet matchar ett specifikt värde\n" +
                            "\"print_field_descending_height\" - visa värdena för längdfältet för alla objekt " +
                            "i fallande ordning\n"},

            // Static labels
            {"", ""},

            // Server responses
            {"", ""},

            // Error messages
            {"Server error. Please, try later.", "Serverfel. Försök igen senare."},
            {"Welcome back, ","Välkommen tillbaka, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Felaktigt användarnamn eller lösenord (5 till 15 tecken som endast får innehålla bokstäver " +
                            "eller siffror)."},
            {"Wrong username or password!","Felaktigt användarnamn eller lösenord!"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}