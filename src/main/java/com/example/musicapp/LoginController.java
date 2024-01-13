package com.example.musicapp;

import ServerPackage.GeneralMessage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LoginController {
    //Klient klient = new Klient();
    @FXML
    public Label infoLabel;
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField passwordField;

    public LoginController() throws IOException {
    }

    @FXML
    void onLoginButtonClicked() throws IOException, InterruptedException {
        String isCredentialCorrect = "false";
        String inputLogin = loginTextField.getText();
        String inputPassword = passwordField.getText();

        GeneralMessage msg = new GeneralMessage("m1");
        msg.setLoginMessage(inputLogin, inputPassword);
        GeneralMessage msg2;
        DatagramSocket socket = null;
        try{ // IPv4 UDP
            // ustawienia UDP
            socket = new DatagramSocket();
            InetAddress servAddr = InetAddress.getByName("127.0.0.1");
            byte buf[] = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            // UDP wysylka obiektow
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            // 1. wysylanie pakietu z zapytaniem o logowanie
            oo.writeObject(msg);
            oo.flush();
            byte[] serializedMessage = bStream.toByteArray();
            socket.send(new DatagramPacket(serializedMessage, serializedMessage.length, servAddr, 4321));
            // odbior odpowiedzi od servera
            // UDP odbior obiektow
            socket.receive(packet);
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            msg = (GeneralMessage) iStream.readObject(); // "a tu serwer z powrotem wita"
            oo.close();
            if(msg.m1.success){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("menu-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("MusicApp");
                stage.setScene(scene);
                stage.show();

                Stage currentStage = (Stage) loginTextField.getScene().getWindow();
                currentStage.close();
            }
            else{
                infoLabel.setText("Wrong Login or Password!");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            socket.close();
        }
    }
}
