package com.example.musicapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class PlaylistViewController {
    @FXML
    public ListView <String> songList;
    @FXML
    public Button startButton;

    String[] songs = {"Song1 - Artist1", "Song2 - Artist2", "Song2 - Artist2"};


    public void initialize() {
        songList.getItems().addAll(songs);
    }

    public void onStartButtonClicked() {
    }
}
