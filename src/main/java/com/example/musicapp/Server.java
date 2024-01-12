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
                System.out.println("Klient "+ clientSocket.getPort()+" został podłączony do serwera na porcie " + serverSocket.getLocalPort() + ".");
                handleClient();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private void handleClient() {
        try {
            //sendWelcomeMessage();
            Boolean change = receiveAndProcessDataLogin();
            System.out.println(change);
            sendWelcomeMessage();
            //sendLoginAnswer(change);
            // receiveAndProcessDataRegistration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendWelcomeMessage() throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        String message = "true";
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void sendLoginAnswer(Boolean out) throws IOException {

        OutputStream outputStream = clientSocket.getOutputStream();
            if (!clientSocket.isClosed()) {
                if (out) {
                    String message = "true";
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                    System.out.println("Wysłano true");
                } else {
                    String message = "false";
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                    System.out.println("Wysłano false");
                }
            } else {
                System.out.println("Client socket is already closed.");
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

            if (!clientSocket.isClosed())
            {
                System.out.println("Client socket is not closed.");
            }
            else
            {
                System.out.println("Client socket is already closed.");
            }

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
