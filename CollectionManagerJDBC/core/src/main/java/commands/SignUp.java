package commands;

import interfaces.Command;

import java.io.IOException;

public class SignUp extends AuthorizationCommand implements Command {
    public SignUp(String username, String password) {
        super(username, password);
    }

    @Override
    public String execute() throws IOException {
        return null;
    }
}