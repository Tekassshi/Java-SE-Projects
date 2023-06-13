package readers;

import factories.CommandFactory;
import interfaces.AssemblableCommand;
import interfaces.Command;
import interfaces.CommandWithArg;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class ConsoleReader {
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static Object readCommand(BufferedReader reader) throws IOException {

        System.out.print(GREEN_BOLD + ">>> " + ANSI_RESET);
        String[] values = reader.readLine().split(" ");

        try {
            if (values.length < 1)
                throw new NullPointerException();
            if (values[0].equals("exit")) {
                return "exit";
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
            return command;
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
        return null;
    }
}