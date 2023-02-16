package com.example.nuuLibrary.db;

import java.sql.*;

public class Connector {
    private String dbURL = null;
    private String dbUser = null;
    private String dbPassword = null;

    public Connector() {
        this.dbURL = "jdbc:mysql://localhost:3306/nuu_library";
        this.dbUser = "library_db_user";
        this.dbPassword = "12345678";
    }

    public Connection getConnection() throws  SQLException {
        return DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Could not close database connection");
            System.out.println(e.getMessage());
        }
    }
}
