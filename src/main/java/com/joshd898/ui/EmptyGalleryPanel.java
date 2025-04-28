package com.joshd898.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays a message ot the user, telling them to click a button to add a new drawing.
 */
public class EmptyGalleryPanel extends AbstractLayout {
    private static final String MESSAGE = "Click New to add your first drawing!";
    private static final Font FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Color COLOR = Color.BLACK; 

    public EmptyGalleryPanel() {
        super();

        middlePanel = new JPanel(new GridBagLayout());
        middlePanel.add(messageText());
    }

    @Override
    public void addButtonsToBottomPanel() {
        JButton newButton = new CustomButton("New");
        bottomPanel.add(newButton);
        newButton.addActionListener(e -> onNew());
    }

    private JLabel messageText() {
        JLabel text = new JLabel(MESSAGE);
        text.setFont(FONT);
        text.setForeground(COLOR);
        text.setHorizontalAlignment(JLabel.CENTER);

        return text;
    }

    private void onNew() {
        UserInterface.getInstance().updateDisplay(new EaselPanel(true));
    }
}
