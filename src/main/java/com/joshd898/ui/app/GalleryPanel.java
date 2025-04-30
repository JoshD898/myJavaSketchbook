package com.joshd898.ui.app;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.joshd898.ui.UserInterface;

/**
 * DisplayPanel displays a view-only version of drawings and their titles.
 * 
 * Left and right arrows are shown to allow users to navigate through drawings in the gallery
 */
public class GalleryPanel extends AbstractDisplayPanel {

    public GalleryPanel() {
        super();
        drawingPanel = new DrawingPanel(UserInterface.getInstance().getCurrentDrawing());
        title.setEditable(false);
        title.setText(drawingPanel.drawing.getTitle());
        title.setBorder(BorderFactory.createEmptyBorder());

        leftComponent = navigateLeftButton();
        rightComponent = navigateRightButton();

        super.makeMiddlePanel();
    }

    @Override
    protected void addButtonsToBottomPanel() {
        JButton delete = new CustomButton("Delete");
        JButton edit = new CustomButton("Edit");
        JButton newButton = new CustomButton("New");

        bottomPanel.add(delete);
        bottomPanel.add(edit);
        bottomPanel.add(newButton);
        
        delete.addActionListener(e -> onDelete());
        edit.addActionListener(e -> onEdit());
        newButton.addActionListener(e -> onNew());
    }

    private JButton navigateLeftButton() {
        JButton button = new JButton("<");
        button.setPreferredSize(new Dimension(50,50));
        button.addActionListener(e -> {
            UserInterface ui = UserInterface.getInstance();
            ui.decreaseCurrentIndex();
            ui.updateDisplay(new GalleryPanel());
        });
        return button;
    }

    private JButton navigateRightButton() {
        JButton button = new JButton(">");
        button.setPreferredSize(new Dimension(50,50));
        button.addActionListener(e -> {
            UserInterface ui = UserInterface.getInstance();
            ui.increaseCurrentIndex();
            ui.updateDisplay(new GalleryPanel());
        });
        return button;
    }

    private void onDelete() {
        UserInterface ui = UserInterface.getInstance();

        ui.removeCurrentDrawing();

        if (ui.getGallery().getDrawingList().size() == 0) {
            ui.updateDisplay(new EmptyGalleryPanel());
        } else {
            ui.updateDisplay(new GalleryPanel());
        }
    }

    private void onEdit() {
        UserInterface.getInstance().updateDisplay(new EaselPanel(false));
    }

    private void onNew() {
        UserInterface.getInstance().updateDisplay(new EaselPanel(true));
    }
}


