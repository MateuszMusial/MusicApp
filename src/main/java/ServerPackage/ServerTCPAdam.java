package ServerPackage;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCPAdam {
    public static void main(String[] args){
        // ServerSocket w try catch jest wygodny - dzieki niemu nie musimy recznie wywolywac metody close();
        ServerSocket serverSocket = null;
        // egzamin
        //Egzamin egzamin = new Egzamin();
        try{
            serverSocket = new ServerSocket(1234);
            int activeClients = 0;
            while(true){
                // czekamy na zgloszenie klienta..
                // po zgloszeniu klienta nalezy go obsluzyc - np w osobnym watku - klasa ServerTCPThread
                Socket s = serverSocket.accept();
                if(activeClients < 250) { // blok na 250 uczniow naraz
                    System.out.println("New client connected");
                    activeClients++;
                    // rozmowa klient-server
                    ServerThreadTCPAdam t = new ServerThreadTCPAdam(s, activeClients);
                    t.start();
                }
                else
                    System.out.println("Connection failure - too many clients");
            }
        }
        // zamkniecie strumieni i polaczenia
        catch(Exception e){
            System.err.println(e);
        }
    }
}
