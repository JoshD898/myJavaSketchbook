package com.joshd898.ui;

import java.awt.BorderLayout;
import com.joshd898.model.Drawing;
import com.joshd898.model.Gallery;
import com.joshd898.ui.app.AbstractLayout;
import com.joshd898.ui.app.EmptyGalleryPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is the JFrame through which the GUI is shown.
 * 
 * A singleton pattern is emplyed to make it easy for other classes to access relevant fields.
 */
public class UserInterface extends JFrame {
    private static UserInterface instance;

    private Gallery gallery;
    private int currentIndex;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;

    private static final String APP_TITLE = "MyJavaSketchbook";
    private static final int DEFAULT_FRAME_WIDTH = 1500;
    private static final int DEFAULT_FRAME_HEIGHT = 800;
  
    private UserInterface() {
        instance = this;
        gallery = new Gallery();
        currentIndex = 0;

        super.setTitle(APP_TITLE);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        super.setLayout(new BorderLayout());

        updateDisplay(new EmptyGalleryPanel());

        super.setVisible(true);
    }

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public Gallery getGallery() {
        return gallery;
    }

    /**
     * Modifies the top, bottom and middle panels of the frame according to a layout template
     * 
     * @param layout The LayoutTemplate to udate the display to
     */
    public void updateDisplay(AbstractLayout layout) {
        super.getContentPane().removeAll();

        topPanel = layout.getTopPanel();
        middlePanel = layout.getMiddlePanel();
        bottomPanel = layout.getBottomPanel();

        super.add(topPanel, BorderLayout.NORTH);
        super.add(middlePanel, BorderLayout.CENTER);
        super.add(bottomPanel, BorderLayout.SOUTH);

        super.revalidate();
        super.repaint();
    }    

    public Drawing getCurrentDrawing() {
        return gallery.getDrawingList().get(currentIndex);
    }

    public void increaseCurrentIndex() {
        if (currentIndex < gallery.getDrawingList().size() - 1) {
            currentIndex++;
        }
    }

    public void decreaseCurrentIndex() {
        if (currentIndex > 0) {
            currentIndex--;
        }
    }

    /**
     * Removes the drawing at the current index from the gallery
     */
    public void removeCurrentDrawing() {
        gallery.removeDrawing(currentIndex);
        decreaseCurrentIndex();
    }
}
