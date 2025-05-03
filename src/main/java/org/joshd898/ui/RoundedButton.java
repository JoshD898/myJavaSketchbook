package org.joshd898.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;

public class RoundedButton extends JButton {
    private final Color backgroundColor;
    private final Color foregroundColor;
    private final int cornerRadius;
    private static final int BORDER_SIZE = 2;

    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    public RoundedButton(String text, int cornerRadius, Color backgroundColor, Color foregroundColor) {
        super(text);

        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;  
        this.cornerRadius = cornerRadius;

        super.setContentAreaFilled(false);
        super.setFocusPainted(false);
        super.setBorderPainted(false);
        super.setFont(BUTTON_FONT);
        super.setForeground(backgroundColor);

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
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

        g2.setColor(foregroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        g2.setColor(backgroundColor);
        g2.drawRoundRect(BORDER_SIZE, BORDER_SIZE, getWidth() - 2 * BORDER_SIZE, getHeight() - 2 * BORDER_SIZE, cornerRadius, cornerRadius);

        g2.dispose();

        super.paintComponent(g);
    }
}