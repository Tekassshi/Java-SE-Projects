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


            // Vnorené okná príkazov

            // Pridať
            {"AddNewPerson", "Pridať nový záznam"},
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

            // Vykonanie skriptu
            {"File doesn't exist!", "Súbor neexistuje!"},
            {"Wrong data in script. Process will be terminated.", "Chybné údaje v skripte. Proces bude ukončený."},
            {"Recursion detected. Process will be terminated.", "Zistená rekurzia. Proces bude ukončený."},

            // Pomoc
            {"helpLabel", "Nápoveda k dostupným príkazom"},
            {"helpMsg",
                    "\n\n\"help\" - zobraziť nápovedu k dostupným príkazom\n" +
                            "\"info\" - zobraziť informácie o kolekcii (typ, dátum inicializácie, počet prvkov atď.)\n" +
                            "\"show\" - zobraziť všetky prvky kolekcie\n" +
                            "\"add\" - pridať nový prvok do kolekcie\n" +
                            "\"update id\" - aktualizovať hodnotu prvku v kolekcii s daným id\n" +
                            "\"remove_by_id id\" - odstrániť prvok z kolekcie podľa id\n" +
                            "\"clear\" - vyčistiť kolekciu\n" +
                            "\"execute_script file_name\" - načítať a vykonať skript zo zadaného súboru. " +
                            "Skript obsahuje príkazy v rovnakej forme, ako ich zadáva používateľ v interaktívnom režime.\n" +
                            "\"head\" - zobraziť prvý prvok kolekcie\n" +
                            "\"add_if_min\" - pridať nový prvok do kolekcie, ak je jeho hodnota nižšia " +
                            "ako hodnota najmenšieho prvku v kolekcii\n" +
                            "\"remove_greater\" - odstrániť zo kolekcie všetky prvky, ktoré majú vyššiu hodnotu\n" +
                            "\"remove_all_by_nationality nationality\" - odstrániť zo kolekcie všetky prvky, " +
                            "ktoré majú zhodnú národnosť\n" +
                            "\"filter_by_nationality nationality\" - zobraziť prvky, ktoré majú zhodnú národnosť\n" +
                            "\"print_field_descending_height\" - zobraziť hodnoty atribútu " +
                            "výška pre všetky prvky v zostupnom poradí\n"},

            // Statické návestia
            {"", ""},

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
            {"",""},
            {"",""},
            {"",""}

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}