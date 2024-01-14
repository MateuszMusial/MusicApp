package ServerPackage;
import java.io.*;
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
            // UDP wysylka obietkow
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            // UDP odbior obiektow
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packetOtrzymany.getData()));
            GeneralMessage x = (GeneralMessage) iStream.readObject();
            if(x != null && x.prio.equals("m1")){
                x.m1.login();
                try {
                    oo.writeObject(x);
                    oo.flush();
                    byte[] serializedMessage = bStream.toByteArray();
                    mySocket.send(new DatagramPacket(serializedMessage, serializedMessage.length,
                            packetOtrzymany.getAddress(), packetOtrzymany.getPort()));
                    System.out.println("odeslalem wiadomosc o zalogowaniu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(x != null && x.prio.equals("m2")){
                x.m2.checkIfRegistered();
                if(x.m2.isRegistered)
                    x.m2.register();
                try {
                    oo.writeObject(x);
                    oo.flush();
                    byte[] serializedMessage = bStream.toByteArray();
                    mySocket.send(new DatagramPacket(serializedMessage, serializedMessage.length,
                            packetOtrzymany.getAddress(), packetOtrzymany.getPort()));
                    System.out.println("odeslalem wiadomosc o zalogowaniu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(x != null && x.prio.equals("m3")){
                x.m3.searchSong();
                try {
                    oo.writeObject(x);
                    oo.flush();
                    byte[] serializedMessage = bStream.toByteArray();
                    mySocket.send(new DatagramPacket(serializedMessage, serializedMessage.length,
                            packetOtrzymany.getAddress(), packetOtrzymany.getPort()));
                    System.out.println("odeslalem wiadomosc o zalogowaniu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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


