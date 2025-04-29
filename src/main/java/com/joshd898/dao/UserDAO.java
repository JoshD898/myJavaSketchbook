package com.joshd898.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.joshd898.model.Gallery;
import com.joshd898.model.User;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Adds a user to the Users table
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        user.setUserID(generatedKeys.getInt(1));
    }

    // Returns true if the given username exists in the Users table
    public boolean usernameTaken(String username) throws SQLException {
        String query = "SELECT 1 FROM Users WHERE username = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    public Gallery getUserGallery(User user) {
        Gallery gallery = new Gallery();

        return gallery;
    }
}
