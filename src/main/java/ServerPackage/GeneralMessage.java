package ServerPackage;

import javafx.scene.media.Media;

import java.io.Serializable;

public class GeneralMessage implements Serializable {
    public ClientServerLoginMsg m1;
    public ClientServerRegisterMsg m2;
    public ClientServerSongMsg m3;
    String prio = null;
    public GeneralMessage(String prio){
        this.prio = prio;
        this.m1 = new ClientServerLoginMsg();
        this.m2 = new ClientServerRegisterMsg();
        this.m3 = new ClientServerSongMsg();
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
    public void setSongMessageX(String artist, String album, String title, String searchBy, Media songMedia){
        // zmiany w m3..
        m3.artist = artist;
        m3.album = album;
        m3.title = title;
        m3.searchBy = searchBy;
        //m3.songMedia = songMedia;
    }
    public void setSongMessage(String input, String choice){
        if(choice.equals("Artist"))
            m3.artist = input;
        else if(choice.equals("Album"))
            m3.album = input;
        else
            m3.title = input;
        m3.searchBy = choice;
    }
}
