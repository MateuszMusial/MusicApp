package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.Callable;

public class ServerPawel implements Callable<Void> {

    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        ServerPawel serverPawel = new ServerPawel();
        try {
            serverPawel.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() throws Exception {
        ServerSocket serverSocket = null;

        try {
            System.out.println("Serwer Pawel został uruchomiony.");
            serverSocket = new ServerSocket(SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klient Pawel został podłączony do serwera.");
                handleClient(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSocket(serverSocket);
        }

        return null;
    }

    private void handleClient(Socket clientSocket) {
        try {
            sendWelcomeMessage(clientSocket);
            receiveAndProcessData(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeClientSocket(clientSocket);
        }
    }

    private void sendWelcomeMessage(Socket clientSocket) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        String message = "Witaj, to jest serwer!";
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void receiveAndProcessData(Socket clientSocket) throws IOException {
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

            // Tutaj możesz dodać logikę przetwarzania otrzymanych danych

            // Przykładowa obsługa bazy danych PostgreSQL
            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/twojastacjabazodanowa", "login", "haslo")) {
                String query = "SELECT * FROM uzytkownicy WHERE login = ? AND haslo = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, login);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Zalogowano pomyślnie.");
                            // Tutaj możesz dodać dodatkową logikę w przypadku udanego logowania
                        } else {
                            System.out.println("Błędny login lub hasło.");
                            // Tutaj możesz dodać dodatkową logikę w przypadku nieudanego logowania
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    // Przykładowa metoda obsługi bazy danych
    private void handleDatabase(Connection connection) {
        DBInterface dbInterface = new DBInterface(connection);
        boolean userExists = dbInterface.checkUserCredentials("admin", "admin");
        System.out.println("Czy użytkownik istnieje w bazie danych? " + userExists);

    }



    private void closeClientSocket(Socket clientSocket) {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeServerSocket(ServerSocket serverSocket) {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}