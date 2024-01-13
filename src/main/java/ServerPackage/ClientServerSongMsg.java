package ServerPackage;

import javafx.scene.media.Media;

import java.io.File;
import java.io.Serializable;
import java.sql.*;
import javax.sound.sampled.*;
public class ClientServerSongMsg implements Serializable {
    public String title, album, artist;
    public String link = null;
    public File songFile = null;
    public Media songMedia = null;
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
    public void searchSong(){
        Connection con = null;
        String SQL = "select top 1 idsong, link from songs" +
                " where title =  \"" + this.title + "\" and album = " +
                this.album + "\" and artist = " +
                this.artist;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(SQL);
            if (!res.next()) {
                // do nothing
                this.link = "SONG NOT FOUND!";
                this.idsong = Integer.parseInt(String.valueOf(-1));
            }
            else{
                this.link = String.valueOf(res.getString("link"));
                this.idsong = Integer.parseInt(res.getString("idsong"));
                this.getFile();
            }
        } catch (Exception excpt) {
            excpt.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
