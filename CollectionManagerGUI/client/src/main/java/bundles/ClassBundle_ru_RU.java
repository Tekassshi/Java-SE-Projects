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
            {"Coord X", "Коорд. X"},
            {"Location X", "Расположение Х"},
            {"Location Y", "Расположение Y"},
            {"Location Z", "Расположение Z"},
            {"Coord Y", "Коорд. Y"},
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
            {"Remove", "Удалить"},
            {"Update", "Обновить"},

            //Commands nested windows

            //Add
            {"Add new person", "Добавить новую запись"},
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

            // Info
            {"infoTitle", "Информация о коллекции"},

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

            // Command buttons
            {"Add", "Добавить"},
            {"Clear", "Очистить"},
            {"Execute", "Выполнить"},
            {"Filter", "Фильтровать"},
            {"Remove", "Удалить"},
            {"Update", "Обновить"},

            // Static labels
            {"Add new person", "Добавить новую запись"},
            {"Add new person if min", "Добавить новую запись, если она меньше"},
            {"Remove greater persons", "Удалить больших"},
            {"Add new person if min", "Добавить, если меньше"},
            {"Do you want to clear all your records?", "Хотите очистить все ваши записи?"},
            {"Execute script", "Выполнить скрипт"},
            {"Script filename", "Имя файла скрипта"},
            {"File not found.", "Файл не найден."},
            {"File doesn't exist!", "Файл не существует!"},
            {"Filter by nationality", "Фильтровать по национальности"},
            {"Remove by ID", "Удалить по ID"},
            {"Remove by nationality", "Удалить по национальности"},
            {"Update ID", "Обновить ID"},

            // Error messages
            {"Server error. Please, try later.", "Ошибка сервера. Пожалуйста, попробуйте позже."},
            {"Welcome back, ","С возвращением, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Неверный формат имени пользователя или пароля (от 5 до 15 символов, и они должны " +
                            "содержать только английские буквы или цифры.)"},
            {"Wrong username or password!","Неверный логин или пароль!"},
            {"Wrong id value! (Id should be > 0 and contain only digits)",
                    "Неправильное значение идентификатора! (Id должен быть > 0 и содержать только цифры)"},

            // Server responses
            {"Server response", "Результат с сервера"},
            {"\nCollection is empty!\n", "\nКоллекция пуста!\n"},
            {"Person was added successfully!\n", "Запись успешно добавлена!\n"},
            {"Error updating, inserting or loading data from database.\n", "Ошибка при обновлении, " +
                    "вставке или загрузке данных из базы данных.\n"},
            {"\nYour element value is bigger or the same than min element in collection\nElement will not be recorded.\n",
                    "\nЗначение вашего элемента больше или равно минимальному значению элемента в коллекции." +
                            "\nЭлемент не будет записан.\n"},
            {"\nCollection is empty!\n", "\nКоллекция пуста!\n"},
            {"\nId, that you want to update, doesn't exist!\nTry again\n", "\nИдентификатор, который вы хотите " +
                    "обновить, не существует!\nПопробуйте снова.\n"},
            {"\nYou can edit only your own records!\nTry again.\n", "\nВы можете редактировать только свои " +
                    "собственные записи!\nПопробуйте снова.\n"},
            {"\nPerson with given id was successfully updated!\n", "\nЗапись с указанным идентификатором успешно " +
                    "обновлена!\n"},
            {"\nPerson with given id value doesn't exist!Try again.\n", "\nЗапись с указанным значением " +
                    "идентификатора не существует! Попробуйте снова.\n"},
            {"\nPerson with given id was successfully removed!\n", "\nЗапись с указанным идентификатором " +
                    "успешно удалена!\n"},
            {"\nAll your own records successfully cleared.\n", "\nВсе ваши собственные записи успешно очищены.\n"},
            {"\nYour own records have been successfully removed!\n", "\nВаши собственные записи успешно удалены!\n"},
            {"Collection type:", "Тип коллекции:"},
            {"Number of elements:", "Количество элементов:"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}