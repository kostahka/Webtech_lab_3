package by.bsuir.lab3.client;

import by.bsuir.lab3.client.controller.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {

        ClientController client = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Commands:\n" +
                    "CONNECT\n" +
                    "EXIT");
            String input;
            while((input = reader.readLine()) != null){
                if(client != null && client.isRunning()){
                    client.sendMessage(input);
                }else{
                    if(input.equals("CONNECT")){
                        client = new ClientController();
                        client.start();
                    } else if (input.equals("EXIT")) {
                        break;
                    }
                }
            }
            if(client != null && client.isRunning()){
                client.sendMessage("DISCONNECT");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
