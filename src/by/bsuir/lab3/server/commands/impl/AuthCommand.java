package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.net.SocketAddress;

public class AuthCommand implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException{
        String[] arguments = request.split(" ");
        if (arguments.length != 2) throw new CommandException("AUTH command should contain 1 argument");
        AuthType authType;
        try {
            authType = AuthType.valueOf(arguments[1]);
        } catch (IllegalArgumentException e) {
            throw new CommandException("No such auth type");
        }

        ServiceFactory.getInstance().getAuthService().setAuthType(address, authType);
        return "Success.";
    }
}
