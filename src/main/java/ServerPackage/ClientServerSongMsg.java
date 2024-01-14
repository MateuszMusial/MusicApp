package ServerPackage;

import javafx.scene.media.Media;

import java.io.File;
import java.io.Serializable;
import java.sql.*;
public class ClientServerSongMsg implements Serializable {
    public String title, album, artist, searchBy;
    public String link;
    public String songString;
    public boolean songFound = false;
    public ClientServerSongMsg(String tit, String alb, String art, String sb, String lnk){
        title = tit;
        album = alb;
        artist = art;
        searchBy = sb;
        link = lnk;
    }
    public ClientServerSongMsg(){
        title = "";
        album = "";
        artist = "";
        link = "";
        searchBy = "";
    }

    public void searchSong(){
        Connection con = null;
        String SQL;
        /* szuka po wszystkim
        String SQL = "select top 1 idsong, link from songs" +
                " where title =  \"" + this.title + "\" and album = " +
                this.album + "\" and artist = " +
                this.artist; */
        if(searchBy.equals("Artist")) {
            SQL = "select link from songs" +
                    " where artist =  \"" + this.artist + "\"";
        }
        else if(searchBy.equals("Album")){
            SQL = "select link from songs" +
                    " where  album =  \"" + this.album + "\"";
        }
        else{
            SQL = "select link from songs" +
                    " where  title =  \"" + this.title + "\"";
        }
        try {
            System.out.println(SQL);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(SQL);
            if (!res.next()) {
                // do nothing
                this.songFound = false;
            }
            else{
                System.out.println("ssss");
                this.songFound = true;
                this.link = String.valueOf(res.getString("link"));
                this.songString = new File(this.link).toURI().toString();
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
