package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerPawel implements Callable<Void> {

    public static void main(String[] args) {
        ServerPawel serverPawel = new ServerPawel();
        try {
            serverPawel.call(); // Wywołanie metody call w ramach interfejsu Callable
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() throws Exception {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(12345); // Port, na którym serwer będzie nasłuchiwał
            System.out.println("Serwer Pawel został uruchomiony.");

            while (true) {
                // Akceptuj połączenie od klienta
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klient Pawel został podłączony do serwera.");

                // Obsługa klienta w oddzielnym wątku lub jako osobny Callable
                // Przekazanie clientSocket do wątku obsługującego klienta
                // np. new Thread(new ClientHandler(clientSocket)).start();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }

        return null;
    }

    private void handleClient(Socket clientSocket) {
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Zamykamy połączenie z klientem
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
