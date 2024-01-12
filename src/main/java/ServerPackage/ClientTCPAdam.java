package ServerPackage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCPAdam {
    public static void main(String[] args) {
        Socket socket = null;
        try{ // IPv4 in args
            int liczbaPytan = 4;
            int liczbaLinijekPytania = 5;
            socket = new Socket(InetAddress.getByName(args[0]), 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner odp = new Scanner(System.in);
            while(true){
                System.out.println(in.readLine());
                String nazwaStudenta = odp.nextLine();
                out.println(nazwaStudenta);
                for(int i = 0; i < liczbaPytan; i++) {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(in.readLine());
                    }
                    out.println(odp.nextLine());
                }
                System.out.println(in.readLine());
                break;
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

