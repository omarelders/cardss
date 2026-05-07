package com.cardss.util;

import java.awt.*;

public class UITheme {

    public static final String CURRENCY     = "EGP";
    public static final String CURRENCY_SYM = "EGP ";

    public static String formatPrice(double amount) {
        return String.format("EGP %,.0f", amount);
    }

    public static final Color BG_DARK      = new Color(0x0C, 0x0E, 0x1A);
    public static final Color BG_PANEL     = new Color(0x12, 0x15, 0x24);
    public static final Color BG_SIDEBAR   = new Color(0x0E, 0x10, 0x1E);
    public static final Color BG_CARD      = new Color(0x17, 0x1B, 0x2E);
    public static final Color BG_CARD2     = new Color(0x1D, 0x22, 0x38);
    public static final Color BG_INPUT     = new Color(0x14, 0x18, 0x28);
    public static final Color BG_TABLE_ALT = new Color(0x15, 0x19, 0x2A);

    public static final Color GRAD_TOP     = new Color(0x0C, 0x0E, 0x1A);
    public static final Color GRAD_BOT     = new Color(0x0A, 0x0C, 0x18);

    public static final Color ACCENT_PURPLE = new Color(0x7C, 0x4D, 0xFF);
    public static final Color ACCENT_BLUE   = new Color(0x4A, 0x9E, 0xFF);
    public static final Color ACCENT_CYAN   = new Color(0x00, 0xD4, 0xFF);
    public static final Color ACCENT_GREEN  = new Color(0x00, 0xE5, 0xA0);
    public static final Color ACCENT_AMBER  = new Color(0xFF, 0xB8, 0x42);
    public static final Color ACCENT_PINK   = new Color(0xFF, 0x4F, 0xA6);
    public static final Color ACCENT_RED    = new Color(0xFF, 0x4D, 0x6A);

    public static final Color[] GRAD_PURPLE = {new Color(0x7C,0x4D,0xFF,60), new Color(0x4A,0x9E,0xFF,20)};
    public static final Color[] GRAD_CYAN   = {new Color(0x00,0xD4,0xFF,60), new Color(0x00,0xE5,0xA0,20)};
    public static final Color[] GRAD_AMBER  = {new Color(0xFF,0xB8,0x42,60), new Color(0xFF,0x4F,0xA6,20)};
    public static final Color[] GRAD_GREEN  = {new Color(0x00,0xE5,0xA0,60), new Color(0x4A,0x9E,0xFF,20)};

    public static final Color TEXT_PRIMARY   = new Color(0xF2, 0xF4, 0xFF);
    public static final Color TEXT_SECONDARY = new Color(0x8A, 0x96, 0xC0);
    public static final Color TEXT_MUTED     = new Color(0x4E, 0x5A, 0x80);

    public static final Color BORDER_COLOR  = new Color(0x27, 0x2E, 0x50);
    public static final Color BORDER_GLOW   = new Color(0x7C, 0x4D, 0xFF, 80);
    public static final Color SEPARATOR_CLR = new Color(0x1E, 0x24, 0x3E);

    public static final Font FONT_HERO    = new Font("Segoe UI", Font.BOLD,  30);
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD,  16);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_LABEL   = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_MONO    = new Font("Consolas",  Font.PLAIN, 12);
    public static final Font FONT_CAPTION = new Font("Segoe UI", Font.PLAIN, 10);

    public static void fillGradient(Graphics2D g2, int x, int y, int w, int h,
                                    Color top, Color bottom) {
        g2.setPaint(new GradientPaint(x, y, top, x, y + h, bottom));
        g2.fillRect(x, y, w, h);
    }

    public static void fillGradientRound(Graphics2D g2, int x, int y, int w, int h,
                                         int arc, Color c1, Color c2) {
        g2.setPaint(new GradientPaint(x, y, c1, x + w, y + h, c2));
        g2.fillRoundRect(x, y, w, h, arc, arc);
    }

    public static javax.swing.JButton jPrimaryButton(String text) {
        javax.swing.JButton b = new javax.swing.JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = getModel().isPressed() ? ACCENT_PURPLE.darker()
                         : getModel().isRollover() ? new Color(0x8A, 0x5F, 0xFF)
                         : ACCENT_PURPLE;
                Color c2 = getModel().isRollover() ? ACCENT_CYAN : ACCENT_BLUE;
                fillGradientRound(g2, 0, 0, getWidth(), getHeight(), 10, c1, c2);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 10, 10);
                g2.setColor(TEXT_PRIMARY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setFont(FONT_LABEL);
        b.setForeground(TEXT_PRIMARY);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 40));
        return b;
    }

    public static javax.swing.JButton jSecondaryButton(String text) {
        javax.swing.JButton b = new javax.swing.JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean hov = getModel().isRollover();
                g2.setColor(hov ? new Color(0x7C, 0x4D, 0xFF, 25) : BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(hov ? ACCENT_PURPLE : BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2.setColor(hov ? ACCENT_PURPLE : TEXT_SECONDARY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setFont(FONT_LABEL);
        b.setForeground(TEXT_SECONDARY);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 40));
        return b;
    }

    public static javax.swing.JButton jDangerButton(String text) {
        javax.swing.JButton b = new javax.swing.JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isRollover() ? ACCENT_RED.brighter() : ACCENT_RED;
                fillGradientRound(g2, 0, 0, getWidth(), getHeight(), 10,
                        c, new Color(c.getRed(), c.getGreen()/2, c.getBlue()/2));
                g2.setColor(new Color(255,255,255,15));
                g2.fillRoundRect(0, 0, getWidth(), getHeight()/2, 10, 10);
                g2.setColor(TEXT_PRIMARY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setFont(FONT_LABEL);
        b.setForeground(TEXT_PRIMARY);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 38));
        return b;
    }

    public static javax.swing.JTextField styledTextField(int columns) {
        javax.swing.JTextField f = new javax.swing.JTextField(columns);
        f.setBackground(BG_INPUT);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT_PURPLE);
        f.setFont(FONT_BODY);
        f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(7, 12, 7, 12)));
        return f;
    }

    public static javax.swing.JPasswordField styledPasswordField(int columns) {
        javax.swing.JPasswordField f = new javax.swing.JPasswordField(columns);
        f.setBackground(BG_INPUT);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT_PURPLE);
        f.setFont(FONT_BODY);
        f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1),
                javax.swing.BorderFactory.createEmptyBorder(7, 12, 7, 12)));
        return f;
    }

    public static javax.swing.JComboBox<String> styledComboBox(String[] items) {
        javax.swing.JComboBox<String> cb = new javax.swing.JComboBox<>(items);
        cb.setBackground(BG_INPUT);
        cb.setForeground(TEXT_PRIMARY);
        cb.setFont(FONT_BODY);
        cb.setBorder(javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1));
        return cb;
    }

    public static javax.swing.JLabel titleLabel(String text) {
        javax.swing.JLabel l = new javax.swing.JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static javax.swing.JLabel headingLabel(String text) {
        javax.swing.JLabel l = new javax.swing.JLabel(text);
        l.setFont(FONT_HEADING);
        l.setForeground(TEXT_PRIMARY);
        return l;
    }

    public static javax.swing.JLabel bodyLabel(String text) {
        javax.swing.JLabel l = new javax.swing.JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_SECONDARY);
        return l;
    }

    public static void applyGlobalDefaults() {
        javax.swing.UIManager.put("Panel.background",             BG_DARK);
        javax.swing.UIManager.put("OptionPane.background",        BG_PANEL);
        javax.swing.UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        javax.swing.UIManager.put("OptionPane.messageFont",       FONT_BODY);
        javax.swing.UIManager.put("Button.background",            ACCENT_PURPLE);
        javax.swing.UIManager.put("Button.foreground",            TEXT_PRIMARY);
        javax.swing.UIManager.put("TextField.background",         BG_INPUT);
        javax.swing.UIManager.put("TextField.foreground",         TEXT_PRIMARY);
        javax.swing.UIManager.put("TextField.caretForeground",    ACCENT_PURPLE);
        javax.swing.UIManager.put("PasswordField.background",     BG_INPUT);
        javax.swing.UIManager.put("PasswordField.foreground",     TEXT_PRIMARY);
        javax.swing.UIManager.put("ComboBox.background",          BG_INPUT);
        javax.swing.UIManager.put("ComboBox.foreground",          TEXT_PRIMARY);
        javax.swing.UIManager.put("Table.background",             BG_PANEL);
        javax.swing.UIManager.put("Table.foreground",             TEXT_PRIMARY);
        javax.swing.UIManager.put("Table.gridColor",              SEPARATOR_CLR);
        javax.swing.UIManager.put("Table.selectionBackground",    ACCENT_PURPLE);
        javax.swing.UIManager.put("Table.selectionForeground",    Color.WHITE);
        javax.swing.UIManager.put("TableHeader.background",       BG_SIDEBAR);
        javax.swing.UIManager.put("TableHeader.foreground",       ACCENT_PURPLE);
        javax.swing.UIManager.put("TableHeader.font",             FONT_LABEL);
        javax.swing.UIManager.put("ScrollPane.background",        BG_DARK);
        javax.swing.UIManager.put("Viewport.background",          BG_DARK);
        javax.swing.UIManager.put("TabbedPane.background",        BG_PANEL);
        javax.swing.UIManager.put("TabbedPane.foreground",        TEXT_SECONDARY);
        javax.swing.UIManager.put("TabbedPane.selected",          BG_CARD2);
        javax.swing.UIManager.put("TabbedPane.selectedForeground",ACCENT_PURPLE);
        javax.swing.UIManager.put("Slider.background",            BG_DARK);
        javax.swing.UIManager.put("Slider.foreground",            ACCENT_PURPLE);
        javax.swing.UIManager.put("Spinner.background",           BG_INPUT);
        javax.swing.UIManager.put("Spinner.foreground",           TEXT_PRIMARY);
        javax.swing.UIManager.put("ProgressBar.background",       BG_CARD);
        javax.swing.UIManager.put("ProgressBar.foreground",       ACCENT_PURPLE);
        javax.swing.UIManager.put("ProgressBar.selectionBackground", TEXT_PRIMARY);
        javax.swing.UIManager.put("ProgressBar.selectionForeground", TEXT_PRIMARY);
        javax.swing.UIManager.put("Label.foreground",             TEXT_PRIMARY);
        javax.swing.UIManager.put("CheckBox.background",          BG_DARK);
        javax.swing.UIManager.put("CheckBox.foreground",          TEXT_PRIMARY);
        javax.swing.UIManager.put("RadioButton.background",       BG_DARK);
        javax.swing.UIManager.put("RadioButton.foreground",       TEXT_PRIMARY);
        javax.swing.UIManager.put("Separator.foreground",         BORDER_COLOR);
        javax.swing.UIManager.put("ToolTip.background",           BG_CARD2);
        javax.swing.UIManager.put("ToolTip.foreground",           TEXT_PRIMARY);
        javax.swing.UIManager.put("ToolTip.border",
                javax.swing.BorderFactory.createLineBorder(BORDER_COLOR));
    }
}
