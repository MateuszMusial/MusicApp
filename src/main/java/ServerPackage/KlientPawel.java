package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class KlientPawel {

    public static void main(String[] args) {
        try {
            // Adres i port serwera
            String serverAddress = "localhost";
            int serverPort = 12345;

            // Nawiązanie połączenia z serwerem
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Klient Pawel został podłączony do serwera.");

            // Odbieranie powitalnej wiadomości od serwera
            receiveWelcomeMessage(socket);

            // Tutaj możesz dodać logikę wysyłania danych do serwera
            sendToServer(socket);

            // Zamykamy połączenie z serwerem
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveWelcomeMessage(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String receivedMessage = new String(buffer, 0, bytesRead);
        System.out.println("Otrzymano od serwera: " + receivedMessage);
    }

    private static void sendToServer(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        String message = "Witaj,";
        outputStream.write(message.getBytes());
        outputStream.flush();

        String message1 = "to jest klient!";
        outputStream.write(message1.getBytes());
        outputStream.flush();
    }

}
