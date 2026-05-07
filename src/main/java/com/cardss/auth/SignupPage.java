package com.cardss.auth;

import com.cardss.util.AuthManager;
import com.cardss.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class SignupPage extends JDialog {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel errorLabel;

    public SignupPage(JFrame parent) {
        super(parent, "CarDSS — Create Account", true);
        setSize(480, 560);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel root = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UITheme.BG_DARK, getWidth(), getHeight(), new Color(0x0A, 0x0E, 0x1A));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        setContentPane(root);

        JPanel card = createCard();
        card.setBounds(40, 30, 400, 490);
        root.add(card);

        JLabel title = new JLabel("Create Account");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setBounds(30, 28, 340, 30);
        card.add(title);

        JLabel sub = new JLabel("Join CarDSS to find your perfect car");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setBounds(30, 60, 340, 20);
        card.add(sub);

        addFieldRow(card, "Username", 100);
        usernameField = UITheme.styledTextField(20);
        usernameField.setBounds(30, 122, 340, 36);
        card.add(usernameField);

        addFieldRow(card, "Email Address", 170);
        emailField = UITheme.styledTextField(20);
        emailField.setBounds(30, 192, 340, 36);
        card.add(emailField);

        addFieldRow(card, "Password", 240);
        passwordField = UITheme.styledPasswordField(20);
        passwordField.setBounds(30, 262, 340, 36);
        card.add(passwordField);

        addFieldRow(card, "Confirm Password", 310);
        confirmField = UITheme.styledPasswordField(20);
        confirmField.setBounds(30, 332, 340, 36);
        card.add(confirmField);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.ACCENT_RED);
        errorLabel.setBounds(30, 378, 340, 16);
        card.add(errorLabel);

        JButton registerBtn = UITheme.jPrimaryButton("Create Account");
        registerBtn.setBounds(30, 402, 340, 42);
        registerBtn.setPreferredSize(null);
        card.add(registerBtn);

        JButton cancelBtn = UITheme.jSecondaryButton("Back to Login");
        cancelBtn.setBounds(30, 452, 340, 30);
        cancelBtn.setPreferredSize(null);
        card.add(cancelBtn);

        registerBtn.addActionListener(e -> handleRegister());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void addFieldRow(JPanel parent, String labelText, int y) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        lbl.setBounds(30, y, 340, 16);
        parent.add(lbl);
    }

    private JPanel createCard() {
        return new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(4, 4, getWidth(), getHeight(), 18, 18);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 18, 18);
                g2.setColor(UITheme.ACCENT_GREEN);
                g2.setStroke(new BasicStroke(2f));
                g2.drawLine(20, 0, getWidth() - 24, 0);
                g2.dispose();
            }
        };
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        String error = AuthManager.register(username, email, password);
        if (error != null) {
            errorLabel.setText(error);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Account created successfully!\nYou can now log in.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
