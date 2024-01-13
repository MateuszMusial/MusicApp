package ServerPackage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class ServerThreadUPDAdam extends Thread {
    DatagramSocket mySocket;
    DatagramPacket packetOtrzymany;
    int activeClients;

    public ServerThreadUPDAdam(DatagramSocket socket, DatagramPacket p, int actCli) {
        super(); // konstruktor Thread
        mySocket = socket;
        packetOtrzymany = p;
        activeClients = actCli;
    }
    public void run() {
        // program watku, wstepnie napisz co ma sie robic po akceptacji gniazda
        try {
            // UDP odbior obiektow
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packetOtrzymany.getData()));
            GeneralMessage x = (GeneralMessage) iStream.readObject();
            if(x != null && x.prio.equals("my prio is to be good UDP Client :)")){
                final GeneralMessage obiektKlasyX = (GeneralMessage) x;
                System.out.println(obiektKlasyX.prio);
            }
            else{
                String received = new String(packetOtrzymany.getData(), 0, packetOtrzymany.getLength());
                System.out.println(received);
                try {
                    String powitanie = "a tu serwer wita";
                    mySocket.send(new DatagramPacket(powitanie.getBytes(), powitanie.length(),packetOtrzymany.getAddress(), packetOtrzymany.getPort()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}


