package com.example.nuuLibrary.controllers;

import com.example.nuuLibrary.BookModel;
import com.example.nuuLibrary.DBUtils;
import com.example.nuuLibrary.db.BookManager;
import com.example.nuuLibrary.db.models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserMainPageController implements  Initializable{
    @FXML
    private Button button_home, button_myBooks, button_place_order, button_settings, button_logout;
    @FXML
    Label label_welcome;

    @FXML
    private TableView<BookModel> bookTableData;

    @FXML
    private TableColumn<BookModel, Integer> bookId;
    @FXML
    private TableColumn<BookModel, String> bookTitle;
    @FXML
    private TableColumn<BookModel, String> bookAuthor;
    @FXML
    private TableColumn<BookModel, String> bookGenre;
    @FXML
    private TableColumn<BookModel, Integer> bookQuantity;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "MainPage.fxml", "Main Page!", null);
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
        button_place_order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "PlaceOrder.fxml", "Order!",null);
            }
        });
        button_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            DBUtils.changeScene(event, "Settings.fxml", "Settings!", null);
        }
        });


        bookId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        bookGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        bookTableData.setItems(getTableData());
    }

    public void setUserInformation(String username){
        label_welcome.setText("Welcome " + username + "!");
    }

    private final ObservableList<BookModel> bookModels = FXCollections.observableArrayList(
            new BookModel(1, "Lorem", "Ipsum", "A", 2)
    );

    private ObservableList<BookModel> getTableData() {
        ObservableList<BookModel> tableData = FXCollections.observableArrayList();
        BookManager bookManager = BookManager.getManager();
        ArrayList<Book> books = bookManager.searchBook("");
        for (Book book:books) {
            tableData.add(new BookModel(
                    book.id,
                    book.title,
                    book.author,
                    book.genre,
                    book.quantity
            ));
        }
        return tableData;
    }
}
