package com.joshd898;

import com.formdev.flatlaf.FlatLightLaf;
import com.joshd898.dao.DrawingDAO;
import com.joshd898.dao.UserDAO;
import com.joshd898.model.Gallery;
import com.joshd898.model.User;
import com.joshd898.ui.MainFrame;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

// The class from which the application is run. 
// It contains fields for all the important objects needed to run the app.
// A singleton design pattern is employed for easy access to the fields
public class App {
    private static App instance;
    // TODO change password and redact string prior to final github push
    private static final String psqlString = "postgresql://postgres:aPbCC9ULRj5KWo4x@forlornly-colossal-threadfin.data-1.usw2.tembo.io:5432/postgres";
    
    private Connection conn;
    private static UserDAO userDAO;
    private static DrawingDAO drawingDAO;

    private User user;
    private Gallery gallery;

    public static void main(String[] args) {
        App.getInstance();
        MainFrame.getInstance();
    }

    private App() {
        try {
            initializeConnection();
            userDAO = new UserDAO(conn);
            drawingDAO = new DrawingDAO(conn);
        } catch (SQLException | URISyntaxException e) {
            System.err.println(e);
        }
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public boolean hasConnection() {
        return conn != null;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public void setGallery(Gallery g) {
        this.gallery = g;
    }

    private void initializeConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(psqlString);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?user=" + username + "&password=" + password;
        conn = DriverManager.getConnection(dbUrl);
    }

    // Returns true if new user is successfully added to the database, false otherwise
    public boolean registerUser(String username, String password) {
        try {
            userDAO.addUserToDatabase(new User(username, password));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Returns true, sets this.user and this.gallery to the coresponding objects if user exists in database
    // Returns false otherwise
    public boolean login(String username, String password) {
        User fetchedUser;
        Gallery fetchedGallery;

        try {
            fetchedUser = userDAO.getUser(username, password);
            fetchedGallery = drawingDAO.loadGallery(fetchedUser);
        } catch (NoSuchElementException | SQLException | IOException e) {
            return false;
        }

        this.user = fetchedUser;
        this.gallery = fetchedGallery;
        return true;
    }
}
