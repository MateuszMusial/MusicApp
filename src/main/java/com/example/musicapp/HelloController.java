package com.example.musicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    protected void onLoginButtonClick() {
        System.out.println("akcja login");
    }

    @FXML
    protected void onRegisterButtonClick() {
        System.out.println("akcja register");
    }
}