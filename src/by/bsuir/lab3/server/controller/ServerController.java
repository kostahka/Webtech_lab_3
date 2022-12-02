package by.bsuir.lab3.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController extends Thread{
    public static final int PORT = 25565;
    private static final int BACKLOG = 50;
    private ServerSocket socket;
    @Override
    public void run(){
        try {
            socket = new ServerSocket(PORT, BACKLOG, null);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Server is running");

        while(true){
            Socket clientSocket;
            try {
                clientSocket = socket.accept();
                System.out.println("Client has connected");
                ServerClientController client = new ServerClientController(clientSocket, this);
                client.start();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
