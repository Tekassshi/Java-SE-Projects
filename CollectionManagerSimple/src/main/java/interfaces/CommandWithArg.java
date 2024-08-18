package interfaces;

/** Interface contains methods for commands, that should get some argument before executing/*/
public interface CommandWithArg extends Command {
    /**
     * CommandWithArg interface setter method for setting String value of argument for command executing.
     * */
    void setArg(String arg);
}