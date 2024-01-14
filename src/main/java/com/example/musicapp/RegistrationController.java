package com.example.musicapp;

import ServerPackage.GeneralMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RegistrationController {

    @FXML
    public TextField usernameField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    public Label infoLabel;

    @FXML
    void onRegisterButtonClicked() throws IOException {
        boolean isUserRegistered = false;

        String inputUsername = usernameField.getText();
        String inputLogin = loginField.getText();
        String inputPassword = passwordField.getText();
        String inputEmail = emailField.getText();

        GeneralMessage msg = new GeneralMessage("m2");
        msg.setRegisterMessage(inputUsername,inputLogin,inputPassword,inputEmail);
        DatagramSocket socket = null;
        try { // IPv4 UDP
            // ustawienia UDP
            socket = new DatagramSocket();
            InetAddress servAddr = InetAddress.getByName("10.233.146.115");
            byte buf[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            // UDP wysylka obiektow
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            // 1. wysylanie pakietu z zapytaniem o rejestracje
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
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            socket.close();
        }
        if(msg.m2.isRegistered){
            Stage currentStage = (Stage) loginField.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("MusicApp");
            stage.setScene(scene);
            stage.show();
        }
        else{
            infoLabel.setText("Login or Username exist!");
        }
    }
}