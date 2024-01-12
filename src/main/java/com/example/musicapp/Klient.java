package com.example.musicapp;
import java.io.*;
import java.net.Socket;

public class Klient {
    private static Socket socket;
    static InputStream inputStream;
    static OutputStream outputStream;
    int Port = 12345;
    String serverAddress = "localhost";
    Klient() throws IOException {
        socket = new Socket(serverAddress, Port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        receiveMessage();
    }
   private static void closeConnection() throws IOException {
       try (Socket socket = Klient.socket) {
           socket.shutdownOutput();
           Thread.sleep(10000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println("Klient Pawel został odłączony od serwera.");
   }
    public static String receiveMessage() throws IOException {
        inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        if (bytesRead > 0) {
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Otrzymano od serwera: " + receivedMessage);
            return receivedMessage;
        } else {
            return "";
        }
    }

    public static String receiveLoginAnswer() throws IOException {
        if (socket.isClosed()) {
            System.out.println("Socket is closed");
        }
        else {
            System.out.println("Socket is not closed");
        }
        InputStream a = socket.getInputStream();
        byte[] buffer = new byte[1024];

        int bytesRead = a.read(buffer);
        if (bytesRead > 0) {
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Otrzymano od serwera: " + receivedMessage);
            return receivedMessage;
        } else {
            return "";
        }
   }



    public static void sendToServerLogin(String login, String password) throws IOException {
        outputStream = socket.getOutputStream();

        outputStream.write(login.getBytes());
        outputStream.flush();

        outputStream.write(password.getBytes());
        outputStream.flush();
        System.out.println("Wysłano login i hasło do serwera.");
    }

    private static void sendRegistrationToServer(String firstName, String lastName, String login, String password) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            // Wyślij imię
            dataOutputStream.writeUTF(firstName);
            dataOutputStream.flush();

            // Wyślij nazwisko
            dataOutputStream.writeUTF(lastName);
            dataOutputStream.flush();

            // Wyślij login
            dataOutputStream.writeUTF(login);
            dataOutputStream.flush();

            // Wyślij hasło
            dataOutputStream.writeUTF(password);
            dataOutputStream.flush();
        }
    }


}
