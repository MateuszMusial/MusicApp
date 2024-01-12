package ServerPackage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class ServerThreadTCPAdam extends Thread {
    Socket mySocket;
    int activeClients;

    public ServerThreadTCPAdam(Socket socket, int actCli) {
        super(); // konstruktor Thread
        mySocket = socket;
        activeClients = actCli;
    }
    // funkcja do rejestracji nowego klienta (= wprowadzenie jego danych do BD)
    public void register(ClientServerRegisterMsg msg){
        Connection con = null;
        String SQL = "insert into users values( " +
                "\"" + msg.username + "\"," + "\"" + msg.login + "\"," +
                "\"" + msg.password + "\"," + "\"" + msg.email + "\"," + "\"" + "\"" + msg.address + "\"," + "\"" +
                msg.age + "\")";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
        } catch (Exception excpt) {
            excpt.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void login(ClientServerLoginMsg msg){
        Connection con = null;
        String SQL = "SELECT STATEMENT";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
        } catch (Exception excpt) {
            excpt.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void searchSong(ClientServerSongMsg msg){
        Connection con = null;
        String SQL = "select top 1 idsong, link from songs" +
                " where title =  \"" + msg.title + "\" and album = " +
                msg.album + "\" and artist = " +
                msg.artist;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(SQL);
            if (!res.next()) {
                // do nothing
                msg.link = "SONG NOT FOUND!";
                msg.idsong = Integer.parseInt(String.valueOf(-1));
            }
            else{
                msg.link = String.valueOf(res.getDate("link"));
                msg.idsong = Integer.parseInt(res.getString("idsong"));
                msg.getFile();
            }
        } catch (Exception excpt) {
            excpt.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void playSong(){

    }
    public void run() {
        // program watku, wstepnie napisz co ma sie robic po akceptacji gniazda
        try {
            // przygotowanie do odczytu/zapisu w plikach // zamkniecie dopiero po wykonaniu calosci
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()),true);
            // wysylka obiektow do klienta
            OutputStream os = mySocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            // odbior obiektow od klienta
            InputStream is = mySocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            //poczatek polaczenia
            while(true) {
                Object objectReceived = ois.readObject();
                // rejestracja nowego klienta
                if(objectReceived instanceof ClientServerRegisterMsg){
                    final ClientServerRegisterMsg msg = (ClientServerRegisterMsg) objectReceived;
                    // rejestracja w BD
                    register(msg);
                    // odsyla dane rejestracyjne jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                    break;
                }
                // logowanie klienta, boolean jako sukces operacji (w klasie)
                if(objectReceived instanceof ClientServerLoginMsg){
                    final ClientServerLoginMsg msg = (ClientServerLoginMsg) objectReceived;
                    // rejestracja w BD
                    login(msg);
                    // odsyla dane logowania jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                }
                // odbior zapytania o piosenke klienta i odeslanie piosenki
                if(objectReceived instanceof ClientServerSongMsg){
                    final ClientServerSongMsg msg = (ClientServerSongMsg) objectReceived;
                    // zapytanie w BD
                    searchSong(msg);
                    // odsyla dane logowania jako potwierdzenie sukcesu
                    oos.writeObject(msg);
                    oos.flush();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                activeClients--;
                mySocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}

