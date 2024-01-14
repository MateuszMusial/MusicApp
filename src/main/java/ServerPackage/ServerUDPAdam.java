package ServerPackage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// IPv4 mojego kompa
public class ServerUDPAdam {
    private static final int PORT = 4321;
    public static void main(String[] args){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(PORT);
            int activeClients = 0;
            System.out.println(PORT);
            System.out.println("IP serwer: " + InetAddress.getLocalHost().getHostAddress());
            while(true){
                byte[] receiveData = new byte[1024];
                DatagramPacket packetOtrzymany = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(packetOtrzymany);
                System.out.println("New client connected");
                activeClients++;
                // rozmowa klient-server
                ServerThreadUPDAdam t = new ServerThreadUPDAdam(socket, packetOtrzymany, activeClients);
                t.start();
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
        finally {
            socket.close();

        }
    }
}
