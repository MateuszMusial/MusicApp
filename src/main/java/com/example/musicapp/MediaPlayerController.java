package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MediaPlayerController {

    MediaPlayer mediaplayer;
    @FXML
    public Label artistLabel;
    @FXML
    public Label totalTimeLabel;
    @FXML
    public Label songTitleLabel;

    private String fileName = "tomorrow.mp3";


    public void updateMediaInformation(String title, String artist, String totalTime) {

        artistLabel.setText(artist);
        totalTimeLabel.setText(totalTime);
        songTitleLabel.setText(title);

    }

    @FXML
    protected void onPlayButtonClicked() {
        playSong(fileName);
    }


    @FXML
    protected void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }

    protected void playSong(String fileName){
        String path = Objects.requireNonNull(getClass().getResource(fileName)).getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaplayer = new MediaPlayer(media);
        mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaplayer.play();
    }
}