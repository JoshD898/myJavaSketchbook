package org.joshd898.ui.app;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.joshd898.model.Drawing;

// Displays a drawing that can be edited by clicking on it
public class EditableDrawingPanel extends DrawingPanel {
    private int brushSize;
    private Color brushColor;

    public EditableDrawingPanel(Drawing drawing, int brushSize, Color brushColor) {
        super(drawing);

        this.brushSize = brushSize;
        this.brushColor = brushColor;
        
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                paint(e.getX(), e.getY());
            }
        });
        super.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                paint(e.getX(), e.getY());
            }
        });
    }

    private void paint(int x, int y) {
        drawing.draw(x, y, brushSize, brushColor);
        super.repaint();
    }

    public Color getBrushColor() {
        return brushColor;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushColor(Color c) {
        brushColor = c;
    }

    public void setBrushSize(int size) {
        brushSize = size;
    }
}
