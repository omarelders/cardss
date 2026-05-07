package com.cardss.dashboard;

import com.cardss.model.Car;
import com.cardss.service.RecommendationEngine;
import com.cardss.util.UITheme;
import com.cardss.util.UserPreferences;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecommendationPage extends JPanel {

    private final DashboardPage dashboard;
    private JPanel cardsPanel;

    public RecommendationPage(DashboardPage dashboard) {
        this.dashboard = dashboard;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(null);
        header.setBackground(UITheme.BG_PANEL);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 80));

        JLabel title = UITheme.titleLabel("Recommendations");
        title.setBounds(24, 16, 400, 30);
        header.add(title);

        JLabel sub = UITheme.bodyLabel("Top car matches based on your preferences and DSS weighted scoring.");
        sub.setBounds(24, 48, 700, 20);
        header.add(sub);

        JButton refreshBtn = UITheme.jPrimaryButton("↺  Refresh");
        refreshBtn.setBounds(820, 22, 140, 36);
        refreshBtn.setPreferredSize(null);
        refreshBtn.addActionListener(e -> refresh());
        header.add(refreshBtn);

        JButton prefBtn = UITheme.jSecondaryButton("Edit Preferences");
        prefBtn.setBounds(660, 22, 156, 36);
        prefBtn.setPreferredSize(null);
        prefBtn.addActionListener(e -> dashboard.navigateTo("PREFS"));
        header.add(prefBtn);

        add(header, BorderLayout.NORTH);

        JPanel centerArea = new JPanel(new BorderLayout(0, 16));
        centerArea.setBackground(UITheme.BG_DARK);
        centerArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        cardsPanel.setBackground(UITheme.BG_DARK);

        JScrollPane cardsScroll = new JScrollPane(cardsPanel);
        cardsScroll.setBackground(UITheme.BG_DARK);
        cardsScroll.getViewport().setBackground(UITheme.BG_DARK);
        cardsScroll.setBorder(null);
        cardsScroll.getVerticalScrollBar().setUnitIncrement(16);
        centerArea.add(cardsScroll, BorderLayout.CENTER);

        add(centerArea, BorderLayout.CENTER);
    }

    public void refresh() {
        List<Car> cars = dashboard.getCarDataset();
        UserPreferences prefs = dashboard.getUserPreferences();

        if (cars.isEmpty()) {
            showEmptyState();
            return;
        }

        List<Car> results = RecommendationEngine.recommend(cars, prefs, 5);
        renderCards(results, prefs);
    }

    private void renderCards(List<Car> results, UserPreferences prefs) {
        cardsPanel.removeAll();

        if (results.isEmpty()) {
            showEmptyState();
            return;
        }

        JPanel prefChip = buildPrefChip(prefs);
        prefChip.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        prefChip.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardsPanel.add(prefChip);
        cardsPanel.add(Box.createVerticalStrut(16));

        JLabel rankHeader = new JLabel("Top " + results.size() + " Recommended Cars");
        rankHeader.setFont(UITheme.FONT_HEADING);
        rankHeader.setForeground(UITheme.TEXT_PRIMARY);
        rankHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardsPanel.add(rankHeader);
        cardsPanel.add(Box.createVerticalStrut(12));

        for (int i = 0; i < results.size(); i++) {
            JPanel card = buildCarCard(results.get(i), i + 1);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardsPanel.add(card);
            cardsPanel.add(Box.createVerticalStrut(12));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    private JPanel buildPrefChip(UserPreferences prefs) {
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        chip.setBackground(UITheme.BG_CARD);
        chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 4, 2, 4)));

        addChip(chip, "Budget", UITheme.formatPrice(prefs.getBudget()));
        addChip(chip, "Fuel", prefs.getFuelType());
        addChip(chip, "Trans", prefs.getTransmission());
        addChip(chip, "Min Seats", String.valueOf(prefs.getMinSeats()));
        return chip;
    }

    private void addChip(JPanel parent, String label, String value) {
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        chip.setBackground(new Color(0x4A, 0x9E, 0xFF, 20));
        chip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.ACCENT_BLUE, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        JLabel l = new JLabel(label + ": " + value);
        l.setFont(UITheme.FONT_SMALL);
        l.setForeground(UITheme.ACCENT_BLUE);
        chip.add(l);
        parent.add(chip);
    }

    private JPanel buildCarCard(Car car, int rank) {
        JPanel card = new JPanel(new BorderLayout(0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                Color rankColor = rank == 1 ? UITheme.ACCENT_AMBER
                        : rank == 2 ? new Color(0xC0, 0xC0, 0xC0)
                                : rank == 3 ? new Color(0xCD, 0x7F, 0x32)
                                        : UITheme.BORDER_COLOR;
                g2.setColor(rankColor);
                g2.fillRoundRect(0, 0, 5, getHeight(), 5, 5);
                g2.setColor(UITheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }
        };
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel rankBadge = new JLabel("#" + rank);
        rankBadge.setFont(UITheme.FONT_HERO);
        rankBadge.setForeground(rank == 1 ? UITheme.ACCENT_AMBER : UITheme.TEXT_MUTED);
        rankBadge.setPreferredSize(new Dimension(60, 60));
        rankBadge.setVerticalAlignment(SwingConstants.CENTER);
        card.add(rankBadge, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JLabel name = new JLabel(car.getName());
        name.setFont(UITheme.FONT_HEADING);
        name.setForeground(UITheme.TEXT_PRIMARY);
        info.add(name);
        info.add(Box.createVerticalStrut(2));

        JLabel yearFuel = new JLabel(car.getYear() + "  •  " + car.getFuel() + "  •  " + car.getTransmission());
        yearFuel.setFont(UITheme.FONT_SMALL);
        yearFuel.setForeground(UITheme.TEXT_SECONDARY);
        info.add(yearFuel);
        info.add(Box.createVerticalStrut(4));

        JLabel price = new JLabel(UITheme.formatPrice(car.getSellingPrice()));
        price.setFont(UITheme.FONT_HEADING);
        price.setForeground(UITheme.ACCENT_GREEN);
        info.add(price);
        info.add(Box.createVerticalStrut(4));

        JLabel stats = new JLabel(String.format("%.1f kmpl  •  %.0f CC  •  %.0f bhp  •  %d seats",
                car.getMileage(), car.getEngine(), car.getMaxPower(), car.getSeats()));
        stats.setFont(UITheme.FONT_SMALL);
        stats.setForeground(UITheme.TEXT_SECONDARY);
        info.add(stats);
        info.add(Box.createVerticalStrut(6));

        card.add(info, BorderLayout.CENTER);
        return card;
    }

    private void showEmptyState() {
        cardsPanel.removeAll();
        JLabel empty = new JLabel("No cars found. Import a dataset and set your preferences.");
        empty.setFont(UITheme.FONT_HEADING);
        empty.setForeground(UITheme.TEXT_MUTED);
        empty.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardsPanel.add(Box.createVerticalStrut(80));
        cardsPanel.add(empty);
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}
