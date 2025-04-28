package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Drawing;

/**
 * Displays a drawing that cannot be edited.
 */
public class DrawingPanel extends JPanel {
    protected Drawing drawing;

    private final Color BORDER_COLOR = Color.BLACK;
    private final int BORDER_SIZE = 2;

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
