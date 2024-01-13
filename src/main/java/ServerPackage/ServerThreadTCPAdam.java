package ServerPackage;
import java.io.*;
import java.net.Socket;


public class ServerThreadTCPAdam extends Thread {
    Socket mySocket;
    int activeClients;

    public ServerThreadTCPAdam(Socket socket, int actCli) {
        super(); // konstruktor Thread
        mySocket = socket;
        activeClients = actCli;
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

