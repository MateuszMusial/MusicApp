package com.example.musicapp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Klient {

    private static Socket socket;

    public static void main(String[] args) {
        int Port = 12345;
        String serverAddress = "localhost";
        try {
            socket = new Socket(serverAddress, Port);
            System.out.println("Klient Pawel został podłączony do serwera na porcie " + Port + ".");
            receiveWelcomeMessage();
            sendToServerLogin("Admin", "Admin");
            //sendRegistrationToServer();
            closeConnection();
        } catch (IOException e) {
            System.out.println("Port " + Port + " jest zajęty.");
            //e.printStackTrace();
        }
    }

    private static Boolean openConnection(String serverAddress, int serverPort) throws IOException {
        try(Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Klient Pawel został podłączony do serwera na porcie " + serverPort + ".");
            return true;
        }
        catch (IOException e) {
            System.out.println("Port " + serverPort + " jest zajęty.");
            return false;
        }
    }

    private static void closeConnection() throws IOException {
        // Shutdown the output to signal the server that no more data will be sent
        socket.shutdownOutput();

        // Give some time for the server to process the shutdown
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the socket
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

    private static void sendToServerLogin(String login, String password) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(login.getBytes());
        outputStream.flush();

        outputStream.write(password.getBytes());
        outputStream.flush();
    }

    private static void sendRegistrationToServer(String firstName, String lastName, String login, String password) throws IOException {
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
