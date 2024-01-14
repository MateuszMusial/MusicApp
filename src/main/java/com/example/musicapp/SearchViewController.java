package com.example.musicapp;

import ServerPackage.GeneralMessage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

public class SearchViewController implements Initializable {
    public String songString;
    @FXML
    public Text textField;
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField textInput;

    public String[] choices = {"Artist", "Song Title", "Album"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (choiceBox != null) {
            choiceBox.setItems(FXCollections.observableArrayList(choices));
        }
    }

    public void onSearchButtonClick() throws IOException {

        String userInput = textInput.getText();
        String userChoice = choiceBox.getValue();
        // search song function

        //  zapisz piosenke na dysku jesli znaleziona
        GeneralMessage msg = new GeneralMessage("m3");
        msg.setSongMessage(userInput, userChoice);
        DatagramSocket socket = null;
        try { // IPv4 UDP
            // ustawienia UDP
            socket = new DatagramSocket();
            InetAddress servAddr = InetAddress.getByName("127.0.0.1");
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
            if(msg.m3.songFound){
                this.songString = msg.m3.songString;
                Stage currentStage = (Stage) textInput.getScene().getWindow();
                //Scene currentScene = textInput.getScene();
                //currentScene.setUserData(songString);
                currentStage.close();
                //
                FXMLLoader fxmlLoader = new FXMLLoader();
                // nowe
                fxmlLoader.setLocation(getClass().getResource("player-view.fxml"));
                Parent tableMediaPlayer = fxmlLoader.load();

                Scene newScene = new Scene(tableMediaPlayer);

                MediaPlayerController controller = fxmlLoader.getController();
                controller.songString = this.songString;

                Stage stage = new Stage();
                stage.setTitle("MusicApp");
                stage.setScene(newScene);
                stage.show();
            }
            else{
                textField.setText("Song not found!");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            socket.close();
        }

    }

    public void onBackButtonClick() throws IOException {
        Stage currentStage = (Stage) textInput.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }
}
