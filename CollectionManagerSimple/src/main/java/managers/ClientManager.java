package managers;

import factories.CommandFactory;
import interfaces.AssemblableCommand;
import interfaces.Command;
import interfaces.CommandWithArg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

/**
 * Class that contains one method "run()", which is responsible for manage user input.
 * */
public class ClientManager {
    private final String GREEN_BOLD = "\033[1;32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    /**
     * Main method for assemble commands from user input stream and call their execution.
     * @throws IOException when has error with input stream.
     * */
    public void run() throws IOException {
        System.out.println(GREEN_BOLD + "\n--- Welcome to collection manager! ---\n" + ANSI_RESET);
        System.out.println("(type \"help\" - to get reference, \"exit\" - to terminate)\n");

        // Request to create collection manager and check paths
        CollectionManager collectionManager = new CollectionManager();
        CommandFactory.setCollectionManager(collectionManager);
        collectionManager.getCollectionPath();
        collectionManager.load();

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);

        while (true){
            System.out.print(GREEN_BOLD + ">>> " + ANSI_RESET);
            String[] values = reader.readLine().split(" ");

            try {
                if (values.length < 1)
                    throw new NullPointerException();
                if (values[0].equals("exit")) {
                    System.out.print(ANSI_RED + "Do you want exit without saving? [yes / no]: " + ANSI_RESET);
                    String input = reader.readLine();

                    if (input == null){
                        throw new InputMismatchException();
                    }
                    else if (input.equals("yes"))
                        break;
                    else if (input.equals("no")) {
                        System.out.println();
                        continue;
                    }
                    else {
                        throw new InputMismatchException();
                    }
                }

                Command command = CommandFactory.getCommand(values[0]);
                if (command == null)
                    throw new NullPointerException();

                if (command instanceof CommandWithArg){
                    if (values.length < 2)
                        throw new IOException();

                    CommandWithArg tmp = (CommandWithArg) command;
                    tmp.setArg(values[1]);
                }

                if (command instanceof AssemblableCommand) {
                    AssemblableCommand tmp = (AssemblableCommand) command;
                    tmp.buildObject();
                }

                command.execute();
            }
            catch (NullPointerException e){
                System.out.println(ANSI_RED + "\nWrong Command!" + ANSI_RESET);
                System.out.println(ANSI_RED + "Try again (type \"help\" - to get reference)\n" + ANSI_RESET);
            }
            catch (IOException e){
                System.out.println(ANSI_RED + "\nYou should input argument for this command!" + ANSI_RESET);
                System.out.println(ANSI_RED + "Try again.\n" + ANSI_RESET);
            }
            catch (InputMismatchException e){
                System.out.println(ANSI_RED + "\nWrong data." + ANSI_RESET);
                System.out.println(ANSI_RED + "Try again.\n" + ANSI_RESET);
            }
        }
    }
}