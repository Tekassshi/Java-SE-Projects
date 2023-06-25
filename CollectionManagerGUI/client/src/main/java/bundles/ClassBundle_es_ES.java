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
            {"Coord X", "Coord. X"},
            {"Location X", "Ubicación X"},
            {"Location Y", "Ubicación Y"},
            {"Location Z", "Ubicación Z"},
            {"Coord Y", "Coord. Y"},
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
            {"Remove", "Eliminar"},
            {"Update", "Actualizar"},

            // Inbäddade kommandofönster

            // Lägg till
            {"Add new person", "Agregar nuevo registro"},
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

            // Info
            {"infoTitle", "Información de la colección"},

            // Help
            {"helpLabel", "Ayuda sobre comandos disponibles"},
            {"helpMsg","\"help\" - mostrar información de ayuda sobre los comandos disponibles.\n" +
                    "\"info\" - mostrar información sobre la colección (tipo, fecha de inicialización, " +
                    "número de elementos, etc.) en el flujo de salida estándar.\n" +
                    "\"show\" - mostrar todos los elementos de la colección en representación de cadena " +
                    "en el flujo de salida estándar.\n" +
                    "\"add\" - agregar un nuevo elemento a la colección.\n" +
                    "\"update id\" - actualizar el valor del elemento de la colección con el id especificado.\n" +
                    "\"remove_by_id id\" - eliminar un elemento de la colección por su id.\n" +
                    "\"clear\" - limpiar la colección.\n" +
                    "\"execute_script file_name\" - leer y ejecutar un script desde el archivo especificado. " +
                    "El script contiene comandos en el mismo formato que los ingresa el usuario en el modo " +
                    "interactivo.\n" +
                    "\"head\" - mostrar el primer elemento de la colección.\n" +
                    "\"add_if_min\" - agregar un nuevo elemento a la colección si su valor es menor que el del " +
                    "elemento más pequeño en la colección.\n" +
                    "\"remove_greater\" - eliminar todos los elementos de la colección que sean mayores que el " +
                    "especificado.\n" +
                    "\"remove_all_by_nationality nationality\" - eliminar todos los elementos de la colección cuyo " +
                    "valor del campo de nacionalidad sea equivalente al especificado.\n" +
                    "\"filter_by_nationality nationality\" - mostrar elementos cuyo valor del campo de nacionalidad " +
                    "sea igual al especificado.\n" +
                    "\"print_field_descending_height\" - mostrar los valores del campo de altura de todos los " +
                    "elementos en orden descendente."},

            // Ejekvera skript
            {"File doesn't exist!", "¡El archivo no existe!"},
            {"Wrong data in script. Process will be terminated.", "Datos incorrectos en el script. El proceso se " +
                    "terminará."},
            {"Recursion detected. Process will be terminated.", "Se detectó recursión. El proceso se terminará."},

            // Command buttons
            {"Add", "Agregar"},
            {"Clear", "Limpiar"},
            {"Execute", "Ejecutar"},
            {"Filter", "Filtrar"},
            {"Remove", "Eliminar"},
            {"Update", "Actualizar"},

            // Statiska etiketter
            {"Add new person", "Agregar nueva persona"},
            {"Add new person if min", "Agregar nueva persona si es mínima"},
            {"Remove greater persons", "Eliminar personas mayores"},
            {"Add new person if min", "Agregar nueva persona si es mínima"},
            {"Do you want to clear all your records?", "¿Deseas borrar todos tus registros?"},
            {"Execute script", "Ejecutar script"},
            {"Script filename", "Nombre de archivo del script"},
            {"File not found.", "Archivo no encontrado."},
            {"File doesn't exist!", "¡El archivo no existe!"},
            {"Filter by nationality", "Filtrar por nacionalidad"},
            {"Remove by ID", "Eliminar por ID"},
            {"Remove by nationality", "Eliminar por nacionalidad"},
            {"Update ID", "Actualizar ID"},

            // Felmeddelanden
            {"Server error. Please, try later.", "Error del servidor. Por favor, inténtelo más tarde."},
            {"Session timeout. Connect again.", "Tiempo de sesión agotado. Conéctese nuevamente."},
            {"Welcome back, ","Bienvenido de nuevo, "},
            {"Wrong username or password format (From 5 to 15 symbols and they " +
                    "should contains only english letters or digits.)",
                    "Formato incorrecto de nombre de usuario o contraseña (de 5 a 15 símbolos y deben contener " +
                            "solo letras o dígitos en inglés)."},
            {"Wrong username or password!","¡Nombre de usuario o contraseña incorrectos!"},
            {"Wrong id value! (Id should be > 0 and contain only digits)", "¡Valor de ID incorrecto! " +
                    "(El ID debe ser > 0 y contener solo dígitos)"},

            // Server responses
            {"Server response", "Respuesta del servidor"},
            {"\nCollection is empty!\n", "\n¡La colección está vacía!\n"},
            {"Person was added successfully!\n", "¡Persona añadida exitosamente!\n"},
            {"Error updating, inserting or loading data from database.\n", "Error al actualizar, " +
                    "insertar o cargar datos desde la base de datos.\n"},
            {"\nYour element value is bigger or the same than min element in collection\nElement will " +
                    "not be recorded.\n", "\nEl valor de tu elemento es mayor o igual que el valor mínimo " +
                    "en la colección\nEl elemento no será registrado.\n"},
            {"\nCollection is empty!\n", "\n¡La colección está vacía!\n"},
            {"\nId, that you want to update, doesn't exist!\nTry again\n", "\n¡El ID que deseas actualizar " +
                    "no existe! Inténtalo de nuevo\n"},
            {"\nYou can edit only your own records!\nTry again.\n", "\n¡Solo puedes editar tus propios " +
                    "registros! Inténtalo de nuevo.\n"},
            {"\nPerson with given id was successfully updated!\n", "\nLa persona con el ID proporcionado se " +
                    "actualizó correctamente.\n"},
            {"\nPerson with given id value doesn't exist!Try again.\n", "\nLa persona con el valor de ID " +
                    "proporcionado no existe. Inténtalo de nuevo.\n"},
            {"\nPerson with given id was successfully removed!\n", "\nLa persona con el ID proporcionado se " +
                    "eliminó correctamente.\n"},
            {"\nAll your own records successfully cleared.\n", "\nTodos tus registros propios se borraron " +
                    "correctamente.\n"},
            {"\nYour own records have been successfully removed!\n", "\nTus propios registros se han eliminado " +
                    "correctamente.\n"},
            {"Collection type:", "Tipo de colección:"},
            {"Number of elements:", "Número de elementos:"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}