package com.joshd898.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.joshd898.model.Drawing;
import com.joshd898.model.Gallery;
import com.joshd898.model.User;

public class DrawingDAO {
    private final Connection connection;

    public DrawingDAO(Connection connection) {
        this.connection = connection;
    }

    // Adds drawing associated with user to the Drawings table, then sets the unique ID of the drawing
    public void addToDatabase(Drawing drawing, User user) throws SQLException, IOException {
        String query = "INSERT INTO Drawings (userID, title, drawing_bytes) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, user.getUserID());
            statement.setString(2, drawing.getTitle());
            statement.setBytes(3, drawing.getByteArray());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                drawing.setDrawingID(generatedKeys.getLong(1));
            }            
        }
    }

    // Deletes drawing from the Drawings table
    public void deleteFromDatabase(Drawing d) throws SQLException {
        String query = "DELETE FROM Drawings WHERE drawingID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, d.getDrawingID());
            statement.executeUpdate();
        }        
    }

    // Overwrites the title and drawing_bytes of the given drawing
    public void updateDrawing(Drawing d) throws SQLException, IOException {
        String query = "UPDATE Drawings SET title = ?, drawing_bytes = ? WHERE drawingID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, d.getTitle());
            statement.setBytes(2, d.getByteArray());
            statement.setLong(3, d.getDrawingID());
            statement.executeUpdate();
        }  
    }

    // Returns a gallery of all the user's drawings
    public Gallery loadGallery(User user) throws SQLException, IOException{
        String query = "SELECT drawingID FROM Drawings WHERE userID = ?";
        Gallery gallery = new Gallery();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getUserID());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    gallery.addDrawing(loadDrawing(rs.getLong("drawingID")));
                }
            }
        }

        return gallery;
    }

    // Return a Drawing object with all of the user's drawings
    public Drawing loadDrawing(long drawingID) throws SQLException, IOException {
        String query = "SELECT title, drawing_bytes FROM Drawings WHERE drawingID = ?";
        String title;
        byte[] bytes;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, drawingID);

            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                title = rs.getString("title");
                bytes = rs.getBytes("drawing_bytes");
            }
        }

        return Drawing.fromBytes(title, drawingID, bytes);
    }

    
    
}
