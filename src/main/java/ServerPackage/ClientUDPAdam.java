package ServerPackage;
import com.example.musicapp.HelloController;
import javafx.application.Application;

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
                Application.launch(HelloController.class);
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
