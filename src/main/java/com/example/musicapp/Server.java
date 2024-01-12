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
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            System.out.println("Serwer został uruchomiony na porcie: " + port);
            while (true) {
                waitForClient();
                sendWelcomeMessage();
                sendLoginAnswer(receiveAndProcessDataLogin());
                closeClientSocket();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeServerSocket();
        }
    }

    private void waitForClient() {
        try {
            System.out.println("Oczekiwanie na klienta...");
            clientSocket = serverSocket.accept();
            System.out.println("Klient " + clientSocket.getInetAddress() + " został połączony.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendWelcomeMessage() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        String message = "Witaj";
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void sendLoginAnswer(Boolean out) throws IOException {
        System.out.println("Wysłano do klienta:");
        OutputStream outputStream = clientSocket.getOutputStream();
        if (out) {
            String message = "true";
            outputStream.write(message.getBytes());
            System.out.println("Wysłano do klienta: " + message);
            outputStream.flush();
        }
    }

    private Boolean receiveAndProcessDataLogin() {
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
                return true;
            } else {
                System.out.println("Błędny login lub hasło.");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeClientSocket() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
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
