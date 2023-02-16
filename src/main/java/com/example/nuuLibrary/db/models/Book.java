package com.example.nuuLibrary.db.models;

public class Book {
    public int id;
    public String title;
    public String author;
    public String genre;
    public int quantity;

    public Book(int id, String title, String author, String genre, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

    public Book(String title, String author, String genre, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

}
