package com.joshd898.dao;

import com.joshd898.model.Drawing;
import com.joshd898.model.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDrawingDAO extends AbstractDatabaseTest {
    private Drawing d1;
    private Drawing d2;
    private User user;

    private UserDAO userDAO;
    private DrawingDAO drawingDAO;

    private String query;
    private ResultSet rs;
    private PreparedStatement ps;

    @BeforeEach
    void runBefore() throws SQLException {
        d1 = new Drawing(10, 20, Color.WHITE, "Test");
        d2 = new Drawing(20, 30, Color.RED, "Red");
        user = new User("testUser", "password");

        drawingDAO = new DrawingDAO(connection);
        userDAO = new UserDAO(connection);

        PreparedStatement clearDrawings = connection.prepareStatement("DELETE FROM Drawings");
        clearDrawings.executeUpdate();
        clearDrawings.close();

        PreparedStatement clearUsers = connection.prepareStatement("DELETE FROM Users");
        clearUsers.executeUpdate();
        clearUsers.close();
    }

    @Test
    void testAddToDatabase() { 
        try {
            query = "SELECT * FROM Drawings";
            ps = connection.prepareStatement(query);

            userDAO.addUserToDatabase(user);

            rs = ps.executeQuery();
            assertFalse(rs.next());

            drawingDAO.addToDatabase(d1, user);

            rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals("Test", rs.getString("title")); 
            assertEquals(user.getUserID(), rs.getInt("userID")); 

        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testAddToDatabaseWithoutAddingUser() {
        try {
            drawingDAO.addToDatabase(d1, user);
            fail("SQLException should have been thrown");
        } catch (SQLException e) {
            //pass
        } catch (IOException e) {
            fail("SQLException should have been thrown");
        }
    }

    @Test
    void testDeleteFromDatabase() {
        try {
            query = "SELECT * FROM Drawings";
            ps = connection.prepareStatement(query);

            userDAO.addUserToDatabase(user);
            drawingDAO.addToDatabase(d1, user);

            rs = ps.executeQuery();
            assertTrue(rs.next());

            drawingDAO.deleteFromDatabase(d1);

            rs = ps.executeQuery();
            assertFalse(rs.next());
        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testUpdateDrawing() {
        try {
            userDAO.addUserToDatabase(user);

            drawingDAO.addToDatabase(d1, user);

            d1.setTitle("Updated title");
            d1.draw(0, 0, 1, Color.RED);

            drawingDAO.updateDrawing(d1);

            Drawing dFromDB = drawingDAO.loadDrawing(d1.getDrawingID());

            assertEquals(d1.getTitle(), dFromDB.getTitle());
            assertEquals(d1.getWidth(), dFromDB.getWidth());
            assertEquals(d1.getHeight(), dFromDB.getHeight());
            
            for (int x = 0; x < d1.getWidth(); x++) {
                for (int y = 0; y < d1.getHeight(); y++) {
                    assertEquals(d1.getRGB(x, y), dFromDB.getRGB(x, y));
                }
            }
        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testLoadGallery() {
        try {
            userDAO.addUserToDatabase(user);

            assertTrue(drawingDAO.loadGallery(user).getDrawingList().isEmpty());

            drawingDAO.addToDatabase(d1, user);
            assertEquals(1, drawingDAO.loadGallery(user).getDrawingList().size());

            drawingDAO.addToDatabase(d2, user);
            assertEquals(2, drawingDAO.loadGallery(user).getDrawingList().size());
        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }
}
