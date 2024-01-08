package ServerPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInterface {
    private static final String URL = "jdbc:postgresql://localhost:5432/music_app";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private Connection connection;

    public DBInterface() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Błąd inicjalizacji połączenia z bazą danych: " + e.getMessage());
        }
    }

    public boolean checkUserCredentials(String login, String password) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM \"Users\" WHERE \"Login\" = ? AND \"Password\" = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Zwróć true, jeśli użytkownik istnieje w bazie danych

        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
            return false;

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("Błąd podczas zamykania PreparedStatement: " + e.getMessage());
            }
        }
    }

    public String findSongByTitle(String title) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT \"Path\" FROM \"Songs\" WHERE \"Title\" = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Path");
            } else {
                return null; // Jeśli utwór o podanym tytule nie został znaleziony
            }

        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
            return null;

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("Błąd podczas zamykania PreparedStatement: " + e.getMessage());
            }
        }
    }
    public void fetchUsersFromDatabase() {
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM \"Users\"";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getLong("ID") +
                        ", FirstName: " + resultSet.getString("FirstName") +
                        ", LastName: " + resultSet.getString("LastName") +
                        ", Login: " + resultSet.getString("Login") +
                        ", Password: " + resultSet.getString("Password") +
                        ", Age: " + resultSet.getInt("Age") +
                        ", Country: " + resultSet.getString("Country"));
            }

        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());

        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("Błąd podczas zamykania PreparedStatement: " + e.getMessage());
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("\nPołączenie z bazą danych zostało zamknięte.\n");
            }
        } catch (SQLException e) {
            System.err.println("\nBłąd podczas zamykania połączenia: " + e.getMessage());
        }
    }
}