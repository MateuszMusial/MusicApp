package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

public class MediaPlayerController {

    MediaPlayer mediaplayer;
    @FXML
    public Label artistLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Label songTitleLabel;

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
    protected void onVolumeButtonClicked() {
        System.out.println("akcja volume");
    }

    protected void playSong(String fileName){
        String path = Objects.requireNonNull(getClass().getResource(fileName)).getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaplayer = new MediaPlayer(media);
        mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaplayer.play();
    }
}

