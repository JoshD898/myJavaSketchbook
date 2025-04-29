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

    // Adds drawing to the Drawings table, then sets the unique ID of the drawing
    public void addToDatabase(User user, Drawing drawing) throws SQLException, IOException {
        String query = "INSERT INTO Drawings (userID, title, drawing_bytes) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, user.getUserID());
        statement.setString(2, drawing.getTitle());
        statement.setBytes(3, drawing.getByteArray());

        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        drawing.setDrawingID(generatedKeys.getInt(1));
    }

    // Deletes drawing row from the Drawings table
    public void deleteFromDatabase(Drawing d) {

    }

    // Updates the title of the drawing in the Drawings table
    public void updateTitle(Drawing d) {
        String query = "";

    }

    // Updates the drawing_bytes column in the Drawings table
    public void updateContent(Drawing d) {

    }

    // Return a Drawing object with all of the user's drawings
    public Drawing loadDrawing(int drawingID) {
        return null; //stub
    }
    
}
