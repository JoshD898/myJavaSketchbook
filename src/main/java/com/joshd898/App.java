package com.joshd898;

import com.joshd898.dao.DrawingDAO;
import com.joshd898.dao.UserDAO;
import com.joshd898.model.User;
import com.joshd898.ui.UserInterface;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class App {

    private static final String psqlString = "postgresql://postgres:aPbCC9ULRj5KWo4x@forlornly-colossal-threadfin.data-1.usw2.tembo.io:5432/postgres";
    


    // public static void main(String[] args) {
    //     UserInterface.getInstance();
    // }

     public static void main(String[] args) {
        
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
}
