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

            // Przykładowe odbieranie danych od serwera
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Otrzymano od serwera: " + receivedMessage);

            // Tutaj możesz dodać logikę wysyłania danych do serwera
            OutputStream outputStream = socket.getOutputStream();
            String message = "Witaj, to jest klient!";
            outputStream.write(message.getBytes());


            // Zamykamy połączenie z serwerem
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
