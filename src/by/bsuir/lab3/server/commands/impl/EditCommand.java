package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;

import java.net.SocketAddress;

public class EditCommand  implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException {
        return "OK";
    }
}
