package org.joshd898.ui.login;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joshd898.App;
import org.joshd898.ui.RoundedButton;

public class RegistrationAndLoginPanel extends JPanel {
    private static final Color LOGIN_BACKGROUND = new Color(8, 93, 150);
    private static final Color REGISTER_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(8, 93, 160);

    private static final String USERNAME_PLACEHOLDER = "Username";
    private static final String PASSWORD_PLACEHOLDER = "Password";

    private static final String LOGIN_SWITCH_LABEL = "Made an account?";
    private static final String LOGIN_LABEL = "Login";
    private static final String LOGIN_BUTTON_LABEL = "Login";
    private static final String LOGIN_SWITCH_BUTTON_LABEL = "Click to login";

    private static final String REGISTER_SWITCH_LABEL = "Don't have an account?";
    private static final String REGISTER_LABEL = "Create Account";
    private static final String REGISTER_BUTTON_LABEL = "Register";
    private static final String REGISTER_SWITCH_BUTTON_LABEL = "Click to register";

    private static final String LOGIN_ERROR_MESSAGE = "Could not find find user in database";
    private static final String REGISTRATION_ERROR_MESSAGE = "Please choose a different username";
    private static final String REGISTRATION_SUCCESS_MESSAGE = "Registration successful!";
    private static final Color ERROR_COLOR = Color.RED;
    private static final Color SUCCESS_COLOR = Color.GREEN;

    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 24);

    private static final int INPUT_FIELD_WIDTH = 200;
    private static final int INPUT_FIELD_HEIGHT = 40;

    private static final int VERTICAL_SPACER = 10;

    private static final int BUTTON_CORNER_RADIUS = 30;
    private static final int BULGE_RADIUS = 100;

    private VerticallyStackedPanel loginPanel;
    private VerticallyStackedPanel registerPanel;
    private VerticallyStackedPanel switchToLoginPanel;
    private VerticallyStackedPanel switchToRegisterPanel;

    private PlaceholderInputField loginUsernameField;
    private PlaceholderInputField loginPasswordField;
    private JLabel loginErrorMessage;

    private PlaceholderInputField registrationUsernameField;
    private PlaceholderInputField registrationPasswordField;
    private JLabel registrationErrorMessage;

    private boolean onLoginPage;

    public RegistrationAndLoginPanel() {
        super.setLayout(new GridLayout(1, 2));

        initLoginPanel();
        initRegisterPanel();
        initSwitchToLoginPanel();
        initSwitchToRegisterPanel();

        showLoginPanel(); 
    }

    private void initLoginPanel() {
        loginPanel = new VerticallyStackedPanel(VERTICAL_SPACER);
        loginUsernameField = new PlaceholderInputField(USERNAME_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
        loginPasswordField = new PlaceholderInputField(PASSWORD_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
        loginErrorMessage = new JLabel();

        JButton loginButton = new RoundedButton(LOGIN_BUTTON_LABEL, BUTTON_CORNER_RADIUS, REGISTER_BACKGROUND, LOGIN_BACKGROUND);
        loginButton.addActionListener(e -> onLoginClicked());

        JLabel label = new JLabel(LOGIN_LABEL);
        label.setFont(LABEL_FONT);
        label.setForeground(REGISTER_BACKGROUND);

        loginPanel.add(label);
        loginPanel.add(loginUsernameField);
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);
        loginPanel.add(loginErrorMessage);
    }

    private void initRegisterPanel() {
        registerPanel = new VerticallyStackedPanel(VERTICAL_SPACER);
        registrationUsernameField = new PlaceholderInputField(USERNAME_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
        registrationPasswordField = new PlaceholderInputField(PASSWORD_PLACEHOLDER, INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT);
        registrationErrorMessage = new JLabel();
        
        JButton registerButton = new RoundedButton(REGISTER_BUTTON_LABEL, BUTTON_CORNER_RADIUS, LOGIN_BACKGROUND, REGISTER_BACKGROUND);
        registerButton.addActionListener(e -> onRegisterClicked());

        JLabel label = new JLabel(REGISTER_LABEL);
        label.setFont(LABEL_FONT);
        label.setForeground(LOGIN_BACKGROUND);

        registerPanel.add(label);
        registerPanel.add(registrationUsernameField);
        registerPanel.add(registrationPasswordField);
        registerPanel.add(registerButton);
        registerPanel.add(registrationErrorMessage);
    }

    private void initSwitchToLoginPanel() {
        switchToLoginPanel = new VerticallyStackedPanel(VERTICAL_SPACER);

        JButton goToLogin = new RoundedButton(LOGIN_SWITCH_BUTTON_LABEL, BUTTON_CORNER_RADIUS, REGISTER_BACKGROUND, LOGIN_BACKGROUND);
        goToLogin.addActionListener(e -> showLoginPanel());

        JLabel label = new JLabel(LOGIN_SWITCH_LABEL);
        label.setFont(LABEL_FONT);
        label.setForeground(REGISTER_BACKGROUND);

        switchToLoginPanel.add(label);
        switchToLoginPanel.add(goToLogin);
    }

    private void initSwitchToRegisterPanel() {
        switchToRegisterPanel = new VerticallyStackedPanel(VERTICAL_SPACER);

        JButton goToRegister = new RoundedButton(REGISTER_SWITCH_BUTTON_LABEL, BUTTON_CORNER_RADIUS, LOGIN_BACKGROUND, REGISTER_BACKGROUND);
        goToRegister.addActionListener(e -> showRegisterPanel());

        JLabel label = new JLabel(REGISTER_SWITCH_LABEL);
        label.setFont(LABEL_FONT);
        label.setForeground(LOGIN_BACKGROUND);

        switchToRegisterPanel.add(label);
        switchToRegisterPanel.add(goToRegister);
    }

    private void showLoginPanel() {
        super.removeAll();
        super.add(loginPanel);
        super.add(switchToRegisterPanel);
        resetErrorMessages();

        onLoginPage = true;

        super.revalidate();
        super.repaint();
    }

    private void showRegisterPanel() {
        super.removeAll();
        super.add(switchToLoginPanel);
        super.add(registerPanel);
        resetErrorMessages();

        onLoginPage = false;

        super.revalidate();
        super.repaint();
    }

    private void displayLoginErrorMessage() {
        loginErrorMessage.setText(LOGIN_ERROR_MESSAGE);
        loginErrorMessage.setForeground(ERROR_COLOR);
    }

    private void displayRegistrationErrorMessage() {
        registrationErrorMessage.setText(REGISTRATION_ERROR_MESSAGE);
        registrationErrorMessage.setForeground(ERROR_COLOR);
    }

    private void displayRegistrationSuccessMessage() {
        registrationErrorMessage.setText(REGISTRATION_SUCCESS_MESSAGE);
        registrationErrorMessage.setForeground(SUCCESS_COLOR);
    }

    private void resetErrorMessages() {
        loginErrorMessage.setText("");
        registrationErrorMessage.setText("");
    }

    private void onRegisterClicked() {
        String username = registrationUsernameField.getText();
        String password = registrationPasswordField.getText();

        boolean registrationSuccessful = App.getInstance().registerUser(username, password);

        if (registrationSuccessful) {
            displayRegistrationSuccessMessage();
        } else {
            displayRegistrationErrorMessage();
        }
    }

    private void onLoginClicked() {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        if (!App.getInstance().login(username, password)) {
            displayLoginErrorMessage();
        }
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

        int x = panelWidth / 2 - BULGE_RADIUS;
        int width = 2 * BULGE_RADIUS;
        int height = panelHeight;

        g2.setPaint(onLoginPage ? LOGIN_BACKGROUND : REGISTER_BACKGROUND);
        g2.fillOval(x, 0, width, height);

        g2.setStroke(new BasicStroke(2));
        g2.setPaint(BORDER_COLOR);
        int startAngle = onLoginPage ? 270 : 90;
        g2.drawArc(x, 0, width, height, startAngle, 180);
    }
}
