package by.bsuir.lab3.server.commands;

import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.commands.impl.*;

public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();

    private CommandProvider() {

    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }
    public ICommand getCommand(String request) throws CommandException {
        if(request == null) throw new CommandException("No command");
        String[] arguments = request.split(" ");
        if(arguments.length < 1) throw new CommandException("No command");
        ICommand command;
        switch(arguments[0]){
            case "AUTH": command = new AuthCommand();
                break;
            case "DISCONNECT": command = new DisconnectCommand();
                break;
            case "EDIT": command = new EditCommand();
                break;
            case "VIEW": command = new ViewCommand();
                break;
            case "Create": command = new CreateCommand();
                break;
            default: throw new CommandException("No such command");
        }
        return command;
    }
}
