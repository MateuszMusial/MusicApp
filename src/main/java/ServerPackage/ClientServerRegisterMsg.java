package ServerPackage;

import java.io.*;
import java.sql.*;

public class ClientServerRegisterMsg implements Serializable {
    public String username, login, password, email;
    public boolean isRegistered = false;
    public ClientServerRegisterMsg(String us, String lg, String psswd, String em){
        username = us;
        login = lg;
        password = psswd;
        email = em;
    }
    public ClientServerRegisterMsg(){
        username = "";
        login = "";
        password = "";
        email = "";
    }
    public void register(){
        Connection con = null;
        String SQL = "insert into users values( " +
                "\"" + this.username + "\"," + "\"" + this.login + "\"," +
                "\"" + this.password + "\"," + "\"" + this.email + "\")";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            stat.executeUpdate(SQL);
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

    public void checkIfRegistered(){
        Connection con = null;
        String SQL = "select username, login from users where username = " + "\"" + this.username  + "\"" +
                " and login = "  + "\"" + this.login  + "\"" ;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(SQL);
            if (!res.next()) {
                // do nothing
                this.isRegistered = false;
            }
            else{
                this.isRegistered = true;
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
