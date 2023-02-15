package com.example.nuuLibrary.db.models;

public class User {
    public int id;
    public String name;
    public String username;
    public String phone;
    public String password;
    public boolean isAdmin;

    public User(int id, String name, String username, String phone, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(String name, String username, String phone, String password, boolean isAdmin) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
