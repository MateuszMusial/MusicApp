package ServerPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
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
        InputStream inputStream = clientSocket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String receivedMessage = new String(buffer, 0, bytesRead);
        System.out.println("Otrzymano od klienta: " + receivedMessage);

        // Tutaj możesz dodać logikę przetwarzania otrzymanych danych

        // Przykładowa obsługa bazy danych
        // handleDatabase(connection);
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
