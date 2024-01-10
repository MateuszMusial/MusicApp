package com.example.musicapp;

import ServerPackage.DBInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.Callable;

public class Server implements Callable<Void> {
    private static final int SERVER_PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() throws Exception {
        try {
            System.out.println("Serwer Pawel został uruchomiony.");
            serverSocket = new ServerSocket(SERVER_PORT);

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Klient Pawel został podłączony do serwera.");
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
            receiveAndProcessData();

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

    private Boolean receiveAndProcessData() throws IOException {
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

            // Przykładowa obsługa bazy danych PostgreSQL
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/twojastacjabazodanowa", "login", "haslo")) {
                String query = "SELECT * FROM uzytkownicy WHERE login = ? AND haslo = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Zalogowano pomyślnie.");
                            return true;
                        } else {
                            System.out.println("Błędny login lub hasło.");
                            return false;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Przykładowa metoda obsługi bazy danych
    private void handleDatabase(Connection connection) {
        DBInterface dbInterface = new DBInterface(connection);
        boolean userExists = dbInterface.checkUserCredentials("admin", "admin");
        System.out.println("Czy użytkownik istnieje w bazie danych? " + userExists);

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
