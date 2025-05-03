package org.joshd898.ui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.joshd898.App;
import org.joshd898.model.Drawing;
import org.joshd898.ui.RoundedButton;

public class EditPanel extends AbstractAppLayout {

    private static final Color[] COLORS = {
        Color.BLACK, 
        Color.GREEN, 
        Color.ORANGE, 
        Color.YELLOW, 
        Color.RED, 
        Color.BLUE, 
        Color.MAGENTA, 
        Color.WHITE, 
        Color.CYAN, 
        Color.GRAY
    };

    private static final int COLOR_BUTTON_RADIUS = 30;
    private static final int COLOR_BUTTON_SPACER = 10;

    private static final int MIN_BRUSH_SIZE = 1;
    private static final int MAX_BRUSH_SIZE = 50;

    private static final int BRUSH_SLIDER_HEIGHT = 300;
    private static final int INDICATOR_GAP = 30;

    private static final int DRAWING_WIDTH = 700;
    private static final int DRAWING_HEIGHT = 500;
    private static final Color DRAWING_BACKGROUND_COLOR = Color.WHITE;
    private static final String DEFAULT_DRAWING_TITLE = "Edit Me!";

    private static final Color COLOR_BUTTON_BORDER = Color.BLACK;   

    private static final int HORIZONTAL_SPACER = 50;
    private static final int VERTICAL_SPACER = 10;

    private EditableDrawingPanel drawingPanel;
    private JTextField title;
    private BrushIndicator brushIndicator;
    private Boolean isNewDrawing;

    public EditPanel(boolean isNewDrawing) {
        this.isNewDrawing = isNewDrawing;

        RoundedButton delete = super.makeRoundedButton("Cancel");
        delete.addActionListener(e -> onCancel());

        RoundedButton edit = super.makeRoundedButton("Clear");
        edit.addActionListener(e -> onClear());

        RoundedButton add = super.makeRoundedButton("Save");
        add.addActionListener(e -> onSave());

        super.addButtonToBottomPanel(delete);
        super.addButtonToBottomPanel(edit);
        super.addButtonToBottomPanel(add);

        if (isNewDrawing) {
            drawingPanel = new EditableDrawingPanel(new Drawing(DRAWING_WIDTH, DRAWING_HEIGHT, DRAWING_BACKGROUND_COLOR, DEFAULT_DRAWING_TITLE), MIN_BRUSH_SIZE, Color.BLACK);
        } else {
            drawingPanel = new EditableDrawingPanel(App.getInstance().getGallery().getCurrentDrawing().getCopy(), MIN_BRUSH_SIZE, Color.BLACK);
        }

        brushIndicator = new BrushIndicator();

        makeMiddlePanel();
    }

    // Switch back to display panel with no changes
    private void onCancel() {
        App.getInstance().switchToDisplayPanel();
    }

    // Clear all drawing from the drawingPanel
    private void onClear() {
        drawingPanel.getDrawing().clear();
        drawingPanel.repaint();
    }

    // Save or add the changes and switch back to display panel
    private void onSave() {
        drawingPanel.getDrawing().setTitle(title.getText());

        if (isNewDrawing) {
            App.getInstance().addDrawing(drawingPanel.getDrawing());
        } else {
            App.getInstance().saveDrawingEdits(drawingPanel.getDrawing());
        }
    }

    private void makeMiddlePanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, HORIZONTAL_SPACER, VERTICAL_SPACER, HORIZONTAL_SPACER);

        gbc.gridx = 1;
        gbc.gridy = 0;

        title = new JTextField(drawingPanel.getDrawing().getTitle());
        title.setFont(LABEL_FONT);
        title.setHorizontalAlignment(JTextField.CENTER); 
        title.setPreferredSize(new Dimension(drawingPanel.getDrawing().getWidth() / 2, title.getPreferredSize().height));
        
        middlePanel.add(title, gbc); 
        
        gbc.gridx = 0;
        gbc.gridy = 1; 
        middlePanel.add(brushSliderAndIndicator(), gbc); 

        gbc.gridx = 1;
        gbc.gridy = 1;
        middlePanel.add(drawingPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        middlePanel.add(colorButtons(), gbc);
    }

    private JPanel colorButtons() {
        JPanel panel = new JPanel(new GridLayout(5, 2, COLOR_BUTTON_SPACER, COLOR_BUTTON_SPACER)); 
        panel.setOpaque(false);
        for (Color color : COLORS) {            
            panel.add(new ColorButton(color));
        }      
        return panel;
    }

    private JPanel brushSliderAndIndicator() {
        JPanel panel = new JPanel(new BorderLayout());

        JSlider sizeSlider = new JSlider(JSlider.VERTICAL, 
                                         MIN_BRUSH_SIZE, 
                                         MAX_BRUSH_SIZE, 
                                         drawingPanel.getBrushSize());

        sizeSlider.setMinimumSize(new Dimension(1, BRUSH_SLIDER_HEIGHT));
        sizeSlider.addChangeListener(e -> {
            drawingPanel.setBrushSize(sizeSlider.getValue());
            brushIndicator.repaint();
        });

        panel.add(brushIndicator, BorderLayout.NORTH);
        panel.add(sizeSlider, BorderLayout.CENTER);

        sizeSlider.setOpaque(false);
        panel.setOpaque(false);

        return panel;
    }

    // Acts as an indicator of the brush size and color
    private class BrushIndicator extends JPanel {
        public BrushIndicator() {
            super.setPreferredSize(new Dimension(2 * MAX_BRUSH_SIZE, 2 * MAX_BRUSH_SIZE + INDICATOR_GAP));
            super.setMinimumSize(new Dimension(2 * MAX_BRUSH_SIZE, 2 * MAX_BRUSH_SIZE + INDICATOR_GAP));
            super.setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int diameter = drawingPanel.getBrushSize() * 2;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(drawingPanel.getBrushColor());          
            g2.fillOval(x, y, diameter, diameter);
            g2.dispose();
        }
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
                drawingPanel.setBrushColor(color);
                brushIndicator.repaint();
            });

            super.addMouseListener(new MouseAdapter() {
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

            g2.setColor(COLOR_BUTTON_BORDER);
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
}

