package by.bsuir.lab3.server.controller;

import java.io.*;
import java.net.Socket;

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

        do{
            try {
                String request = readMessage();
                if(request == null)
                    break;

                // Do command

                if(request.equals("DISCONNECT"))
                    running = false;

                sendMessage("OK");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }while (running);

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
