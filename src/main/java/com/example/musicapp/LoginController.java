package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    Klient klient = new Klient();
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

        // tutaj wywolujemy funkcje ktora wysyla dane do sprawdzenia i zwraca tru/false
        System.out.println(inputLogin + " " + inputPassword);
        klient.sendToServerLogin(inputLogin, inputPassword);
        isCredentialCorrect = klient.receiveWelcomeMessage();
        System.out.println(isCredentialCorrect);

        if(isCredentialCorrect.equals("true")){
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
}
