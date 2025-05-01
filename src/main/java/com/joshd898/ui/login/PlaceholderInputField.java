package com.joshd898.ui.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class PlaceholderInputField extends JTextField {
    private final String placeholder;
    private boolean showingPlaceholder;

    private static final Color BACKGROUND_COLOR = new Color(242, 242, 242);

    public PlaceholderInputField(String placeholder, int width, int height) {
        this.placeholder = placeholder;
        showingPlaceholder = true;

        super.setPreferredSize(new Dimension(width, height));
        super.setMinimumSize(new Dimension(width, height));
        super.setText(placeholder);
        super.setForeground(Color.GRAY);
        super.addFocusListener(new CustomAdapter());       
        super.addMouseListener(new CustomMouseListener());

        super.setBackground(BACKGROUND_COLOR);
    }

    @Override
    public String getText() {
        return showingPlaceholder ? "" : super.getText();
    }

    private class CustomAdapter extends FocusAdapter {
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
                PlaceholderInputField.this.setForeground(Color.GRAY);
                showingPlaceholder = true;
            }
        }
    }

    private class CustomMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (showingPlaceholder) {
                e.consume(); 
                setCaretPosition(0); 
            }
        }
    }
}
