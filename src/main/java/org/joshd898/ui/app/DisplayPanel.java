package org.joshd898.ui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.joshd898.App;
import org.joshd898.model.Drawing;
import org.joshd898.ui.MainFrame;
import org.joshd898.ui.RoundedButton;

public class DisplayPanel extends AbstractAppLayout {

    private static final Color BUTTON_FOREGROUND = new Color(8, 93, 150);
    private static final Color BUTTON_BACKGROUND = Color.WHITE;
    private static final int BUTTON_RADIUS = 75;

    private static final int HORIZONTAL_SPACER = 50;
    private static final int VERTICAL_SPACER = 10;

    private Drawing drawing;

    public DisplayPanel() {
        drawing = App.getInstance().getGallery().getCurrentDrawing();

        RoundedButton delete = super.makeRoundedButton("Delete");
        delete.addActionListener(e -> onDelete());

        RoundedButton edit = super.makeRoundedButton("Edit");
        edit.addActionListener(e -> onEdit());

        RoundedButton add = super.makeRoundedButton("+");
        add.addActionListener(e -> onAdd());

        super.addButtonToBottomPanel(delete);
        super.addButtonToBottomPanel(edit);
        super.addButtonToBottomPanel(add);


        makeMiddlePanel();
    }

    private void makeMiddlePanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, HORIZONTAL_SPACER, VERTICAL_SPACER, HORIZONTAL_SPACER);

        JLabel title = new JLabel(drawing.getTitle());
        title.setFont(LABEL_FONT);

        JButton leftButton = new RoundedButton("<", BUTTON_RADIUS, BUTTON_FOREGROUND, BUTTON_BACKGROUND);
        leftButton.setPreferredSize(new Dimension(BUTTON_RADIUS, BUTTON_RADIUS));
        leftButton.addActionListener(e -> goLeft());

        JButton rightButton = new RoundedButton(">", BUTTON_RADIUS, BUTTON_FOREGROUND, BUTTON_BACKGROUND);
        rightButton.setPreferredSize(new Dimension(BUTTON_RADIUS, BUTTON_RADIUS));
        rightButton.addActionListener(e -> goRight());

        gbc.gridx = 1;
        gbc.gridy = 0;      
        middlePanel.add(title, gbc); 
        
        gbc.gridx = 0;
        gbc.gridy = 1; 
        middlePanel.add(leftButton, gbc); 

        gbc.gridx = 1;
        gbc.gridy = 1;
        middlePanel.add(new DrawingPanel(drawing), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        middlePanel.add(rightButton, gbc);
    }

    private void onDelete() {
        App.getInstance().deleteDrawing();
    }

    private void onEdit() {
        MainFrame.getInstance().updateDisplay(new EditPanel(false));
    }

    private void onAdd() {
        MainFrame.getInstance().updateDisplay(new EditPanel(true));
    }

    private void goLeft() {
        App.getInstance().goLeft();
    }

    private void goRight() {
        App.getInstance().goRight();
    }
}
