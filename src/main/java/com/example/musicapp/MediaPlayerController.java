package com.example.musicapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MediaPlayerController {
    public String songString, artist, title;
    public ProgressBar progressbar;
    public Button Playlist;
    private MediaPlayer mediaPlayer;
    private String fileName = "tomorrow.mp3";

    @FXML
    public Label artistLabel;
    @FXML
    public Label songTitleLabel;
    @FXML
    public Slider volumeSlider;


    public void initialize() {
        volumeSlider.setValue(0.5);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });
    }

    public void updateMediaInformation(String title, String artist) {
        artistLabel.setText(artist);
        songTitleLabel.setText(title);
    }

    @FXML
    protected void onPlayButtonClicked() {
        playSong();
        System.out.println(title);
        System.out.println(artist);
        updateMediaInformation(this.title, this.artist);
    }

    public void onStopButtonClickedButtonClicked() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Stage currentStage = (Stage) volumeSlider.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("MusicApp");
        stage.setScene(scene);
        stage.show();
    }

    protected void playSong() {
        if(songString != null) {
            //System.out.println(songString);
            Media media = new Media(this.songString);

            mediaPlayer = new MediaPlayer(media);


            // Set the volume based on the slider value
            mediaPlayer.setVolume(volumeSlider.getValue());

            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void onAddToPlaylistButtonClick() {
    }
}

