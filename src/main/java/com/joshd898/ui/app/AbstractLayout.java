package com.joshd898.ui.app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Provides a base layout for panels in the application, consisting of a top panel, a middle panel, and a bottom panel. 
 * It also provides a custom button design for use in the bottom panel.
 * 
 * Subclasses must add their content to the middle panel, and add their own buttons to the bottom panel.
 */
abstract class AbstractLayout {
    private static final int TOP_PANEL_HEIGHT = 60;
    private static final int BOTTOM_PANEL_HEIGHT = 60;

    private static final Color PANEL_COLOR = new Color(8, 93, 150);

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 40;
    private static final Color BUTTON_FILL_COLOR = new Color(220, 243, 245);
    private static final Color BUTTON_BORDER_COLOR = Color.BLACK;
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final int BUTTON_CORNER_RADIUS = 20;

    protected JPanel topPanel;
    protected JPanel middlePanel;
    protected JPanel bottomPanel;

    public AbstractLayout() {
        topPanel = new JPanel();
        bottomPanel = new JPanel();

        topPanel.setPreferredSize(new Dimension(1, TOP_PANEL_HEIGHT));
        bottomPanel.setPreferredSize(new Dimension(1, BOTTOM_PANEL_HEIGHT));

        topPanel.setBackground(PANEL_COLOR);
        bottomPanel.setBackground(PANEL_COLOR);

        addButtonsToBottomPanel();
    }


    /**
     * Subclasses must implement this method to add buttons to the bottom panel
     */
    abstract void addButtonsToBottomPanel();

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getMiddlePanel() {
        return middlePanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    /**
     * A custom button class that is designed for use in the bottom panel.
     * The button has rounded corners, a custom background color, and a border.
     * It also changes the cursor to a hand icon when hovered over.
     */
    protected class CustomButton extends JButton {
        public CustomButton(String text) {
            super(text);
            super.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            super.setContentAreaFilled(false);
            super.setFocusPainted(false);
            super.setBorderPainted(false); 
            super.setFont(BUTTON_FONT);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
    
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(BUTTON_FILL_COLOR); 
            g2.fillRoundRect(0, 0, super.getWidth(), super.getHeight(), BUTTON_CORNER_RADIUS, BUTTON_CORNER_RADIUS); 

            g2.setColor(BUTTON_BORDER_COLOR);
            g2.drawRoundRect(0, 0, super.getWidth(), super.getHeight(), BUTTON_CORNER_RADIUS, BUTTON_CORNER_RADIUS);
    
            g2.dispose();
            super.paintComponent(g); 
        }
    }
}
