package readers;

import commands.AbstractCommand;
import commands.ExecuteScript;
import factories.CommandFactory;
import interfaces.AssemblableCommand;
import interfaces.Command;
import interfaces.CommandWithArg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Queue;

public class ScriptReader {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static boolean ReadScript(String file_path, Queue<Command> commandQueue){
        if (file_path == null)
            return false;

        System.out.println("\nExecuting user script");
        CommandFactory.scripts.add(file_path);

        File file = new File(file_path);
        int line = 1;

        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String input;

            while ((input = reader.readLine()) != null) {
                String[] values = input.split(" ");

                if (values[0].equals("exit"))
                    break;

                Command command = CommandFactory.getCommand(values[0]);

                if (command == null) {
                    System.out.println(ANSI_RED + "Line: " + line + ANSI_RESET);
                    System.out.println(ANSI_RED + "Wrong command.\n" + ANSI_RESET);
                    return false;
                }
                if (command instanceof CommandWithArg) {
                    if (values.length < 2) {
                        System.out.println(ANSI_RED + "You should input argument to this command. (Command skipped)\n"
                                + ANSI_RESET);
                        return false;
                    }

                    CommandWithArg tmp = (CommandWithArg) command;
                    tmp.setArg(values[1]);
                }
                if (command instanceof ExecuteScript){
                    String path = ((AbstractCommand) command).getArgument();
                    if (CommandFactory.scripts.contains(path)) {
                        commandQueue.clear();
                        throw new RuntimeException();
                    }
                    CommandFactory.scripts.add(path);
                    boolean result = ScriptReader.ReadScript(path, commandQueue);
                    if (result == false)
                        return false;
                    continue;
                }

                System.out.println("\nCommand \"" + values[0] + " \"executing...\n");

                if (command instanceof AssemblableCommand) {
                    AssemblableCommand tmp = (AssemblableCommand) command;
                    try {
                        tmp.buildObjectFromScript(reader);
                    }
                    catch (IOException | InputMismatchException e){
                        System.out.println(ANSI_RED + "Wrong data in script. Process terminated.\n"
                                + ANSI_RESET);
                        return false;
                    }
                }

                commandQueue.add(command);
                line++;
                CommandFactory.scripts.remove(file_path);
            }
            return true;
        }
        catch (IllegalArgumentException | IOException | InputMismatchException | NullPointerException e) {
            System.out.println(ANSI_RED + "Wrong data in script. Process will be terminated.\n" + ANSI_RESET);
            return false;
        }
        catch (RuntimeException e){
            System.out.println(ANSI_RED + "Recursion detected. Process will be terminated.\n" + ANSI_RESET);
            return false;
        }
    }
}