package by.bsuir.lab3.client;

import by.bsuir.lab3.client.controller.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) {
        ClientController client = new ClientController();
        client.run();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (client.isRunning()) {
                String response = reader.readLine();
                client.sendMessage(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
