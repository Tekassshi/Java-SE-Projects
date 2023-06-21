package readers;

import commands.AbstractCommand;
import commands.ExecuteScript;
import factories.CommandFactory;
import interfaces.AssemblableCommand;
import interfaces.Command;
import interfaces.CommandWithArg;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Queue;

public class ScriptReader {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void ReadScript(String file_path, Queue<Command> commandQueue) throws IOException {

        if (file_path == null)
            throw new FileNotFoundException();

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
                   continue;
                }
                if (command instanceof CommandWithArg) {
                    if (values.length < 2) {
                        System.out.println(ANSI_RED + "You should input argument to this command.\n"
                                + ANSI_RESET);
                       continue;
                    }

                    CommandWithArg tmp = (CommandWithArg) command;
                    tmp.setArg(values[1]);
                }
                if (command instanceof ExecuteScript){
                    String path = ((AbstractCommand) command).getArgument();
                    if (CommandFactory.scripts.contains(path)) {
                        commandQueue.clear();
                        CommandFactory.scripts.clear();
                        throw new RuntimeException();
                    }

                    ScriptReader.ReadScript(path, commandQueue);
                    continue;
                }

                System.out.println("\nCommand \"" + values[0] + " \"executing...\n");

                if (command instanceof AssemblableCommand) {
                    AssemblableCommand tmp = (AssemblableCommand) command;
                    try {
                        tmp.buildObjectFromScript(reader);
                    }
                    catch (IOException e) {
                        commandQueue.clear();
                        CommandFactory.scripts.clear();
                        throw new IOException();
                    }
                }

                commandQueue.add(command);
                line++;
            }
            CommandFactory.scripts.remove(file_path);
        }
    }
}