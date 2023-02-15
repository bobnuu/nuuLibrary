package com.example.nuuLibrary.db;

import com.example.nuuLibrary.db.models.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookManager {
    private static final BookManager instance = new BookManager();
    Connector connector = new Connector();

    public static BookManager getManager() {
        return instance;
    }

    public BookManager() {
        initTable();
        addInitialBooks();
    }

    public void initTable() {
        Connection connection = null;
        String query = """
                CREATE TABLE IF NOT EXISTS books(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(200) NOT NULL,
                    author VARCHAR(100) UNIQUE,
                    genre VARCHAR(100) NOT NULL,
                    quantity INT DEFAULT 0
                );
                """;
        try {
            connection = connector.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Failed to initialize books table");
            System.out.println(e.getMessage());
            if (connection != null) {
                connector.closeConnection(connection);
            }
            throw new RuntimeException(e);
        }
    }

    public Book getBookById(int id) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, title, author, genre, quantity
                    FROM books
                    WHERE
                        id = ?
                    LIMIT 1;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            Book book = null;
            while (results.next()) {
                book = new Book(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getInt(5)
                );
            }
            return book;
        }
        catch (SQLException e) {
            System.out.println("Failed to get book");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    public Book getBookByTitle(String title) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, title, author, genre, quantity
                    FROM books
                    WHERE
                        title = ?
                    LIMIT 1;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            Book book = null;
            while (results.next()) {
                book = new Book(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getInt(5)
                );
            }
            return book;
        }
        catch (SQLException e) {
            System.out.println("Failed to get book");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return null;
    }

    public ArrayList<Book> searchBook(String searchTerm) {
        Connection connection = null;
        ArrayList<Book> books = new ArrayList<>();
        String pattern = "%" + searchTerm + "%";
        try {
            connection = connector.getConnection();
            String query = """
                    SELECT
                        id, title, author, genre, quantity
                    FROM books
                    WHERE
                        title LIKE ? OR
                        author LIKE ? OR
                        genre LIKE ?
                    ;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                Book book = new Book(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getInt(5)
                );
                books.add(book);
            }
        }
        catch (SQLException e) {
            System.out.println("Failed to get books");
            System.out.println(e.getMessage());
        }
        finally {
            if (connection != null) {
                connector.closeConnection(connection);
            }
        }
        return books;
    }

    public void addInitialBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter", "J.K.Rowling", "Fantasy", 3));
        books.add(new Book("1984", "George Orwell", "R", 7));
        for (Book book: books) {
            if (getBookByTitle(book.title) == null) {
                createBook(book);
            }
        }
    }

    public Book createBook(Book book) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    INSERT INTO books (
                        title, author, genre, quantity
                    )
                    VALUES (
                        ?, ?, ?, ?
                    );
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.title);
            statement.setString(2, book.author);
            statement.setString(3, book.genre);
            statement.setInt(4, book.quantity);
            statement.execute();
            return getBookByTitle(book.title);
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

    public Book updateBook(Book book) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    UPDATE books
                    SET
                        title = ?,
                        author = ?,
                        genre = ?,
                        quantity = ?
                    WHERE
                        id = ?
                    ;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.title);
            statement.setString(2, book.author);
            statement.setString(3, book.genre);
            statement.setInt(4, book.quantity);
            statement.setInt(5, book.id);
            statement.executeUpdate();
            return getBookById(book.id);
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

    public void deleteBook(Book book) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            String query = """
                    DELETE FROM books
                    WHERE
                        id = ?
                    ;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, book.id);
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
