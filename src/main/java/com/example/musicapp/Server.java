package com.example.musicapp;

import java.io.DataInputStream;
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
            //receiveAndProcessDataLogin();
            receiveAndProcessDataRegistration();

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

    private void receiveAndProcessDataRegistration() throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {
            // Odbierz imię
            String firstName = dataInputStream.readUTF();
            System.out.println("Otrzymano imię od klienta do rejestracji: " + firstName);

            // Odbierz nazwisko
            String lastName = dataInputStream.readUTF();
            System.out.println("Otrzymano nazwisko od klienta do rejestracji: " + lastName);

            // Odbierz login
            String login = dataInputStream.readUTF();
            System.out.println("Otrzymano login od klienta do rejestracji: " + login);

            // Odbierz hasło
            String password = dataInputStream.readUTF();
            System.out.println("Otrzymano hasło od klienta do rejestracji: " + password);


            // obsługa bazy danych MySQL
            try (Connection connection = DriverManager.getConnection(DB_URL, "root", "password")) {
                String query = "INSERT INTO uzytkownik (ID_Uzytkownika, imie, nazwisko, login, haslo) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    // Pobierz ostatnio dodane ID i zwiększ o 1
                    int nextId = getLastInsertedId(connection) + 1;

                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setString(2, firstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, login);
                    preparedStatement.setString(5, password);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Zarejestrowano pomyślnie. ID_Uzytkownika: " + nextId);
                    } else {
                        System.out.println("Nie udało się zarejestrować użytkownika.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    private static int getLastInsertedId(Connection connection) throws SQLException {
        String query = "SELECT ID_Uzytkownika FROM uzytkownik ORDER BY ID_Uzytkownika DESC LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("ID_Uzytkownika");
            } else {
                return 0; // Jeśli nie ma żadnego rekordu w tabeli
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
