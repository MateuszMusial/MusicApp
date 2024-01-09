package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuViewController {

    @FXML
    protected void onLogoutButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onPlaylistButtonClick() throws Exception {
        // TODO: add playlist view
    }

    @FXML
    protected void onSearchButtonClick() throws Exception {
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }
}
