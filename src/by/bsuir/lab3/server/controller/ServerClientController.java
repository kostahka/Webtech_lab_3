package by.bsuir.lab3.server.controller;

import by.bsuir.lab3.server.commands.CommandProvider;
import by.bsuir.lab3.server.commands.ICommand;
import by.bsuir.lab3.server.commands.exceptions.CommandException;
import by.bsuir.lab3.server.commands.impl.DisconnectCommand;
import by.bsuir.lab3.server.model.AuthType;
import by.bsuir.lab3.server.service.ServiceFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerClientController extends Thread{
    private final Socket socket;
    private final ServerController serverController;

    private BufferedReader reader;
    private PrintWriter writer;
    private boolean running;

    public ServerClientController(Socket socket, ServerController serverController) {
        this.socket = socket;
        this.serverController = serverController;
    }

    @Override
    public void run(){
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        sendMessage("Available commands:\n" +
                "AUTH (role = USER/ADMIN)\n" +
                "DISCONNECT\n" +
                "VIEW\n" +
                "CREATE (firstname) (lastname)\n" +
                "EDIT (id) (firstname) (lastname)");

        running = true;

        SocketAddress user = socket.getRemoteSocketAddress();

        do{
            try {
                String request = readMessage();
                if(request == null)
                    break;

                // Do command
                ICommand command = CommandProvider.getInstance().getCommand(request);
                String response = command.execute(user, request);
                sendMessage(response);

                if(command instanceof DisconnectCommand)
                    running = false;
            }
            catch (CommandException e){
                e.printStackTrace();
                sendMessage(e.getMessage());
            }
        }while (running);

        ServiceFactory.getInstance().getAuthService()
                .setAuthType(user, AuthType.UnAUTH);

        try {
            socket.close();
            System.out.println("Client disconnected");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private String readMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}
