package com.example.nuuLibrary;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class  HelloController implements Initializable {

    @FXML
    private Button button_login;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     button_login.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
             DBUtils.loginUser(event, tf_username.getText(), tf_password.getText());
         }
     });
     button_admin.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
             DBUtils.changeScene(event, "LogInAdmin.fxml", "LogIn Admin!", null);
         }
     });
    }
}