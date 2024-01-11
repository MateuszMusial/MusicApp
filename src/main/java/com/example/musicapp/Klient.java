package com.example.musicapp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Klient {
    String login = "Admin";
    String password = "Admin";

    private static Socket socket;

    public static void main(String[] args) {
        try {
            // Adres i port serwera
            String serverAddress = "localhost";
            int serverPort = 12345;
            openConnection(serverAddress, serverPort);
            receiveWelcomeMessage();
            //sendToServerLogin("Admin", "Admin");
            //sendRegistrationToServer("imie", "nazwisko", "user", "user");

            // LoginController

            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openConnection(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Klient Pawel został podłączony do serwera.");
    }

    private static void closeConnection() throws IOException {
        socket.close();
        System.out.println("Klient Pawel został odłączony od serwera.");
    }

    private static void receiveWelcomeMessage() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String receivedMessage = new String(buffer, 0, bytesRead);
        System.out.println("Otrzymano od serwera: " + receivedMessage);
    }

    public static void sendToServerLogin(String login, String password) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(login.getBytes());
        outputStream.flush();

        outputStream.write(password.getBytes());
        outputStream.flush();
    }

    public static void sendRegistrationToServer(String firstName, String lastName, String login, String password) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            // Wyślij imię
            dataOutputStream.writeUTF(firstName);
            dataOutputStream.flush();

            // Wyślij nazwisko
            dataOutputStream.writeUTF(lastName);
            dataOutputStream.flush();

            // Wyślij login
            dataOutputStream.writeUTF(login);
            dataOutputStream.flush();

            // Wyślij hasło
            dataOutputStream.writeUTF(password);
            dataOutputStream.flush();
        }
    }
}
