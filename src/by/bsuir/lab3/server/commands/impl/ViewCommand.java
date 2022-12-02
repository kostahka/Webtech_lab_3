package by.bsuir.lab3.server.commands.impl;

import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.model.Record;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.net.SocketAddress;
import java.util.List;

public class ViewCommand  implements ICommand {
    public String execute(SocketAddress address, String request) throws CommandException {
        if(ServiceFactory.getInstance().getAuthService().getAuthType(address) == AuthType.UnAUTH)
        {
            return "Must be auth";
        }else{
            List<Record> recordList = ServiceFactory.getInstance().getRecordService().getAll();

            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("[\n");
            for (Record record : recordList) {
                resultBuilder.append("\t").append(record.toString()).append("\n");
            }
            resultBuilder.append("]");
            return resultBuilder.toString();
        }
    }
}
