package ServerPackage;

import java.io.Serializable;

public class ClientServerLoginMsg implements Serializable{
    public String login, password;
    public boolean success = false;
    public ClientServerLoginMsg(String lg, String psswd){
        login = lg;
        password = psswd;
    }
}
