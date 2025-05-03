package org.joshd898.ui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joshd898.App;
import org.joshd898.ui.RoundedButton;

public class AbstractAppLayout extends JPanel {

    private static final int TOP_PANEL_HEIGHT = 60;
    private static final int BOTTOM_PANEL_HEIGHT = 60;

    private static final Color PANEL_COLOR = new Color(8, 93, 150);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int BULGE_RADIUS = 50;

    private static final int BUTTON_CORNER_RADIUS = 30;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_SPACER = 25;

    private static final String NO_CONNECTION_MESSAGE = "Database connection failed. Any changes made during this session will not be saved.";

    protected static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 16);

    private JPanel topPanel;
    private JPanel bottomPanel;
    protected JPanel middlePanel;
    
    public AbstractAppLayout() {
        super(new BorderLayout());
        
        initTopPanel();
        initMiddlePanel();
        initBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(1, TOP_PANEL_HEIGHT));

        JLabel topLabel;

        if (App.getInstance().hasConnection()) {
            topLabel = new JLabel(App.getInstance().getUser().getUsername() + "'s Gallery");
        } else {
            topLabel = new JLabel(NO_CONNECTION_MESSAGE);
        }

        topLabel.setForeground(BACKGROUND_COLOR);
        topLabel.setFont(LABEL_FONT);
        topPanel.add(topLabel);
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(1, BOTTOM_PANEL_HEIGHT));
    }

    private void initMiddlePanel() {
        middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setOpaque(false);
    }

    protected RoundedButton makeRoundedButton(String text) {
        RoundedButton button = new RoundedButton(text, BUTTON_CORNER_RADIUS, BACKGROUND_COLOR, PANEL_COLOR);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return button;
    }

    protected void addButtonToBottomPanel(JButton button) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, BUTTON_SPACER, 0, BUTTON_SPACER);
        bottomPanel.add(button, gbc);
    }

    @Override
    // Overridden to add curves to the top and bottom panels
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(PANEL_COLOR);
        g2.fillRect(0, 0, panelWidth, TOP_PANEL_HEIGHT + BULGE_RADIUS);
        g2.fillRect(0, panelHeight - BOTTOM_PANEL_HEIGHT - BULGE_RADIUS, panelWidth, BOTTOM_PANEL_HEIGHT + BULGE_RADIUS);

        int width = panelWidth;
        int height = 2 * BULGE_RADIUS;

        g2.setPaint(Color.WHITE);
        g2.fillRect(0, TOP_PANEL_HEIGHT + BULGE_RADIUS, panelWidth, panelHeight - TOP_PANEL_HEIGHT - BOTTOM_PANEL_HEIGHT - 2 * BULGE_RADIUS);
        g2.fillOval(0, TOP_PANEL_HEIGHT, width, height);
        g2.fillOval(0, panelHeight - BOTTOM_PANEL_HEIGHT - 2 * BULGE_RADIUS, width, height);
    } 
}
