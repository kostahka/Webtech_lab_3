package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.net.SocketAddress;

public class EditCommand  implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException {
        String[] arguments = request.split(" ");
        if (arguments.length != 4) throw new CommandException("Invalid syntax EDIT");

        if (ServiceFactory.getInstance().getAuthService().getAuthType(address) != AuthType.ADMIN)
            return "Should be Admin";

        int id;
        try {
            id = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException ignored) {
            return "Invalid id";
        }

        if (!ServiceFactory.getInstance().getRecordService().containsKey(id))
            return "No such record";

        ServiceFactory.getInstance().getRecordService().editRecord(id, arguments[2], arguments[3]);
        return "Success";
    }
}
