package com.example.musicapp;
import java.io.*;
import java.net.Socket;

public class Klient {

    private static Socket socket;
    static InputStream inputStream;


    int Port = 12345;
    String serverAddress = "localhost";
    Klient() throws IOException {
        socket = new Socket(serverAddress, Port);
        inputStream = socket.getInputStream();
    }

   /* public static void main(String[] args) {
        int Port = 12345;
        String serverAddress = "localhost";
        try {
            socket = new Socket(serverAddress, Port);
            System.out.println("Klient Pawel został podłączony do serwera na porcie " + Port + ".");
            receiveWelcomeMessage();
            sendToServerLogin("Admin", "Admin");
            //sendRegistrationToServer();
            closeConnection();
        } catch (IOException e) {
            System.out.println("Port " + Port + " jest zajęty.");
            //e.printStackTrace();
        }
    }

    */
    private static void closeConnection() throws IOException {
        socket.shutdownOutput();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socket.close();
        System.out.println("Klient Pawel został odłączony od serwera.");
    }


    public static String receiveWelcomeMessage() throws IOException {
        inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String receivedMessage = new String(buffer, 0, bytesRead);
        System.out.println("Otrzymano od serwera: " + receivedMessage);
        return receivedMessage;
    }
    public static String receiveLoginAnswer() throws IOException {

        inputStream = socket.getInputStream();

        byte[] buffer = new byte[1024];

        int bytesRead = inputStream.read(buffer);
        System.out.println(bytesRead);
        return new String(buffer, 0, bytesRead);
    }

    public static void sendToServerLogin(String login, String password) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(login.getBytes());
        outputStream.flush();

        outputStream.write(password.getBytes());
        outputStream.flush();
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
