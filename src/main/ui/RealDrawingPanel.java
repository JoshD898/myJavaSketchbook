package ui;

import model.RealDrawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * A JPanel that displays a drawing. The size of the JPanel is always the same as the drawing.
 * 
 * Clicking on the Panel will change the pixels on the drawing.
 */
public class RealDrawingPanel extends JPanel {
    private RealDrawing drawing;
    private Brush brush;

    public RealDrawingPanel(RealDrawing drawing) {
        this.drawing = drawing;
        super.setSize(new Dimension(drawing.getWidth(), drawing.getHeight()));
        brush = new Brush();
        super.addMouseListener(brush);
        super.addMouseMotionListener(brush);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(drawing.getWidth(), drawing.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawing, 0, 0, this);
    }

    private class Brush extends MouseAdapter {
        private void paint(int x, int y) {
            drawing.draw(x, y, 10, Color.BLACK);
            RealDrawingPanel.this.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            paint(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            paint(e.getX(), e.getY());
        }
    }

}
