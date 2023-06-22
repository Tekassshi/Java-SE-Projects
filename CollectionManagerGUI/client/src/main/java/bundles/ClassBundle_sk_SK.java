package bundles;

import java.util.ListResourceBundle;

public class ClassBundle_sk_SK extends ListResourceBundle {

    private static final Object[][] contents = {
            // Lang Button
            {"lang", "SK"},

            // Locale code
            {"locale", "sk"},

            // Port choice
            {"Port", "Port"},
            {"Connect", "Pripojte sa"},

            // Prihlásenie
            {"signUpMsg", "Ešte nemáte účet?"},
            {"signUp", "zaregistrovať sa"},
            {"Username", "Používateľské meno"},
            {"Password", "Heslo"},
            {"logIn", "Prihlásiť sa"},

            // Registrácia
            {"Creating a new account", "Vytvorenie nového účtu"},
            {"Repeat password", "Opakujte heslo"},
            {"Sign Up", "Registrovať sa"},
            {"Welcome, ", "Vitajte, "},
            {"This username is already exists", "Toto používateľské meno už existuje"},
            {"Passwords are different!", "Heslá sa líšia!"},
            {"RegSucc", "Úspešne ste sa zaregistrovali!"},

            // Úvodná stránka
            {"Next", "Ďalej"},
            {"A new account has been successfully created!", "Nový účet bol úspešne vytvorený!"},

            // Hlavná ovládacia stránka
            {"ID", "ID"},
            {"Name", "Meno"},
            {"Coord X", "Súradnica X"},
            {"Coordinate X","Súradnica X"},
            {"Coordinate Y","Súradnica Y"},
            {"Location X", "Poloha X"},
            {"Location Y", "Poloha Y"},
            {"Location Z", "Poloha Z"},
            {"Coord Y", "Súradnica Y"},
            {"Creation Date", "Dátum vytvorenia"},
            {"Height", "Výška"},
            {"Weight", "Hmotnosť"},
            {"Eye color", "Farba očí"},
            {"Nationality", "Národnosť"},
            {"Location X", "Poloha X"},
            {"Location Y", "Poloha Y"},
            {"Location Z", "Poloha Z"},
            {"Owner", "Vlastník"},
            {"Command", "Príkaz"},
            {"Exit", "Východ"},
            {"Filter", "Filer"},
            {"GREEN", "ZELENÁ"},
            {"RED", "ČERVENÁ"},
            {"BLACK", "ČIERNY"},
            {"BLUE", "MODRÁ"},
            {"YELLOW", "ŽLTÁ"},
            {"RUSSIA", "RUSKO"},
            {"FRANCE", "FRANCÚZSKO"},
            {"THAILAND", "THAJSKO"},
            {"NORTH_KOREA", "SEVERNÁ KÓREA"},
            {"Remove", "Odstrániť"},
            {"Update", "Aktualizovať"},

            // Vnorené okná príkazov

            // Pridať
            {"Add new person", "Pridať nový záznam"},
            {"Add", "Pridať"},
            {"Wrong name format! Name should contain at least 1 symbol and only letters supported",
                    "Nesprávny formát mena! Meno by malo obsahovať aspoň 1 symbol a podporovať iba písmená."},
            {"Wrong coordinates format! (\"X\" should be integer number, that > -783, \"Y\" should be " +
                    "long integer number.)", "Nesprávny formát súradníc! (\"X\" by malo byť celé číslo väčšie ako -783, " +
                    "\"Y\" by malo byť dlhé celé číslo)"},
            {"Wrong height format! (height should be integer > 0, and only digits supported)",
                    "Nesprávny formát výšky! (výška by mala byť celé číslo väčšie ako 0 a obsahovať iba číslice)"},
            {"Wrong weight format! (weight should be decimal value > 0)",
                    "Nesprávny formát hmotnosti! (hmotnosť by mala byť desatinné číslo väčšie ako 0)"},
            {"Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")",
                    "Nesprávna hodnota farby! (môžete si vybrať jednu z hodnôt \"ZELENÁ, ČERVENÁ, ČIERNÁ, MODRÁ, " +
                            "ŽLTÁ\")"},
            {"Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")",
                    "Nesprávna hodnota národnosti! (môžete si vybrať jednu z hodnôt \"RUSKO, FRANCÚZSKO, THAJSKO, " +
                            "SEVERNÁ_KÓREA\")"},
            {"Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                    "be Double number.",
                    "Nesprávny formát polohy! (\"X\" by malo byť číslo s pohyblivou desatinnou čiarkou, " +
                            "\"Y\" by malo byť celé číslo, \"Z\" by malo byť číslo s pohyblivou desatinnou čiarkou."},

            // Info
            {"infoTitle", "Informácie o zbierke"},

            // Help
            {"helpLabel", "Nápoveda k dostupným príkazom"},
            {"helpMsg", "\"help\" - zobraziť informácie o dostupných príkazoch.\n" +
                    "\"info\" - zobraziť informácie o kolekcii (typ, dátum inicializácie, počet prvkov, atď.) " +
                    "na štandardný výstupný prúd.\n" +
                    "\"show\" - zobraziť všetky prvky kolekcie vo formáte reťazca na štandardný výstupný prúd.\n" +
                    "\"add\" - pridať nový prvok do kolekcie.\n" +
                    "\"update id\" - aktualizovať hodnotu prvku v kolekcii s daným id.\n" +
                    "\"remove_by_id id\" - odstrániť prvok z kolekcie podľa id.\n" +
                    "\"clear\" - vyčistiť kolekciu.\n" +
                    "\"execute_script file_name\" - načítať a vykonať skript zo zadaného súboru. Skript " +
                    "obsahuje príkazy vo forme, v akom ich zadáva používateľ v interaktívnom režime.\n" +
                    "\"head\" - zobraziť prvý prvok kolekcie.\n" +
                    "\"add_if_min\" - pridať nový prvok do kolekcie, ak je jeho hodnota menšia ako hodnota " +
                    "najmenšieho prvku v tejto kolekcii.\n" +
                    "\"remove_greater\" - odstrániť z kolekcie všetky prvky, ktoré sú väčšie ako zadaný prvok.\n" +
                    "\"remove_all_by_nationality nationality\" - odstrániť z kolekcie všetky prvky, ktorých " +
                    "hodnota poľa nationality je ekvivalentná zadanému.\n" +
                    "\"filter_by_nationality nationality\" - zobraziť prvky, ktorých hodnota poľa nationality " +
                    "je rovnaká ako zadaná hodnota.\n" +
                    "\"print_field_descending_height\" - zobraziť hodnoty poľa height všetkých prvkov v zostupnom " +
                    "poradí."},

            // Vykonanie skriptu
            {"File doesn't exist!", "Súbor neexistuje!"},
            {"Wrong data in script. Process will be terminated.", "Chybné údaje v skripte. Proces bude ukončený."},
            {"Recursion detected. Process will be terminated.", "Zistená rekurzia. Proces bude ukončený."},

            // Command buttons
            {"Add", "Pridať"},
            {"Clear", "Vyčistiť"},
            {"Execute", "Vykonať"},
            {"Filter", "Filtrovať"},
            {"Remove", "Odstrániť"},
            {"Update", "Aktualizovať"},

            // Statické návestia
            {"Add new person", "Pridať novú osobu"},
            {"Add new person if min", "Pridať novú osobu, ak je minimálna"},
            {"Remove greater persons", "Odstrániť väčšie osoby"},
            {"Add new person if min", "Pridať novú osobu, ak je minimálna"},
            {"Do you want to clear all your records?", "Chcete vymazať všetky vaše záznamy?"},
            {"Execute script", "Vykonaj skript"},
            {"Script filename", "Názov súboru skriptu"},
            {"File not found.", "Súbor nenájdený."},
            {"File doesn't exist!", "Súbor neexistuje!"},
            {"Filter by nationality", "Filtrovať podľa národnosti"},
            {"Remove by ID", "Odstrániť podľa ID"},
            {"Remove by nationality", "Odstrániť podľa národnosti"},
            {"Update ID", "Aktualizovať ID"},

            // Odpovede servera
            {"", ""},

            // Chybové správy
            {"Server error. Please, try later.", "Chyba servera. Skúste to prosím neskôr."},
            {"Welcome back, ","Vitajte späť, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Nesprávny formát používateľského mena alebo hesla (od 5 do 15 znakov a mali by obsahovať " +
                            "iba anglické písmená alebo číslice.)"},
            {"Wrong username or password!","Nesprávne používateľské meno alebo heslo!"},
            {"Wrong id value! (Id should be > 0 and contain only digits)", "Nesprávna hodnota ID! " +
                    "(ID by malo byť > 0 a obsahovať iba číslice)"},

            // Server responses
            {"Server response", "Odpoveď servera"},
            {"\nCollection is empty!\n", "\nKolekcia je prázdna!\n"},
            {"Person was added successfully!\n", "Osoba bola úspešne pridaná!\n"},
            {"Error updating, inserting or loading data from database.\n", "Chyba pri " +
                    "aktualizovaní, vkladaní alebo načítaní údajov z databázy.\n"},
            {"\nYour element value is bigger or the same than min element in collection\nElement will not be recorded.\n",
                    "\nHodnota vášho prvku je väčšia alebo rovnaká ako najmenší prvok v kolekcii.\nPrvok nebude " +
                            "zaznamenaný.\n"},
            {"\nCollection is empty!\n", "\nKolekcia je prázdna!\n"},
            {"\nId, that you want to update, doesn't exist!\nTry again\n", "\nID, ktoré chcete aktualizovať, " +
                    "neexistuje! Skúste to znova.\n"},
            {"\nYou can edit only your own records!\nTry again.\n", "\nMôžete upravovať len vaše vlastné záznamy! " +
                    "Skúste to znova.\n"},
            {"\nPerson with given id was successfully updated!\n", "\nOsoba s daným ID bola úspešne aktualizovaná!\n"},
            {"\nPerson with given id value doesn't exist!Try again.\n", "\nOsoba s danou hodnotou ID neexistuje! " +
                    "Skúste to znova.\n"},
            {"\nPerson with given id was successfully removed!\n", "\nOsoba s daným ID bola úspešne odstránená!\n"},
            {"\nAll your own records successfully cleared.\n", "\nVšetky " +
                    "vaše vlastné záznamy boli úspešne vymazané.\n"},
            {"\nYour own records have been successfully removed!\n",
                    "\nVaše vlastné záznamy boli úspešne odstránené!\n"},
            {"Collection type:", "Typ kolekcie:"},
            {"Number of elements:", "Počet prvkov:"},

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}