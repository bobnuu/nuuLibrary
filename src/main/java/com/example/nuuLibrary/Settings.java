package com.example.nuuLibrary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {
    @FXML
    private Button button_home;
    @FXML
    private Button button_myBooks;
    @FXML
    private Button button_place_order;
    @FXML
    private Button button_settings;
    @FXML
    private Button button_logout;
    @FXML
    private Button button_edit;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainPage.fxml", "Main Page!", null);
            }
        });
        button_myBooks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"myBooks.fxml", "My Books", null);
            }
        });
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "LogIn.fxml", "Login!", null);
            }
        });
        button_myBooks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "myBooks.fxml", "My Books!", null);
            }
        });
        button_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Settings.fxml", "Settings!", null);
            }
        });
        button_place_order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "PlaceOrder.fxml", "Order!", null);
            }
        });
    }
}
