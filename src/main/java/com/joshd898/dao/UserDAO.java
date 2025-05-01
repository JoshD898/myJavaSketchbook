package com.joshd898.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.joshd898.model.User;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Adds a user to the Users table
    public void addUserToDatabase(User user) throws SQLException {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
    
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                user.setUserID(generatedKeys.getLong(1));
            }
        }
    }

    // Returns a User if the given username and password correspond to an existing user, throws NoSuchElementException otherwise
    public User getUser(String username, String password) throws SQLException, NoSuchElementException {
        String query = "SELECT userID FROM Users WHERE username = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User(username, password);
                    user.setUserID(rs.getLong("userID"));
                    return user;
                } else {
                    throw new NoSuchElementException("A User with this username and password doesn't exist");
                }
            }
        }
    }
}
