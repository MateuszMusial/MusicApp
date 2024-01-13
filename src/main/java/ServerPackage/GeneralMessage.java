package ServerPackage;

import java.io.Serializable;

public class GeneralMessage implements Serializable {
    public ClientServerLoginMsg m1;
    public ClientServerRegisterMsg m2;
    public ClientServerSongMsg m3;
    String prio = null;
    public GeneralMessage(String prio){
        this.prio = prio;
        this.m1 = new ClientServerLoginMsg();
    }
    public void setLoginMessage(String login, String password){
        m1.login = login;
        m1.password = password;
        //m1.success = success;
    }
    public void setRegisterMessage(String username, String login, String password, String email){
        m2.username = username;
        m2.login = login;
        m2.password = password;
        m2.email = email;
    }
    // do zrobienia gdy zdecydujemy File vs Media
    public void setSongMessage(String login, String password){
        // zmiany w m3..
    }
}
