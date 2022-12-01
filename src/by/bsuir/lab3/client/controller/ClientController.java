package by.bsuir.lab3.client.controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientController extends Thread {

    PrintWriter writer;
    BufferedReader reader;
    boolean running = true;
    @Override
    public void run(){
        try{
            Socket socket = new Socket(InetAddress.getLocalHost(), 5555);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request;
            while((request = reader.readLine()) != null){
                System.out.println(request);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        running = false;
    }
    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }
    public boolean isRunning(){
        return running;
    }
}
