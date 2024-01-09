package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerPawel implements Callable<Void> {

    // private static final String DATABASE_URL = "jdbc:sqlite:test.db";

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
            // Rejestrowanie sterownika JDBC
            // Class.forName("org.sqlite.JDBC");

            // Nawiązanie połączenia z bazą danych
            // try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            System.out.println("Serwer Pawel został uruchomiony.");
            serverSocket = new ServerSocket(12345);

            while (true) {
                // Akceptuj połączenie od klienta
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klient Pawel został podłączony do serwera.");

                // Obsługa klienta
                handleClient(clientSocket/*, connection*/);
            }
            // }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }

        return null;
    }

    private void handleClient(Socket clientSocket/*, Connection connection*/) {
        try {
            // Przykładowe przesyłanie danych do klienta
            OutputStream outputStream = clientSocket.getOutputStream();
            String message = "Witaj, to jest serwer!";
            outputStream.write(message.getBytes());
            outputStream.flush();

            // Tutaj możesz dodać logikę odbierania danych od klienta
            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Otrzymano od klienta: " + receivedMessage);

            // Obsługa bazy danych
            // Przykład wykonania zapytania SELECT
            // String selectQuery = "SELECT * FROM tabela";
            // try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            //      ResultSet resultSet = preparedStatement.executeQuery()) {
            //     while (resultSet.next()) {
            //         // Przetwarzanie wyników zapytania
            //         String column1 = resultSet.getString("kolumna1");
            //         int column2 = resultSet.getInt("kolumna2");
            //         System.out.println("Wynik z bazy danych: " + column1 + ", " + column2);
            //     }
            // }

        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (Exception e) {
            e.printStackTrace();
        }*/ finally {
            try {
                // Zamykamy połączenie z klientem
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
