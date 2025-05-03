package org.joshd898.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.joshd898.App;
import org.joshd898.model.Gallery;
import org.joshd898.ui.app.EmptyDisplayPanel;
import org.joshd898.ui.login.RegistrationAndLoginPanel;

/**
 * This class is the JFrame through which the GUI is shown.
 * 
 * A singleton pattern is emplyed to make it easy for other classes to access relevant fields.
 */
public class MainFrame extends JFrame {
    private static MainFrame instance;

    private static final String APP_TITLE = "MyJavaSketchbook";
    private static final int DEFAULT_FRAME_WIDTH = 1500;
    private static final int DEFAULT_FRAME_HEIGHT = 800;
  
    private MainFrame() {
        super.setTitle(APP_TITLE);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        super.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());

        // If there is a connection to the database, show login panel
        // Else, launch main app with an empty gallery
        if (App.getInstance().hasConnection()) {
            updateDisplay(new RegistrationAndLoginPanel());
        } else {
            App.getInstance().setGallery(new Gallery());
            updateDisplay(new EmptyDisplayPanel());
        }
        super.setVisible(true);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public void updateDisplay(JPanel content) {
        super.getContentPane().removeAll();
        super.getContentPane().add(content);
        super.revalidate();
        super.repaint();
    }        
}
