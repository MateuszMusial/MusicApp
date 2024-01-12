package com.example.musicapp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Klient {

    private static Socket socket;

    public static void main(String[] args) {
        int startingPort = 12345;
        int maxAttempts = 10;

        for (int i = 0; i < maxAttempts; i++) {
            try {
                // Adres serwera
                String serverAddress = "localhost";

                // Próba nawiązania połączenia na danym porcie
                int currentPort = startingPort + i;
                if (isPortAvailable(serverAddress, currentPort)) {
                    System.out.println("Port " + currentPort + " jest dostępny.");
                    openConnection(serverAddress, currentPort);
                    receiveWelcomeMessage();
                    sendToServerLogin("Admin", "Admin");
                    //sendRegistrationToServer("imie", "nazwisko", "user", "user");
                    Thread.sleep(10000);

                    // LoginController

                    //closeConnection();
                    break; // Przerwij pętlę, gdy nawiązane zostanie połączenie na dostępnym porcie
                } else {
                    System.out.println("Port " + currentPort + " jest zajęty.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static boolean isPortAvailable(String host, int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }



    private static void openConnection(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Klient Pawel został podłączony do serwera na porcie " + serverPort + ".");
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
