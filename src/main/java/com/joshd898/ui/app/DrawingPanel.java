package com.joshd898.ui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import com.joshd898.model.Drawing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Displays a drawing that cannot be edited.
 */
public class DrawingPanel extends JPanel {
    protected Drawing drawing;

    private static final Color BORDER_COLOR = Color.BLACK;
    private static final int BORDER_SIZE = 2;

    public DrawingPanel(Drawing drawing) {
        this.drawing = drawing;

        super.setPreferredSize(new Dimension(drawing.getWidth(), drawing.getHeight()));
        super.setMinimumSize(new Dimension(drawing.getWidth(), drawing.getHeight()));

        super.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawing, 0, 0, this);
    }
}
