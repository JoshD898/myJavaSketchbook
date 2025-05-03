package org.joshd898;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.joshd898.dao.DrawingDAO;
import org.joshd898.dao.UserDAO;
import org.joshd898.model.Drawing;
import org.joshd898.model.Gallery;
import org.joshd898.model.User;
import org.joshd898.ui.MainFrame;
import org.joshd898.ui.app.DisplayPanel;
import org.joshd898.ui.app.EmptyDisplayPanel;


// The class from which the application is run. 
// It contains fields for all the important objects needed to run the app.
// A singleton design pattern is employed for easy access to the fields
public class App {
    private static App instance;

    private Connection conn;
    private UserDAO userDAO;
    private DrawingDAO drawingDAO;
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
        } catch (SQLException | URISyntaxException | IOException e) {
            // pass
        }
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private void initializeConnection() throws URISyntaxException, SQLException, IOException {
        Properties prop = new Properties();
        prop.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

        String psqlString = prop.getProperty("psqlString");
        URI dbUri = new URI(psqlString);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        conn = DriverManager.getConnection(dbUrl, username, password);
    }

    // Attempts to register a new user with the provided username and password
    // Returns true if the user is successfully added to the database, false otherwise
    public boolean registerUser(String username, String password) {
        try {
            userDAO.addUserToDatabase(new User(username, password));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Attempts to log in with the provided credentials
    // If successful, sets this.user and this.gallery to the corresponding objects and returns true
    // Returns false if the user is not found or if an error occurs
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

        switchToDisplayPanel();
        
        return true;
    }

    public void switchToDisplayPanel() {
        if (gallery.isEmpty()) {
            MainFrame.getInstance().updateDisplay(new EmptyDisplayPanel());
        } else {
            MainFrame.getInstance().updateDisplay(new DisplayPanel());
        }
    }

    // Adds a drawing to both the local gallery and the database
    // Always adds to the local gallery; returns true if DB write succeeds, false otherwise
    public boolean addDrawing(Drawing d) {
        gallery.addDrawing(d);
        switchToDisplayPanel();

        try {
            drawingDAO.addToDatabase(d, user);
            return true;
        } catch (SQLException | IOException | NullPointerException e) {
            return false;
        }
    }

    // Saves edits to the current drawing both locally and in the database
    // Always updates the local drawing; returns true if DB write succeeds, false otherwise
    public boolean saveDrawingEdits(Drawing d) {
        gallery.getCurrentDrawing().setContent(d);
        gallery.getCurrentDrawing().setTitle(d.getTitle());
        switchToDisplayPanel();

        try {
            drawingDAO.updateDrawing(d);
            return true;
        } catch (SQLException | IOException | NullPointerException e) {
            return false;
        }
    }

    // Deletes the current drawing both locally and in the database
    // Always updates the local gallery; returns true if DB deletion succeeds, false otherwise
    public boolean deleteDrawing() {
        Drawing d = gallery.getCurrentDrawing();
        gallery.removeCurrentDrawing();
        gallery.decreaseCurrentIndex();

        switchToDisplayPanel();

        try {
            drawingDAO.deleteFromDatabase(d);
            return true;
        } catch (SQLException | NullPointerException e) {
            return false;
        }
    }

    public void goRight() {
        gallery.increaseCurrentIndex();
        MainFrame.getInstance().updateDisplay(new DisplayPanel());
    }

    public void goLeft() {
        gallery.decreaseCurrentIndex();
        MainFrame.getInstance().updateDisplay(new DisplayPanel());
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

    public void setGallery(Gallery g) {
        this.gallery = g;
    }
}
