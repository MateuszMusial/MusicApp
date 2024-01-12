package com.example.musicapp;
import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MusicApp?useSSL=false&serverTimezone=UTC";

    public static boolean loginUser(String login, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "password")) {
            String query = "SELECT * FROM uzytkownik WHERE login = ? AND haslo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void registerUser(String firstName, String lastName, String login, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, "root", "password")) {
            String query = "INSERT INTO uzytkownik (ID_Uzytkownika, imie, nazwisko, login, haslo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
}
