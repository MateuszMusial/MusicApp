package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    Klient klient = new Klient();


    @FXML
    public Label infoLabel;
    @FXML
    public Text loginTextField;
    @FXML
    public Text passwordField;

    @FXML
    void onLoginButtonClicked() throws IOException {
        boolean isCredentialCorrect = false;

        String inputLogin = loginTextField.getText();
        String inputPassword = passwordField.getText();

        // tutaj wywolujemy funkcje ktora wysyla dane do sprawdzenia i zwraca tru/false

        //Klient.sendToServer(inputLogin, inputPassword);
        // isCredentialCorrect = funkcja1(str inputlogin, str inputpassword);

        if(isCredentialCorrect){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("MusicApp");
            stage.setScene(scene);
            stage.show();
        }
        else{
            infoLabel.setText("Wrong Login or Password!");
        }
    }
}
