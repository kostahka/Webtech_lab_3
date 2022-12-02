package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.net.SocketAddress;

public class CreateCommand  implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException {
        String[] arguments = request.split(" ");
        if (arguments.length != 3) throw new CommandException("CREATE invalid syntax");

        if (ServiceFactory.getInstance().getAuthService().getAuthType(address) != AuthType.ADMIN)
            return "Should be ADMIN";

        ServiceFactory.getInstance().getRecordService().addRecord(arguments[1], arguments[2]);

        return "Success";
    }
}
