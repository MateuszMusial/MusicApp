package ServerPackage;

import java.io.Serializable;
import java.sql.*;

public class ClientServerLoginMsg implements Serializable{
    public String login;
    public String password;
    public boolean success = false;
    public ClientServerLoginMsg(String lg, String psswd){
        login = lg;
        password = psswd;
    }
    public ClientServerLoginMsg(){
        login = "";
        password = "";
    }
    public void login(){
        Connection con = null;
        String SQL = "select login, password from users where login = " + "\"" + this.login  + "\"" +
                " and password = "  + "\"" + this.password  + "\"" ;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/musicappdb",
                    "root", "root");
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(SQL);
            if (!res.next()) {
                // do nothing
                this.success = false;
            }
            else{
                this.login = String.valueOf(res.getString("login"));
                this.password = String.valueOf(res.getString("password"));
                this.success = true;
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
