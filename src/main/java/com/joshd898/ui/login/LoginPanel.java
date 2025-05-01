package com.joshd898.ui.login;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    private static final Color LOGIN_BACKGROUND = new Color(8, 93, 150);;
    private static final Color REGISTER_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(8, 93, 160);

    private static final String USERNAME_PLACEHOLDER = "Username";
    private static final String PASSWORD_PLACEHOLDER = "Password";
    private static final String LOGIN_LABEL = "Login";
    private static final String LOGIN_BUTTON_LABEL = "Login";
    private static final String REGISTER_LABEL = "Create Account";
    private static final String REGISTER_BUTTON_LABEL = "Register";


    private static final int INPUT_FIELD_WIDTH = 200;
    private static final int INPUT_FIELD_HEIGHT = 40;

    private static final int VERTICAL_SPACER = 5;

    private static final int BULGE_RADIUS = 50;

    private VerticallyStackedPanel loginPanel;
    private VerticallyStackedPanel registerPanel;
    private VerticallyStackedPanel switchToLoginPanel;
    private VerticallyStackedPanel switchToRegisterPanel;

    private JTextField usernameField;
    private JTextField passwordField;

    private boolean onLoginPage;

    public LoginPanel() {
        super.setLayout(new GridLayout(1, 2));

        usernameField = new PlaceholderInputField(USERNAME_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
        passwordField = new PlaceholderInputField(PASSWORD_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);

        initLoginPanel();
        initRegisterPanel();
        initSwitchToLoginPanel();
        initSwitchToRegisterPanel();

        showLoginPanel(); 
    }

    private void initLoginPanel() {
        loginPanel = new VerticallyStackedPanel(VERTICAL_SPACER);
        

        JButton loginButton = new JButton(LOGIN_BUTTON_LABEL);

        loginPanel.add(new JLabel(LOGIN_LABEL));
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
    }

    private void initRegisterPanel() {
        registerPanel = new VerticallyStackedPanel(VERTICAL_SPACER);
        
        JButton registerButton = new JButton(REGISTER_BUTTON_LABEL);

        registerPanel.add(new JLabel(REGISTER_LABEL));
        registerPanel.add(new PlaceholderInputField(USERNAME_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        registerPanel.add(new PlaceholderInputField(PASSWORD_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        registerPanel.add(registerButton);
    }

    private void initSwitchToLoginPanel() {
        switchToLoginPanel = new VerticallyStackedPanel(VERTICAL_SPACER);

        JButton goToLogin = new JButton("Back to Login");
        goToLogin.addActionListener(e -> showLoginPanel());

        switchToLoginPanel.add(new JLabel("Ready to Login?"));
        switchToLoginPanel.add(goToLogin);
    }

    private void initSwitchToRegisterPanel() {
        switchToRegisterPanel = new VerticallyStackedPanel(VERTICAL_SPACER);

        JButton goToRegister = new JButton("Register instead");
        goToRegister.addActionListener(e -> showRegisterPanel());

        switchToRegisterPanel.add(new JLabel("Don't have an account?"));
        switchToRegisterPanel.add(goToRegister);
    }


    private void showLoginPanel() {
        super.removeAll();
        super.add(loginPanel);
        super.add(switchToRegisterPanel);

        onLoginPage = true;

        super.revalidate();
        super.repaint();
    }

    private void showRegisterPanel() {
        super.removeAll();
        super.add(switchToLoginPanel);
        super.add(registerPanel);

        onLoginPage = false;

        super.revalidate();
        super.repaint();
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 800));
        frame.add(new LoginPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    

    @Override
    // overridden to set the background with a bulge in the middle
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setPaint(LOGIN_BACKGROUND);
        g2.fillRect(0, 0, panelWidth / 2, panelHeight);

        g2.setPaint(REGISTER_BACKGROUND);
        g2.fillRect(panelWidth / 2, 0, panelWidth / 2, panelHeight);

        if (onLoginPage) {
            g2.setPaint(LOGIN_BACKGROUND);
        } else {
            g2.setPaint(REGISTER_BACKGROUND);
        }

        int x = (panelWidth - 2 * BULGE_RADIUS) / 2;
        int width = 2 * BULGE_RADIUS;
        int height = panelHeight; 
        
        g2.fillOval(x, 0, width, height);

        g2.setStroke(new BasicStroke(2));
        g2.setPaint(BORDER_COLOR);

        if (onLoginPage) {
            g2.drawArc(x, 0, width, height, 270, 180);
        } else {
            g2.drawArc(x, 0, width, height, 90, 180);
        }
    }
}
