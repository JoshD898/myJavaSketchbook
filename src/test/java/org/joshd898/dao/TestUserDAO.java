package org.joshd898.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.joshd898.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO extends AbstractDatabaseTest {   
    private User user1;
    private User user2;

    private UserDAO userDAO;

    private String query;
    private ResultSet rs;
    private PreparedStatement ps;

    @BeforeEach
    void runBefore() throws SQLException {
        user1 = new User("testUser", "password");
        user2 = new User("Josh", "pwd");

        userDAO = new UserDAO(connection);

        PreparedStatement clearUsers = connection.prepareStatement("DELETE FROM Users");
        clearUsers.executeUpdate();
        clearUsers.close();
    }

    @Test
    void testAddUserToDatabase() {
        try {
            query = "SELECT * FROM Users";
            ps = connection.prepareStatement(query);

            rs = ps.executeQuery();
            assertFalse(rs.next());

            userDAO.addUserToDatabase(user1);

            rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals("testUser", rs.getString("username")); 
            assertEquals("password", rs.getString("password")); 
            assertEquals(1, user1.getUserID()); 

            userDAO.addUserToDatabase(user2);
            assertEquals(2, user2.getUserID());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testUsernameTaken() {
        try {
            userDAO.addUserToDatabase(user1);
        } catch (SQLException e) {
            fail(e.getMessage());
        }

        try {
            userDAO.addUserToDatabase(user1);
            fail("Should not be allowed to add 2 users with the same username");
        } catch (SQLException e) {
            // pass
        }
    }

    @Test
    void testGetUser() {
        try {
            userDAO.addUserToDatabase(user1);
            User fetchedUser = userDAO.getUser(user1.getUsername(), user1.getPassword());

            assertEquals(user1.getUsername(), fetchedUser.getUsername());
            assertEquals(user1.getPassword(), fetchedUser.getPassword());
            assertEquals(user1.getUserID(), fetchedUser.getUserID());

        } catch (SQLException | NoSuchElementException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetNonexistentUser() {
        try {
            userDAO.getUser("testUser", "password");
            fail("NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            // pass
        } catch (SQLException e) {
            fail("NoSuchElementException should have been thrown");
        }
    }
}
