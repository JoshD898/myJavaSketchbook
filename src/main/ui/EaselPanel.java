package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

import model.Drawing;


/**
 * Display a single drawing that can be drawn on via mouse actions. Users can change the brush color and size, as well as the drawing title.
 * 
 * Buttons on the bottom panel allow users to clear the drawing, cancel their changes or save their changes.
 */
public class EaselPanel extends AbstractDisplayPanel {
    private static final Color[] COLORS = {Color.BLACK, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE, Color.MAGENTA, Color.WHITE, Color.CYAN, Color.GRAY};
    private static final int COLOR_BUTTON_RADIUS = 30;
    private static final int COLOR_BUTTON_SPACER = 10;

    private static final int MIN_BRUSH_SIZE = 5;
    private static final int MAX_BRUSH_SIZE = 50;

    private static final int BRUSH_SLIDER_HEIGHT = 300;
    private static final int INDICATOR_GAP = 30;

    private static final int DRAWING_WIDTH = 700;
    private static final int DRAWING_HEIGHT = 500;
    private static final Color DRAWING_BACKGROUND_COLOR = Color.WHITE;
    private static final String DEFAULT_DRAWING_TITLE = "Edit Me!";

    private BrushIndicator brushIndicator;
    private Boolean isNewDrawing;

    public EaselPanel(Boolean isNewDrawing) {
        super();
        this.isNewDrawing = isNewDrawing;

        if (isNewDrawing) {
            drawingPanel = new EditableDrawingPanel(makeDefaultDrawing(), MIN_BRUSH_SIZE, Color.BLACK);
        } else {
            drawingPanel = new EditableDrawingPanel(UserInterface.getInstance().getCurrentDrawing().getCopy(), MIN_BRUSH_SIZE, Color.BLACK);
        }

        title.setText(drawingPanel.drawing.getTitle());
        brushIndicator = new BrushIndicator();

        leftComponent = brushSliderAndIndicator();
        rightComponent = colorButtons();

        super.makeMiddlePanel();
    }

    @Override
    protected void addButtonsToBottomPanel() {
        JButton cancel = new CustomButton("Cancel");
        JButton clear = new CustomButton("Clear");
        JButton save = new CustomButton("Save");

        bottomPanel.add(cancel);
        bottomPanel.add(clear);
        bottomPanel.add(save);
        
        cancel.addActionListener(e -> onCancel());
        clear.addActionListener(e -> onClear());
        save.addActionListener(e -> onSave());
    }

    private Drawing makeDefaultDrawing() {
        return new Drawing(DRAWING_WIDTH, DRAWING_HEIGHT, DRAWING_BACKGROUND_COLOR, DEFAULT_DRAWING_TITLE);
    }

    

    private JPanel colorButtons() {
        JPanel panel = new JPanel(new GridLayout(5, 2, COLOR_BUTTON_SPACER, COLOR_BUTTON_SPACER)); 

        for (Color color : COLORS) {
            panel.add(new ColorButton(color));
        }

        return panel;
    }

    private class ColorButton extends JButton {
        public ColorButton(Color color) {
            int diameter = COLOR_BUTTON_RADIUS * 2;
            super.setPreferredSize(new Dimension(diameter, diameter));
            super.setMinimumSize(new Dimension(diameter, diameter));
            super.setBackground(color);
            super.setContentAreaFilled(false);
            super.setFocusPainted(false);
            super.setBorderPainted(false);
    
            super.addActionListener(e -> {
                ((EditableDrawingPanel) drawingPanel).setBrushColor(color);
                brushIndicator.repaint();
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
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

            g2.setColor(Color.BLACK);
            g2.fillOval(0, 0, getWidth(), getHeight());

            g2.setColor(getBackground());
            g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);

            super.paintComponent(g);
        }

        @Override
        public boolean contains(int x, int y) {
            int radius = getWidth() / 2;
            int centerX = radius;
            int centerY = getHeight() / 2;
            return ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) <= (radius * radius);
        }
    }


    private JPanel brushSliderAndIndicator() {
        JPanel panel = new JPanel(new BorderLayout());

        JSlider sizeSlider = new JSlider(JSlider.VERTICAL, MIN_BRUSH_SIZE, MAX_BRUSH_SIZE, ((EditableDrawingPanel) drawingPanel).getBrushSize());
        sizeSlider.setMinimumSize(new Dimension(1, BRUSH_SLIDER_HEIGHT));
        sizeSlider.addChangeListener(e -> {
            ((EditableDrawingPanel) drawingPanel).setBrushSize(sizeSlider.getValue());
            brushIndicator.repaint();
        });

        panel.add(brushIndicator, BorderLayout.NORTH);
        panel.add(sizeSlider, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Acts as an indicator of the brsuh size and color.
     */
    private class BrushIndicator extends JPanel {
        public BrushIndicator() {
            super.setPreferredSize(new Dimension(2 * MAX_BRUSH_SIZE, 2 * MAX_BRUSH_SIZE + INDICATOR_GAP));
            super.setMinimumSize(new Dimension(2 * MAX_BRUSH_SIZE, 2 * MAX_BRUSH_SIZE + INDICATOR_GAP));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int diameter = ((EditableDrawingPanel) drawingPanel).getBrushSize() * 2;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(((EditableDrawingPanel) drawingPanel).getBrushColor());          
            g2.fillOval(x, y, diameter, diameter);
            g2.dispose();
        }
    }

    private void onCancel() {
        UserInterface ui = UserInterface.getInstance();

        if (ui.getGallery().getDrawingList().size() == 0) {
            ui.updateDisplay(new EmptyGalleryPanel());
        } else {
            ui.updateDisplay(new GalleryPanel());
        }
    }

    /**
     * If the drawing being edited is a new drawing, then it is added to the gallery.
     * 
     * If it is a currently existing drawing, then the current drawing content is overwritten.
     */
    private void onSave() {
        UserInterface ui = UserInterface.getInstance();

        if (isNewDrawing) {
            drawingPanel.drawing.setTitle(title.getText());
            ui.getGallery().addDrawing(drawingPanel.drawing);
        } else {
            if (!ui.getCurrentDrawing().getTitle().equals(title.getText())) {
                ui.getCurrentDrawing().setTitle(title.getText());
            }

            ui.getCurrentDrawing().setContent(drawingPanel.drawing);
        }

        ui.updateDisplay(new GalleryPanel());
    }

    private void onClear() {
        drawingPanel.drawing.clear();
        drawingPanel.repaint();
    }
}
