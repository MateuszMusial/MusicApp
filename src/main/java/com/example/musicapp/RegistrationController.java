package com.example.musicapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public Text usernameField;
    @FXML
    public Text loginField;
    @FXML
    public Text passwordField;
    @FXML
    public Text emailField;
    @FXML
    public Label infoLabel;

    @FXML
    void onRegisterButtonClicked() throws IOException {
        boolean isUserRegistered = false;

        String inputUsername = usernameField.getText();
        String inputLogin = loginField.getText();
        String inputPassword = passwordField.getText();
        String inputEmail = emailField.getText();

        // tutaj wywolujemy funkcje ktora wysyla dane do sprawdzenia i zwraca tru/false

        // isCredentialCorrect = funkcja2(str inputlogin, str inputpassword
        //                                str email,      str username);

        if(isUserRegistered){
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
            infoLabel.setText("Please try again!");
        }
    }
}