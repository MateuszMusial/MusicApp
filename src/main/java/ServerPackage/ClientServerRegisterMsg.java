package ServerPackage;

import java.io.*;

public class ClientServerRegisterMsg implements Serializable {
    public String username, login, password, email, address;
    int age;
    public ClientServerRegisterMsg(String us, String lg, String psswd, String em, String addr, int a){
        username = us;
        login = lg;
        password = psswd;
        email = em;
        address = addr;
        age = a;
    }
}
