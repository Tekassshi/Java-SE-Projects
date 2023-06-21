package bundles;

import java.util.ListResourceBundle;

public class ClassBundle_es_ES extends ListResourceBundle {

    private static final Object[][] contents = {
            // Lang Button
            {"lang", "ES"},

            // Locale code
            {"locale", "es"},

            // Port choice
            {"Port", "Puerto"},
            {"Connect", "Conectar"},

            // Inloggning
            {"signUpMsg", "¿Aún no tienes una cuenta?"},
            {"signUp", "registrarse"},
            {"Username", "Nombre de usuario"},
            {"Password", "Contraseña"},
            {"logIn", "Iniciar sesión"},

            // Registrera sig
            {"Creating a new account", "Creando una nueva cuenta"},
            {"Repeat password", "Repetir contraseña"},
            {"Sign Up", "Registrarse"},
            {"Welcome, ", "Bienvenido, "},
            {"This username is already exists", "Este nombre de usuario ya existe"},
            {"Passwords are different!", "¡Las contraseñas son diferentes!"},
            {"RegSucc", "¡Se ha registrado exitosamente!"},

            // Välkomstsida
            {"Next", "Siguiente"},
            {"A new account has been successfully created!", "¡Se ha creado una nueva cuenta con éxito!"},

            // Huvudkontrollsidan
            {"ID", "ID"},
            {"Name", "Nombre"},
            {"Coord X", "Coordenada X"},
            {"Coordinate X","Coordenada X"},
            {"Coordinate Y","Coordenada Y"},
            {"Location X", "Ubicación X"},
            {"Location Y", "Ubicación Y"},
            {"Location Z", "Ubicación Z"},
            {"Coord Y", "Coordenada Y"},
            {"Creation Date", "Fecha de creación"},
            {"Height", "Altura"},
            {"Weight", "Peso"},
            {"Eye color", "Color de ojos"},
            {"Nationality", "Nacionalidad"},
            {"Location X", "Ubic. X"},
            {"Location Y", "Ubic. Y"},
            {"Location Z", "Ubic. Z"},
            {"Owner", "Propietario"},
            {"Command", "Dominio"},
            {"Exit", "Salida"},
            {"Filter", "Filtrar"},
            {"GREEN", "VERDE"},
            {"RED", "ROJO"},
            {"BLACK", "NEGRO"},
            {"BLUE", "AZUL"},
            {"YELLOW", "AMARILLO"},
            {"RUSSIA", "RUSIA"},
            {"FRANCE", "FRANCIA"},
            {"THAILAND", "TAILANDIA"},
            {"NORTH_KOREA", "COREA DEL NORTE"},

            // Inbäddade kommandofönster

            // Lägg till
            {"AddNewPerson", "Agregar nuevo registro"},
            {"Add", "Agregar"},
            {"Wrong name format! Name should contain at least 1 symbol and only letters supported",
                    "¡Formato de nombre incorrecto! El nombre debe contener al menos 1 símbolo y solo se admiten " +
                            "letras."},
            {"Wrong coordinates format! (\"X\" should be integer number, that > -783, \"Y\" should be " +
                    "long integer number.)", "¡Formato de coordenadas incorrecto! (\"X\" debe ser un número entero " +
                    "mayor que -783, \"Y\" debe ser un número entero largo)"},
            {"Wrong height format! (height should be integer > 0, and only digits supported)",
                    "¡Formato de altura incorrecto! (la altura debe ser un número entero mayor que 0 y solo se admiten "
                            + "dígitos)"},
            {"Wrong weight format! (weight should be decimal value > 0)",
                    "¡Formato de peso incorrecto! (el peso debe ser un valor decimal mayor que 0)"},
            {"Wrong color value! (you can choose one of the values \"GREEN, RED, BLACK, BLUE, YELLOW\")",
                    "¡Valor de color incorrecto! (puedes elegir uno de los valores \"VERDE, ROJO, NEGRO, AZUL, " +
                            "AMARILLO\")"},
            {"Wrong nationality value! (you can choose one of the values \"RUSSIA, FRANCE, THAILAND, NORTH_KOREA\")",
                    "¡Valor de nacionalidad incorrecto! (puedes elegir uno de los valores \"RUSIA, FRANCIA, TAILANDIA, "
                            + "COREA_DEL_NORTE\")"},
            {"Wrong location format! (\"X\" should be Float number, \"Y\" should be Integer number, \"Z\" should " +
                    "be Double number.",
                    "¡Formato de ubicación incorrecto! (\"X\" debe ser un número decimal, \"Y\" debe ser un número " +
                            "entero, " +
                            "\"Z\" debe ser un número doble)"},

            // Ejekvera skript
            {"File doesn't exist!", "¡El archivo no existe!"},
            {"Wrong data in script. Process will be terminated.", "Datos incorrectos en el script. El proceso se " +
                    "terminará."},
            {"Recursion detected. Process will be terminated.", "Se detectó recursión. El proceso se terminará."},

            // Hjälp
            {"helpLabel", "Ayuda sobre comandos disponibles"},
            {"helpMsg",
                    "\n\n\"help\" - muestra ayuda sobre los comandos disponibles\n" +
                            "\"info\" - muestra información sobre la colección (tipo, fecha de inicialización, " +
                            "cantidad de elementos, etc.)\n" +
                            "\"show\" - muestra todos los elementos de la colección en formato de cadena en la " +
                            "salida estándar\n" +
                            "\"add\" - agrega un nuevo elemento a la colección\n" +
                            "\"update id\" - actualiza el valor de un elemento de la colección cuyo id es el " +
                            "especificado\n" +
                            "\"remove_by_id id\" - elimina un elemento de la colección por su id\n" +
                            "\"clear\" - borra la colección\n" +
                            "\"execute_script nombre_de_archivo\" - lee y ejecuta un script desde el archivo " +
                            "especificado. " +
                            "El script debe contener comandos en el mismo formato que los ingresados por el usuario " +
                            "en " +
                            "modo interactivo.\n" +
                            "\"head\" - muestra el primer elemento de la colección\n" +
                            "\"add_if_min\" - agrega un nuevo elemento a la colección si su valor es menor que el del "
                            +
                            "elemento más pequeño de la colección\n" +
                            "\"remove_greater\" - elimina de la colección todos los elementos que superan el valor " +
                            "especificado\n" +
                            "\"remove_all_by_nationality nacionalidad\" - elimina de la colección todos los " +
                            "elementos cuyo valor del campo " +
                            "nacionalidad es equivalente al especificado\n" +
                            "\"filter_by_nationality nacionalidad\" - muestra los elementos cuyo valor del campo " +
                            "nacionalidad es igual al especificado\n" +
                            "\"print_field_descending_height\" - muestra los valores del campo altura de todos " +
                            "los elementos en orden descendente\n"},

            // Statiska etiketter
            {"", ""},

            // Server-svar
            {"", ""},

            // Felmeddelanden
            {"Server error. Please, try later.", "Error del servidor. Por favor, inténtelo más tarde."},
            {"Welcome back, ","Bienvenido de nuevo, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Formato incorrecto de nombre de usuario o contraseña (de 5 a 15 símbolos y deben contener " +
                            "solo letras o dígitos en inglés)."},
            {"Wrong username or password!","¡Nombre de usuario o contraseña incorrectos!"},
            {"",""},
            {"",""},
            {"",""}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}