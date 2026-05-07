package com.cardss;

import com.cardss.auth.LoginPage;
import com.cardss.util.UITheme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        UITheme.applyGlobalDefaults();

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        UITheme.applyGlobalDefaults();

        SwingUtilities.invokeLater(() -> {
            LoginPage login = new LoginPage();
            login.setVisible(true);
        });
    }
}
