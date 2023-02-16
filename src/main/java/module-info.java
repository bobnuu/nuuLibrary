module com.example.last_team_projects {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.nuuLibrary to javafx.fxml;
    exports com.example.nuuLibrary;
    exports com.example.nuuLibrary.controllers;
    opens com.example.nuuLibrary.controllers to javafx.fxml;
}