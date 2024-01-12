package ServerPackage;

import java.io.File;
import java.io.Serializable;
import javax.sound.sampled.*;
public class ClientServerSongMsg implements Serializable {
    public String title, album, artist;
    public String link = null;
    public File songFile = null;
    public int idsong = Integer.parseInt(null);
    public ClientServerSongMsg(String tit, String alb, String art){
        title = tit;
        album = alb;
        artist = art;
    }
    public void setLink(String lnk){
        link = lnk;
    }
    public String getLink(){
        return link;
    }
    public void setFile(){
        songFile = new File(link).getAbsoluteFile();
    }
    public File getFile(){
        return songFile;
    }
}
