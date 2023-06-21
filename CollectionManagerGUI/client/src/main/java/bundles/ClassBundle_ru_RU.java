package bundles;

import java.util.ListResourceBundle;

public class ClassBundle_ru_RU extends ListResourceBundle {

    private static final Object[][] contents = {
            // Lang Button
            {"lang", "RU"},

            // Locale code
            {"locale", "ru"},

            // Port choice
            {"Port", "Порт"},
            {"Connect", "Подключиться"},

            // Login
            {"signUpMsg", "Еще нет аккаунта?"},
            {"signUp", "зарегистрироваться"},
            {"Username", "Имя пользователя"},
            {"Password", "Пароль"},
            {"logIn", "Войти"},

            // Sign Up
            {"Creating a new account", "Создание нового аккаунта"},
            {"Repeat password", "Повторите пароль"},
            {"Sign Up", "Зарегистрироваться"},
            {"Welcome, ", "Добро пожаловать, "},
            {"This username is already exists", "Такое имя пользователя уже существует"},
            {"Passwords are different!", "Пароли отличаются!"},
            {"RegSucc", "Вы успешно зарегистрированы!"},

            // Welcome page
            {"Next", "Далее"},
            {"A new account has been successfully created!", "Новый аккаунт успешно создан!"},

            // Main control page
            {"ID", "ИД"},
            {"Name", "Имя"},
            {"Coord X", "Координата X"},
            {"Coordinate X","Координата X"},
            {"Coordinate Y","Координата Y"},
            {"Location X", "Расположение Х"},
            {"Location Y", "Расположение Y"},
            {"Location Z", "Расположение Z"},
            {"Coord Y", "Координата Y"},
            {"Creation Date", "Дата создания"},
            {"Height", "Рост"},
            {"Weight", "Вес"},
            {"Eye color", "Цвет глаз"},
            {"Nationality", "Национальность"},
            {"Location X", "Лок. X"},
            {"Location Y", "Лок. Y"},
            {"Location Z", "Лок. Z"},
            {"Owner", "Владелец"},
            {"Command", "Команда"},
            {"Exit", "Выход"},
            {"Filter", "Фильтр"},
            {"GREEN", "ЗЕЛЕНЫЙ"},
            {"RED", "КРАСНЫЙ"},
            {"BLACK", "ЧЕРНЫЙ"},
            {"BLUE", "ГОЛУБОЙ"},
            {"YELLOW", "ЖЕЛТЫЙ"},
            {"RUSSIA","РОССИЯ"},
            {"FRANCE", "ФРАНЦИЯ"},
            {"THAILAND","ТАИЛАНД"},
            {"NORTH_KOREA", "СЕВЕРНАЯ КОРЕЯ"},


            //Commands nested windows

            //Add
            {"AddNewPerson", "Добавить новую запись"},
            {"Add", "Добавить"},
            {"Wrong name format! Name should contain at least 1 symbol and only letters supported",
                    "Неправильный формат имени! Имя должно содержать не менее 1 символа и состоять только из букв."},
            {"Wrong coordinates format! (\"X\" should be integer number, that > -783, \"Y\" should be " +
                    "long integer number.)", "Неверный формат координат! (\"X\" должен быть целым числом > -783, " +
                    "\"Y\" должен быть в формате Long)"},
            {"Wrong height format! (height should be integer > 0, and only digits supported)",
                    "Неверный формат роста! (рост должен быть целым числом > 0, и содержать только цифры)"},
            {"Wrong weight format! (weight should be decimal value > 0)",
                    "Неверный формат веса! (вес должен быть десятичным значением > 0)"},
            {"Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")",
                    "Неверное значение цвета! (можно выбрать одно из значений \"ЗЕЛЕНЫЙ, КРАСНЫЙ, ЧЕРНЫЙ, СИНИЙ, " +
                            "ЖЕЛТЫЙ\")"},
            {"Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")",
                    "Неверное значение национальности! (можно выбрать одно из значений \"РОССИЯ, ФРАНЦИЯ, ТАИЛАНД, " +
                            "СЕВЕРНАЯ_КОРЕЯ\")"},
            {"Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                    "be Double number.",
                    "Неверный формат местоположения! (\"X\" должно быть числом с плавающей запятой, " +
                            "\"Y\" должно быть целым числом, \"Z\" должно быть числом. Двойной точности."},

            // Execute Script
            {"File doesn't exist!", "Файл не существует!"},
            {"Wrong data in script. Process will be terminated.", "Неверные данные в скрипте. Процесс будет прекращен."},
            {"Recursion detected. Process will be terminated.", "Обнаружена рекурсия. Процесс будет прекращен."},

            // Help
            {"helpLabel", "Справка по доступным командам"},
            {"helpMsg",
                    "\n\n\"help\" - вывести справку по доступным командам\n" +
            "\"info\" - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, " +
            "количество элементов и т.д.)\n" +
            "\"show\" - вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "\"add\" - добавить новый элемент в коллекцию\n" +
            "\"update id\" - обновить значение элемента коллекции, id которого равен заданному\n" +
            "\"remove_by_id id\" - удалить элемент из коллекции по его id\n" +
            "\"clear\" - очистить коллекцию\n" +
            "\"execute_script file_name\" - считать и исполнить скрипт из указанного файла. " +
            "В скрипте содержатся команды в таком же виде, " +
            "в котором их вводит пользователь в интерактивном режиме.\n" +
            "\"head\" - вывести первый элемент коллекции\n" +
            "\"add_if_min\" - добавить новый элемент в коллекцию, если его значение меньше, " +
            "чем у наименьшего элемента этой коллекции\n" +
            "\"remove_greater\" - удалить из коллекции все элементы, превышающие заданный\n" +
            "\"remove_all_by_nationality nationality\" - удалить из коллекции все элементы, значение поля " +
            "nationality которого эквивалентно заданному\n" +
            "\"filter_by_nationality nationality\" - вывести элементы, значение поля nationality которых " +
            "равно заданному\n" +
            "\"print_field_descending_height\" - вывести значения поля " +
            "height всех элементов в порядке убывания\n"},

            // Static labels
            {"", ""},

            // Server responses
            {"", ""},

            // Error messages
            {"Server error. Please, try later.", "Ошибка сервера. Пожалуйста, попробуйте позже."},
            {"Welcome back, ","С возвращением, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Неверный формат имени пользователя или пароля (от 5 до 15 символов, и они должны " +
                            "содержать только английские буквы или цифры.)"},
            {"Wrong username or password!","Неверный логин или пароль!"},
            {"",""},
            {"",""},
            {"",""}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}