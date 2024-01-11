package com.example.musicapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class Server implements Callable<Void> {
    private static final int SERVER_PORT = 12345;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MusicApp?useSSL=false&serverTimezone=UTC";


    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");


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

    private void receiveAndProcessData() throws IOException {
        try (InputStream inputStream = clientSocket.getInputStream()) {
            //Class.forName("com.mysql.cj.jdbc.Driver");

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
            try (Connection connection = DriverManager.getConnection(DB_URL, "root", "password")) {
                String query = "SELECT * FROM uzytkownik WHERE login = ? AND haslo = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Zalogowano pomyślnie.");
                        } else {
                            System.out.println("Błędny login lub hasło.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
