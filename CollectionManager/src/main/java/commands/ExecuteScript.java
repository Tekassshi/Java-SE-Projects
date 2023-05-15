package commands;

import factories.CommandFactory;
import interfaces.AssemblableCommand;
import interfaces.Command;
import interfaces.CommandWithArg;
import managers.CollectionManager;
import managers.InputManager;

import java.io.*;

/**
 * Class for "execute_script" command. Command executes script given by user. Script contains commands in default form.
 * */
public class ExecuteScript extends AbstractCommand implements CommandWithArg {

    /**
     * Main constructor that using parent AbstractCommand constructor.
     * @see AbstractCommand
     * */
    public ExecuteScript(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * @param arg filepath to script that should be executed in String representation.
     * */
    @Override
    public void setArg(String arg) {
        super.setArgument(InputManager.readFile(arg));
    }

    /**
     * Method to executing current command. Runs stream using user file path.
     * @throws IOException if file path is incorrect.
     * */
    @Override
    public void execute() throws FileNotFoundException {
        String file_path = super.getArgument();

        if (file_path == null)
            return;

        System.out.println("\nExecuting user script:");

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
                    return;
                }

                if (command instanceof CommandWithArg) {
                    if (values.length < 2) {
                        System.out.println(ANSI_RED + "You should input argument to this command. (Command skipped)\n"
                                + ANSI_RESET);
                        return;
                    }

                    CommandWithArg tmp = (CommandWithArg) command;
                    tmp.setArg(values[1]);
                }

                if (command instanceof ExecuteScript){
                    if (CommandFactory.scripts.contains(((ExecuteScript) command).getArgument())) {
                        throw new IllegalArgumentException();
                    }
                    CommandFactory.scripts.add(((ExecuteScript) command).getArgument());
                }

                System.out.println("\nCommand \"" + values[0] + " \"executing...\n");

                if (command instanceof AssemblableCommand) {
                    AssemblableCommand tmp = (AssemblableCommand) command;
                    tmp.buildObjectFromScript(reader);
                }

                try {
                    command.execute();

                } catch (Exception e) {
                    System.out.println(ANSI_RED + "Wrong data in script. Process will be terminated.\n" + ANSI_RESET);
                    break;
                }

                if (command instanceof ExecuteScript)
                    CommandFactory.scripts.remove(((ExecuteScript) command).getArgument());

                line++;
            }
        }
        catch (NumberFormatException | NullPointerException | IOException e) {
            System.out.println(ANSI_RED + "Wrong data in script. Process will be terminated.\n" + ANSI_RESET);
        }
        catch (IllegalArgumentException e){
            System.out.println(ANSI_RED + "Recursion detected. Process will be terminated.\n" + ANSI_RESET);
        }
    }
}