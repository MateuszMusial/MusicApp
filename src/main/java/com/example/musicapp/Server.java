// Klasa Server
package com.example.musicapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Server implements Callable<Void> {

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    public Server(int port) {
        this.port = port;
    }


    @Override
    public Void call() throws Exception {
        try {
            System.out.println("Serwer Pawel został uruchomiony na porcie " + port + ".");
            serverSocket = new ServerSocket(port);

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Klient Pawel został podłączony do serwera na porcie " + port + ".");
                handleClient();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSocket();
        }

        return null;
    }


    private void handleClient() {
        try {
            sendWelcomeMessage();
            receiveAndProcessDataLogin();
            // receiveAndProcessDataRegistration();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeClientSocket();
        }
    }

    private void sendWelcomeMessage() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        String message = "Witaj, to jest serwer!";
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void receiveAndProcessDataLogin() throws IOException {
        try (InputStream inputStream = clientSocket.getInputStream()) {
            byte[] buffer = new byte[1024];

            // Odbierz login
            int bytesRead = inputStream.read(buffer);
            String login = new String(buffer, 0, bytesRead).trim();
            System.out.println("Otrzymano login od klienta: " + login);

            // Odbierz hasło
            bytesRead = inputStream.read(buffer);
            String password = new String(buffer, 0, bytesRead).trim();
            System.out.println("Otrzymano hasło od klienta: " + password);

            // obsługa bazy danych MySQL
            if (DatabaseHandler.loginUser(login, password)) {
                System.out.println("Zalogowano pomyślnie.");
            } else {
                System.out.println("Błędny login lub hasło.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
