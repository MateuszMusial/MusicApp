package ServerPackage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
// IPv4 mojego kompa
public class ServerUDPAdam {
    public static void main(String[] args){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(4321);
            int activeClients = 0;
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
