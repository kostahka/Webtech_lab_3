package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.net.SocketAddress;

public class DisconnectCommand  implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException {
        ServiceFactory.getInstance().getAuthService().setAuthType(address, AuthType.UnAUTH);
        return "Disconnected";
    }
}
