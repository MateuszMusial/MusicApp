package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuViewController {
    @FXML
    private Button button1;
    @FXML
    protected void onLogoutButtonClick() throws Exception {
        Stage currentStage = (Stage) button1.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onSearchButtonClick() throws Exception {
        Stage currentStage = (Stage) button1.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("search-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onPlayButtonClick() throws Exception {
        Stage currentStage = (Stage) button1.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }
}
