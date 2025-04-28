package com.joshd898.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Provides a structure for components that will display a drawing panel.
 * Initializes a title field to display the title of the drawing above the drawing panel.
 * 
 * Subclasses are responsible for defining their left and right components.
 */
abstract class AbstractDisplayPanel extends AbstractLayout {
    private static final int HORIZONTAL_SPACER = 85;
    private static final int VERTICAL_SPACER = 25;

    private static final int TITLE_WIDTH = 500;
    private static final Font TITLE_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final int TITLE_HEIGHT = 30;

    protected DrawingPanel drawingPanel;
    protected JTextField title;
    protected JComponent leftComponent;
    protected JComponent rightComponent;

    public AbstractDisplayPanel() {
        title = new JTextField();
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setPreferredSize(new Dimension(TITLE_WIDTH, TITLE_HEIGHT));
        title.setMinimumSize(new Dimension(TITLE_WIDTH, TITLE_HEIGHT));
        title.setFont(TITLE_FONT);
    }

    protected void makeMiddlePanel() {
        middlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, HORIZONTAL_SPACER, VERTICAL_SPACER, HORIZONTAL_SPACER);

        gbc.gridx = 1;
        gbc.gridy = 0;
        middlePanel.add(title, gbc); 
        
        gbc.gridx = 0;
        gbc.gridy = 1; 
        middlePanel.add(leftComponent, gbc); 

        gbc.gridx = 1;
        gbc.gridy = 1;
        middlePanel.add(drawingPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        middlePanel.add(rightComponent, gbc);
    }
}
