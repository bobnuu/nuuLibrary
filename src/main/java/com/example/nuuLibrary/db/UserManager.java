package com.example.nuuLibrary.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.nuuLibrary.db.models.User;


public class UserManager {
    private static final UserManager instance = new UserManager();
    Connector connector = new Connector();

    public  static UserManager getManager() {
        return instance;
    }

    public UserManager() {
        initTable();
        addInitialUser();
    }

    public void initTable() {
        Connection connection = null;
        String query = """
                CREATE TABLE IF NOT EXISTS users(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    username VARCHAR(100) UNIQUE,
                    phone VARCHAR(20) NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    is_admin BOOLEAN DEFAULT FALSE
                );
                """;
        try {
            connection = connector.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Failed to initialize users table");
            System.out.println(e.getMessage());
            if (connection != null) {
                connector.closeConnection(connection);
            }
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, name, username, phone, password, is_admin
                    FROM users
                    WHERE
                        id = ?
                    LIMIT 1;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            User user = null;
            while (results.next()) {
                user = new User(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getBoolean(6)
                );
            }
            return user;
        }
        catch (SQLException e) {
            System.out.println("Failed to get user");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    // Use isAdmin=false for user login, isAdmin=true for admin login.
    public User getUser(String username, String password, boolean isAdmin) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, name, username, phone, password, is_admin
                    FROM users
                    WHERE
                        username = ?
                        AND password = ?
                        AND is_admin = ?
                    LIMIT 1;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setBoolean(3, isAdmin);
            ResultSet results = statement.executeQuery();
            User user = null;
            while (results.next()) {
                user = new User(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getBoolean(6)
                );
            }
            return user;
        }
        catch (SQLException e) {
            System.out.println("Failed to get user");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    public void addInitialUser() {
        User user = new User(
                "Murod",
                "murod",
                "1234",
                "1234",
                false
        );
        User user1 = new User(
                "admin",
                "admin",
                "123456",
                "123456",
                true
        );
        if (getUser(user.username, user.password, user.isAdmin) == null) {
            createUser(user);
        }
    }

    // Returns an ArrayList of all the users (if isAdmin=false) or admins (if isAdmin=true).
    public ArrayList<User> getAllUsers(boolean isAdmin) {
        Connection connection = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, name, username, phone, password, is_admin
                    FROM users
                    WHERE
                        is_admin = ?
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, isAdmin);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                User user = new User(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getBoolean(6)
                );
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get user");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return users;
    }


    public User createUser(User user) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    INSERT INTO users (
                        name, username, phone, password, is_admin
                    )
                    VALUES (
                        ?, ?, ?, ?, ?
                    );
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.name);
            statement.setString(2, user.username);
            statement.setString(3, user.phone);
            statement.setString(4, user.password);
            statement.setBoolean(5, user.isAdmin);
            statement.execute();
            return getUser(
                    user.username,
                    user.password,
                    user.isAdmin
            );
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    public User updateUser(User user) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    UPDATE users
                    SET
                        name = ?,
                        username = ?,
                        phone = ?,
                        password = ?
                    WHERE
                        id = ?
                    ;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.name);
            statement.setString(2, user.username);
            statement.setString(3, user.phone);
            statement.setString(4, user.password);
            statement.setInt(5, user.id);
            statement.executeUpdate();
            return getUserById(user.id);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    public void deleteUser(User user) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    DELETE FROM users
                    WHERE
                        id = ?
                    ;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
    }
}
