package com.cardss.auth;

import com.cardss.dashboard.DashboardPage;
import com.cardss.util.AuthManager;
import com.cardss.util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JLabel         errorLabel;

    public LoginPage() {
        setTitle("CarDSS — Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        root.add(buildHeroPanel(),  BorderLayout.WEST);
        root.add(buildFormPanel(), BorderLayout.CENTER);
    }

    private JPanel buildHeroPanel() {
        JPanel panel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0x0F, 0x11, 0x2E),
                        getWidth(), getHeight(), new Color(0x1E, 0x0A, 0x35));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                RadialGradientPaint orb1 = new RadialGradientPaint(
                        new Point(-20, -20), 220,
                        new float[]{0f, 1f},
                        new Color[]{new Color(0x7C, 0x4D, 0xFF, 80), new Color(0,0,0,0)});
                g2.setPaint(orb1);
                g2.fillOval(-120, -120, 440, 440);

                RadialGradientPaint orb2 = new RadialGradientPaint(
                        new Point(getWidth() + 20, getHeight() + 20), 200,
                        new float[]{0f, 1f},
                        new Color[]{new Color(0x4A, 0x9E, 0xFF, 60), new Color(0,0,0,0)});
                g2.setPaint(orb2);
                g2.fillOval(getWidth() - 180, getHeight() - 180, 400, 400);

                g2.setColor(new Color(0xFF, 0xFF, 0xFF, 8));
                for (int x = 20; x < getWidth(); x += 28) {
                    for (int y = 20; y < getHeight(); y += 28) {
                        g2.fillOval(x, y, 2, 2);
                    }
                }

                GradientPaint sep = new GradientPaint(
                        getWidth() - 2, 0, new Color(0x7C, 0x4D, 0xFF, 80),
                        getWidth(), 0, new Color(0,0,0,0));
                g2.setPaint(sep);
                g2.fillRect(getWidth() - 3, 0, 3, getHeight());

                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(420, 0));
        panel.setOpaque(false);

        JLabel carIcon = new JLabel("🚗", SwingConstants.CENTER);
        carIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        carIcon.setBounds(85, 100, 250, 100);
        panel.add(carIcon);

        JLabel brand = new JLabel("CarDSS", SwingConstants.CENTER);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 38));
        brand.setForeground(UITheme.TEXT_PRIMARY);
        brand.setBounds(60, 200, 300, 48);
        panel.add(brand);

        JPanel underline = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, UITheme.ACCENT_PURPLE,
                        getWidth(), 0, UITheme.ACCENT_CYAN);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 3, 3);
                g2.dispose();
            }
        };
        underline.setOpaque(false);
        underline.setBounds(130, 248, 160, 4);
        panel.add(underline);

        JLabel tag = new JLabel("<html><center>AI-Powered Car<br/>Decision Support System</center></html>",
                SwingConstants.CENTER);
        tag.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tag.setForeground(UITheme.TEXT_SECONDARY);
        tag.setBounds(50, 265, 320, 50);
        panel.add(tag);

        String[][] features = {
                {"🎯", "Personalised Recommendations"},
                {"📊", "Rich Data Visualisations"},
                {"📋", "Interactive Spreadsheet View"},
                {"💡", "Weighted DSS Scoring"},
        };
        int y = 350;
        for (String[] f : features) {
            JPanel pill = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4)) {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0xFF, 0xFF, 0xFF, 10));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.setColor(new Color(0x7C, 0x4D, 0xFF, 60));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                    g2.dispose();
                }
            };
            pill.setOpaque(false);
            pill.setBounds(55, y, 310, 36);

            JLabel ico = new JLabel(f[0]);
            ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
            JLabel lbl = new JLabel(f[1]);
            lbl.setFont(UITheme.FONT_BODY);
            lbl.setForeground(UITheme.TEXT_SECONDARY);
            pill.add(ico);
            pill.add(lbl);
            panel.add(pill);
            y += 44;
        }

        return panel;
    }

    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.fillGradient(g2, 0, 0, getWidth(), getHeight(),
                        UITheme.BG_PANEL, UITheme.BG_DARK);
                g2.dispose();
            }
        };
        outer.setOpaque(false);

        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                GradientPaint sheen = new GradientPaint(0, 0,
                        new Color(255, 255, 255, 12), 0, 60, new Color(255, 255, 255, 0));
                g2.setPaint(sheen);
                g2.fillRoundRect(0, 0, getWidth(), 60, 20, 20);

                GradientPaint topGlow = new GradientPaint(30, 0, UITheme.ACCENT_PURPLE,
                        getWidth() - 30, 0, UITheme.ACCENT_CYAN);
                g2.setPaint(topGlow);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawLine(30, 1, getWidth() - 30, 1);

                g2.setColor(UITheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

                g2.setColor(new Color(0, 0, 0, 50));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(3, 3, getWidth()-1, getHeight()-1, 20, 20);

                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBounds(50, 60, 340, 460);
        outer.add(card);

        JLabel welcome = new JLabel("Welcome back 👋");
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.TEXT_PRIMARY);
        welcome.setBounds(30, 32, 280, 30);
        card.add(welcome);

        JLabel sub = new JLabel("Sign in to your CarDSS account");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setBounds(30, 66, 280, 20);
        card.add(sub);

        JSeparator div = new JSeparator();
        div.setForeground(UITheme.SEPARATOR_CLR);
        div.setBounds(30, 96, 280, 2);
        card.add(div);

        JLabel uLbl = new JLabel("USERNAME");
        uLbl.setFont(UITheme.FONT_CAPTION);
        uLbl.setForeground(UITheme.ACCENT_PURPLE);
        uLbl.setBounds(30, 112, 280, 14);
        card.add(uLbl);

        usernameField = UITheme.styledTextField(20);
        usernameField.putClientProperty("placeholder", "Enter username");
        usernameField.setBounds(30, 130, 280, 40);
        card.add(usernameField);

        JLabel pLbl = new JLabel("PASSWORD");
        pLbl.setFont(UITheme.FONT_CAPTION);
        pLbl.setForeground(UITheme.ACCENT_PURPLE);
        pLbl.setBounds(30, 183, 280, 14);
        card.add(pLbl);

        passwordField = UITheme.styledPasswordField(20);
        passwordField.setBounds(30, 201, 280, 40);
        card.add(passwordField);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.ACCENT_RED);
        errorLabel.setBounds(30, 247, 280, 18);
        card.add(errorLabel);

        JButton loginBtn = UITheme.jPrimaryButton("Sign In  →");
        loginBtn.setBounds(30, 270, 280, 44);
        loginBtn.setPreferredSize(null);
        card.add(loginBtn);

        JPanel divRow = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(UITheme.SEPARATOR_CLR);
                g2.drawLine(0, getHeight()/2, 100, getHeight()/2);
                g2.drawLine(getWidth()-100, getHeight()/2, getWidth(), getHeight()/2);
                g2.setFont(UITheme.FONT_SMALL);
                g2.setColor(UITheme.TEXT_MUTED);
                FontMetrics fm = g2.getFontMetrics();
                String t = "OR";
                g2.drawString(t, (getWidth()-fm.stringWidth(t))/2, getHeight()/2+4);
                g2.dispose();
            }
        };
        divRow.setOpaque(false);
        divRow.setBounds(30, 322, 280, 24);
        card.add(divRow);

        JButton signupBtn = UITheme.jSecondaryButton("Create New Account");
        signupBtn.setBounds(30, 353, 280, 44);
        signupBtn.setPreferredSize(null);
        card.add(signupBtn);

        JLabel hint = new JLabel("Hint: admin / admin123", SwingConstants.CENTER);
        hint.setFont(UITheme.FONT_CAPTION);
        hint.setForeground(UITheme.TEXT_MUTED);
        hint.setBounds(30, 410, 280, 16);
        card.add(hint);

        JLabel ver = new JLabel("CarDSS v1.0  •  EGP Pricing", SwingConstants.CENTER);
        ver.setFont(UITheme.FONT_CAPTION);
        ver.setForeground(UITheme.TEXT_MUTED);
        ver.setBounds(30, 428, 280, 16);
        card.add(ver);

        loginBtn.addActionListener(e  -> handleLogin());
        passwordField.addActionListener(e -> handleLogin());
        signupBtn.addActionListener(e  -> new SignupPage(this).setVisible(true));

        return outer;
    }

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());
        String err  = AuthManager.login(user, pass);
        if (err != null) {
            errorLabel.setText("⚠  " + err);
            passwordField.setText("");
            return;
        }
        dispose();
        SwingUtilities.invokeLater(() -> new DashboardPage().setVisible(true));
    }
}
