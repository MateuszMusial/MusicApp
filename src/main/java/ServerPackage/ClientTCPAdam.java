package ServerPackage;

import com.example.musicapp.HelloController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Application;


public class ClientTCPAdam {
    public static void main(String[] args) {
        Socket socket = null;
        try{ // IPv4 in args
            socket = new Socket(InetAddress.getByName(args[0]), 1234);
            //aplikacja START
            Application.launch(HelloController.class);
            // przygotowanie do odczytu/zapisu w plikach // zamkniecie dopiero po wykonaniu calosci
            // wysylka obiektow do klienta
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            // odbior obiektow od klienta
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            while(true){
                Object objectReceived = ois.readObject();
                // rejestracja nowego klienta
                if(objectReceived instanceof ClientServerRegisterMsg){
                    final ClientServerRegisterMsg msg = (ClientServerRegisterMsg) objectReceived;
                    // rejestracja w BD
                    msg.register();
                    // odsyla dane rejestracyjne jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                    break;
                }
                // logowanie klienta, boolean jako sukces operacji (w klasie)
                if(objectReceived instanceof ClientServerLoginMsg){
                    final ClientServerLoginMsg msg = (ClientServerLoginMsg) objectReceived;
                    // rejestracja w BD
                    msg.login();
                    // odsyla dane logowania jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                }
                // odbior zapytania o piosenke klienta i odeslanie piosenki
                if(objectReceived instanceof ClientServerSongMsg){
                    final ClientServerSongMsg msg = (ClientServerSongMsg) objectReceived;
                    // zapytanie w BD
                    msg.searchSong();
                    // odsyla dane logowania jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                }
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}

