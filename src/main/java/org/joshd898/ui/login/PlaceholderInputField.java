package org.joshd898.ui.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class PlaceholderInputField extends JTextField {
    private final String placeholder;
    private boolean showingPlaceholder;

    private static final Color BACKGROUND_COLOR = new Color(242, 242, 242);
    private static final Color PLACEHOLDER_COLOR = Color.GRAY;
    private static final int CORNER_RADIUS = 30;

    public PlaceholderInputField(String placeholder, int width, int height) {
        this.placeholder = placeholder;
        showingPlaceholder = true;

        super.setPreferredSize(new Dimension(width, height));
        super.setMinimumSize(new Dimension(width, height));
        super.setText(placeholder);
        super.addFocusListener(new PlaceholderFieldAdapter());       
        super.addMouseListener(new PlaceHolderFieldListener());
        super.setOpaque(false);
        super.setBorder(BorderFactory.createEmptyBorder());
        super.setForeground(PLACEHOLDER_COLOR);
    }

    @Override
    public String getText() {
        return showingPlaceholder ? "" : super.getText();
    }

    private class PlaceholderFieldAdapter extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            if (showingPlaceholder) {
                setText("");
                setForeground(Color.BLACK);
                showingPlaceholder = false;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (PlaceholderInputField.this.getText().isEmpty()) {
                PlaceholderInputField.this.setText(placeholder);
                PlaceholderInputField.this.setForeground(PLACEHOLDER_COLOR);
                showingPlaceholder = true;
            }
        }
    }

    private class PlaceHolderFieldListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (showingPlaceholder) {
                e.consume(); 
                setCaretPosition(0); 
            }
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 10, 0, 0); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}
