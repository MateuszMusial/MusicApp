package ServerPackage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientServerRegisterMsg implements Serializable {
    public String username, login, password, email;
    public ClientServerRegisterMsg(String us, String lg, String psswd, String em){
        username = us;
        login = lg;
        password = psswd;
        email = em;
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
}
