package ServerPackage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class ClientUDPAdam {
    public static void main(String[] args){
        DatagramSocket socket = null;
        try{ // IPv4 in args
            // 1. wysylanie pakietu..
            socket = new DatagramSocket();
            InetAddress servAddr = InetAddress.getByName(args[0]);
            byte buf[] = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            GeneralMessage g0 = new GeneralMessage("my prio is to be good UDP Client :)");
            GeneralMessage g1 = new GeneralMessage("m1");
            GeneralMessage g2 = new GeneralMessage("m2");

            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            while(true){
                // 1 wiadomosc, by server uzyskal dane klienta do odsylki
                oo.writeObject(g0);
                oo.flush();
                byte[] serializedMessage = bStream.toByteArray();
                socket.send(new DatagramPacket(serializedMessage, serializedMessage.length, servAddr, 4321));
                // odbior powitania od servera
                socket.receive(packet); // "a tu serwer wita"
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
                // UDP przesyl obiektow
                oo.writeObject(g2);
                oo.flush();
                byte[] serializedMessage2 = bStream.toByteArray();
                socket.send(new DatagramPacket(serializedMessage2, serializedMessage2.length, servAddr, 4321));
                oo.close();
                break;
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            socket.close();
        }
    }
}
