package com.example.musicapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchViewController implements Initializable {

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
}
