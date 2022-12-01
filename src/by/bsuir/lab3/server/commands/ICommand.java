package by.bsuir.lab3.server.commands;

import by.bsuir.lab3.server.commands.exceptions.CommandException;

import java.net.SocketAddress;

public interface ICommand {
    String execute(SocketAddress caller, String request) throws CommandException;
}
