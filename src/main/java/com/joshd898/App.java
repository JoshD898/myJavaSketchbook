package com.joshd898;

import com.joshd898.dao.DrawingDAO;
import com.joshd898.dao.UserDAO;
import com.joshd898.model.Gallery;
import com.joshd898.model.User;
import com.joshd898.ui.UserInterface;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// The class from which the applicatoin is run.
// A singleton design pattern is emplyed for easy access to the app's userDAO and drawingDAO objects
public class App {
    private static App instance;
    // TODO change password and redact string prior to final github push
    private static final String psqlString = "postgresql://postgres:aPbCC9ULRj5KWo4x@forlornly-colossal-threadfin.data-1.usw2.tembo.io:5432/postgres";
    
    private static UserDAO userDAO;
    private static DrawingDAO drawingDAO;

    private User user;
    private Gallery gallery;
    private int galleryIndex;

    public static void main(String[] args) {
        UserInterface.getInstance();
    }

    private App() {
        try {
            URI dbUri = new URI(psqlString);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?user=" + username + "&password=" + password;

            Connection conn = DriverManager.getConnection(dbUrl);

            UserDAO userDAO = new UserDAO(conn);
            DrawingDAO drawingDAO = new DrawingDAO(conn);
            
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

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public DrawingDAO getDrawingDAO() {
        return drawingDAO;
    }
}
