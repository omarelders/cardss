package com.cardss.dashboard;

import com.cardss.auth.LoginPage;
import com.cardss.model.Car;
import com.cardss.util.AuthManager;
import com.cardss.util.UITheme;
import com.cardss.util.UserPreferences;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardPage extends JFrame {

    private List<Car> carDataset = new ArrayList<>();
    private UserPreferences userPreferences = new UserPreferences();

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private DataTablePage dataTablePage;
    private UserPreferencesPage prefPage;
    private RecommendationPage recPage;

    private static final String PAGE_HOME = "HOME";
    private static final String PAGE_TABLE = "TABLE";
    private static final String PAGE_PREFS = "PREFS";
    private static final String PAGE_REC = "REC";

    private JLabel statusBar;
    private JPanel backPanel;

    private JPanel chartsGrid;
    private JPanel statsStripContainer;
    private javax.swing.table.DefaultTableModel homeTableModel;
    private JLabel homeRowCountLabel;

    public DashboardPage() {
        setTitle("CarDSS — Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1024, 680));
        buildUI();
        loadSampleData();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        backPanel.setBackground(UITheme.BG_DARK);
        JButton backBtn = new JButton("← Back to Home");
        backBtn.setFont(UITheme.FONT_BODY);
        backBtn.setForeground(UITheme.TEXT_SECONDARY);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                backBtn.setForeground(UITheme.TEXT_PRIMARY);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                backBtn.setForeground(UITheme.TEXT_SECONDARY);
            }
        });
        backBtn.addActionListener(e -> navigateTo(PAGE_HOME));
        backPanel.add(backBtn);
        root.add(backPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.BG_DARK);
        root.add(contentPanel, BorderLayout.CENTER);

        dataTablePage = new DataTablePage(this);
        prefPage = new UserPreferencesPage(this);
        recPage = new RecommendationPage(this);

        contentPanel.add(buildHomePage(), PAGE_HOME);
        contentPanel.add(dataTablePage, PAGE_TABLE);
        contentPanel.add(prefPage, PAGE_PREFS);
        contentPanel.add(recPage, PAGE_REC);

        JPanel statusPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(UITheme.BG_SIDEBAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(UITheme.BORDER_COLOR);
                g2.drawLine(0, 0, getWidth(), 0);
                g2.dispose();
            }
        };
        statusPanel.setOpaque(false);
        statusPanel.setPreferredSize(new Dimension(0, 28));

        statusBar = new JLabel("  Ready");
        statusBar.setFont(UITheme.FONT_SMALL);
        statusBar.setForeground(UITheme.TEXT_MUTED);
        statusPanel.add(statusBar, BorderLayout.WEST);

        JLabel userPill = new JLabel("👤  " + AuthManager.getCurrentUser() + "  ");
        userPill.setFont(UITheme.FONT_SMALL);
        userPill.setForeground(UITheme.ACCENT_PURPLE);
        statusPanel.add(userPill, BorderLayout.EAST);

        root.add(statusPanel, BorderLayout.SOUTH);
        navigateTo(PAGE_HOME);
    }

    private JPanel buildHomePage() {
        JPanel homeRoot = new JPanel(new BorderLayout());
        homeRoot.setBackground(UITheme.BG_DARK);

        JPanel belowHero = new JPanel(new BorderLayout(0, 0));
        belowHero.setOpaque(false);

        JPanel topPart = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UITheme.fillGradient(g2, 0, 0, getWidth(), getHeight(),
                        UITheme.BG_DARK, new Color(0x0A, 0x0C, 0x18));
                g2.dispose();
            }
        };
        topPart.setOpaque(false);
        topPart.setPreferredSize(new Dimension(1080, 130));

        JPanel hero = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                UITheme.fillGradient(g2, 0, 0, getWidth(), getHeight(),
                        new Color(0x14, 0x10, 0x30), UITheme.BG_DARK);
                RadialGradientPaint orb = new RadialGradientPaint(
                        new Point(getWidth(), 0), 300,
                        new float[] { 0f, 1f },
                        new Color[] { new Color(0x7C, 0x4D, 0xFF, 40), new Color(0, 0, 0, 0) });
                g2.setPaint(orb);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        hero.setOpaque(false);
        hero.setBounds(0, 0, 2000, 120);

        JLabel title = new JLabel("Welcome to CarDSS");
        title.setFont(UITheme.FONT_HERO);
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setBounds(40, 24, 700, 44);
        hero.add(title);

        JLabel sub = new JLabel("Your Car Recommendation Decision Support System ");
        sub.setFont(UITheme.FONT_BODY);
        sub.setForeground(UITheme.TEXT_SECONDARY);
        sub.setBounds(40, 72, 800, 22);
        hero.add(sub);
        topPart.add(hero);

        Object[][] cards = {
                { "⚙️", "My Preferences", "Set DSS filters and weights", PAGE_PREFS, UITheme.GRAD_CYAN },
                { "🎯", "Recommendations", "Find your perfect car match", PAGE_REC, UITheme.GRAD_AMBER },
        };

        JPanel cardsRow = new JPanel(null);
        cardsRow.setOpaque(false);
        cardsRow.setPreferredSize(new Dimension(1080, 200));

        int x = 40;
        for (Object[] c : cards) {
            JPanel card = buildQuickCard((String) c[0], (String) c[1], (String) c[2], (String) c[3], (Color[]) c[4]);
            card.setBounds(x, 20, 240, 160);
            cardsRow.add(card);
            x += 260;
        }

        JPanel chartsHeader = new JPanel(new BorderLayout());
        chartsHeader.setOpaque(false);
        chartsHeader.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
        JLabel chartsTitle = UITheme.titleLabel("Analytics Overview");
        chartsHeader.add(chartsTitle, BorderLayout.WEST);

        chartsGrid = new JPanel(new GridLayout(0, 2, 24, 24));
        chartsGrid.setOpaque(false);
        chartsGrid.setBorder(BorderFactory.createEmptyBorder(20, 40, 60, 40));

        JPanel chartsSection = new JPanel(new BorderLayout());
        chartsSection.setOpaque(false);
        chartsSection.add(chartsHeader, BorderLayout.NORTH);
        chartsSection.add(chartsGrid, BorderLayout.CENTER);

        JPanel cardsAndCharts = new JPanel(new BorderLayout(0, 0));
        cardsAndCharts.setOpaque(false);
        cardsAndCharts.add(cardsRow, BorderLayout.NORTH);
        cardsAndCharts.add(chartsSection, BorderLayout.CENTER);

        JPanel scrollContent = new JPanel(new BorderLayout(0, 0));
        scrollContent.setOpaque(false);
        scrollContent.add(topPart, BorderLayout.NORTH);
        belowHero.add(buildHomeTable(), BorderLayout.NORTH);
        belowHero.add(cardsAndCharts, BorderLayout.CENTER);
        scrollContent.add(belowHero, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(scrollContent);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        homeRoot.add(scroll, BorderLayout.CENTER);
        return homeRoot;
    }

    private JPanel buildHomeTable() {
        String[] cols = { "#", "Name", "Year", "Price (EGP)", "Fuel", "Transmission", "Mileage", "Engine (CC)",
                "Seats" };
        homeTableModel = new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(homeTableModel);
        table.setBackground(UITheme.BG_PANEL);
        table.setForeground(UITheme.TEXT_PRIMARY);
        table.setFont(UITheme.FONT_BODY);
        table.setRowHeight(26);
        table.setGridColor(UITheme.SEPARATOR_CLR);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(8, 2));
        table.setSelectionBackground(UITheme.ACCENT_BLUE);
        table.setSelectionForeground(Color.WHITE);
        table.setEnabled(false);

        table.getTableHeader().setBackground(UITheme.BG_SIDEBAR);
        table.getTableHeader().setForeground(UITheme.ACCENT_BLUE);
        table.getTableHeader().setFont(UITheme.FONT_LABEL);
        table.getTableHeader().setPreferredSize(new Dimension(0, 30));
        table.getTableHeader().setReorderingAllowed(false);

        int[] widths = { 36, 220, 50, 120, 70, 100, 80, 90, 50 };
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        javax.swing.table.TableCellRenderer renderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(row % 2 == 0 ? UITheme.BG_PANEL : UITheme.BG_TABLE_ALT);
                setForeground(col == 3 ? UITheme.ACCENT_GREEN : UITheme.TEXT_PRIMARY);
                setFont(UITheme.FONT_BODY);
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                setHorizontalAlignment(col == 0 || col >= 2 ? RIGHT : LEFT);
                return this;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        tableScroll.setBackground(UITheme.BG_PANEL);
        tableScroll.getViewport().setBackground(UITheme.BG_PANEL);
        tableScroll.setPreferredSize(new Dimension(0, 220));

        JPanel labelBar = new JPanel(new BorderLayout());
        labelBar.setBackground(UITheme.BG_PANEL);
        labelBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));

        JLabel tableTitle = UITheme.headingLabel("Dataset Preview");
        labelBar.add(tableTitle, BorderLayout.WEST);

        homeRowCountLabel = new JLabel("Loading…");
        homeRowCountLabel.setFont(UITheme.FONT_SMALL);
        homeRowCountLabel.setForeground(UITheme.TEXT_MUTED);
        labelBar.add(homeRowCountLabel, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BG_PANEL);
        wrapper.add(labelBar, BorderLayout.NORTH);
        wrapper.add(tableScroll, BorderLayout.CENTER);
        return wrapper;
    }

    private void refreshHomeTable() {
        if (homeTableModel == null)
            return;
        homeTableModel.setRowCount(0);
        List<Car> cars = carDataset;
        int limit = Math.min(50, cars.size());
        for (int i = 0; i < limit; i++) {
            Car c = cars.get(i);
            homeTableModel.addRow(new Object[] {
                    i + 1,
                    c.getName(),
                    c.getYear(),
                    UITheme.formatPrice(c.getSellingPrice()),
                    c.getFuel(),
                    c.getTransmission(),
                    c.getMileage() > 0 ? String.format("%.1f kmpl", c.getMileage()) : "—",
                    c.getEngine() > 0 ? (int) c.getEngine() + " CC" : "—",
                    c.getSeats()
            });
        }
        if (homeRowCountLabel != null) {
            homeRowCountLabel.setText(cars.size() + " cars loaded");
        }
    }

    private JPanel buildQuickCard(String icon, String title, String desc, String page, Color[] grad) {
        JPanel card = new JPanel(null) {
            boolean hovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? UITheme.BG_CARD2 : UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                UITheme.fillGradientRound(g2, 0, 0, getWidth(), 6, 16, grad[0], grad[1]);
                g2.setColor(hovered ? grad[0] : UITheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true;
                        repaint();
                    }

                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false;
                        repaint();
                    }

                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        navigateTo(page);
                    }
                });
            }
        };
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setOpaque(false);

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        ico.setBounds(18, 20, 44, 40);
        card.add(ico);

        JLabel ttl = new JLabel(title);
        ttl.setFont(UITheme.FONT_HEADING);
        ttl.setForeground(UITheme.TEXT_PRIMARY);
        ttl.setBounds(18, 68, 210, 22);
        card.add(ttl);

        JLabel dsc = new JLabel("<html>" + desc + "</html>");
        dsc.setFont(UITheme.FONT_SMALL);
        dsc.setForeground(UITheme.TEXT_SECONDARY);
        dsc.setBounds(18, 92, 210, 36);
        card.add(dsc);

        JLabel arrow = new JLabel("→");
        arrow.setFont(UITheme.FONT_HEADING);
        arrow.setForeground(grad[0]);
        arrow.setBounds(200, 128, 30, 22);
        card.add(arrow);

        return card;
    }

    public void refreshHomeCharts() {
        if (chartsGrid != null) {
            chartsGrid.removeAll();
            List<Car> cars = getCarDataset();
            if (cars != null && !cars.isEmpty()) {
                chartsGrid.add(buildPriceDistribution(cars));
                chartsGrid.add(buildFuelPie(cars));
                chartsGrid.add(buildTransmissionPie(cars));
                chartsGrid.add(buildSeatsPie(cars));
                chartsGrid.add(buildEngineScatter(cars));
                chartsGrid.add(buildMileageScatter(cars));
            }
            chartsGrid.revalidate();
            chartsGrid.repaint();
        }
    }

    private JPanel buildPriceDistribution(List<Car> cars) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[] buckets = { 100000, 200000, 350000, 500000, 700000, 900000, 1200000, Integer.MAX_VALUE };
        String[] labels = { "<100K", "100-200K", "200-350K", "350-500K", "500-700K", "700-900K", "900K-1.2M", ">1.2M" };
        int[] counts = new int[labels.length];

        for (Car c : cars) {
            double p = c.getSellingPrice();
            for (int i = 0; i < buckets.length; i++) {
                if (p < buckets[i]) {
                    counts[i]++;
                    break;
                }
            }
        }
        for (int i = 0; i < labels.length; i++) {
            dataset.addValue(counts[i], "Cars", labels[i]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Price Distribution", "Price Range (EGP)", "Number of Cars",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        applyDarkTheme(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, UITheme.ACCENT_BLUE);
        renderer.setMaximumBarWidth(0.06);

        return wrapChart(chart);
    }

    private JPanel buildFuelPie(List<Car> cars) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        cars.stream()
                .collect(Collectors.groupingBy(Car::getFuel, Collectors.counting()))
                .forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Fuel Type Distribution", dataset, true, true, false);

        applyDarkTheme(chart);
        PiePlot plot = (PiePlot) chart.getPlot();
        Color[] colors = { UITheme.ACCENT_BLUE, UITheme.ACCENT_GREEN, UITheme.ACCENT_AMBER,
                UITheme.ACCENT_CYAN, UITheme.ACCENT_RED, new Color(0xAA, 0x66, 0xFF) };
        int ci = 0;
        for (Comparable<?> key : dataset.getKeys()) {
            plot.setSectionPaint((String) key, colors[ci++ % colors.length]);
        }
        return wrapChart(chart);
    }

    private JPanel buildTransmissionPie(List<Car> cars) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        cars.stream()
                .collect(Collectors.groupingBy(Car::getTransmission, Collectors.counting()))
                .forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Transmission Split", dataset, true, true, false);

        applyDarkTheme(chart);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Manual", UITheme.ACCENT_BLUE);
        plot.setSectionPaint("Automatic", UITheme.ACCENT_GREEN);
        return wrapChart(chart);
    }

    private JPanel buildSeatsPie(List<Car> cars) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        cars.stream()
                .collect(Collectors.groupingBy(c -> c.getSeats() + " Seats", Collectors.counting()))
                .forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Seat Capacity", dataset, true, true, false);

        applyDarkTheme(chart);
        PiePlot plot = (PiePlot) chart.getPlot();
        Color[] colors = { UITheme.ACCENT_PURPLE, UITheme.ACCENT_CYAN, UITheme.ACCENT_GREEN,
                UITheme.ACCENT_AMBER, UITheme.ACCENT_BLUE, UITheme.ACCENT_RED };
        int ci = 0;
        for (Comparable<?> key : dataset.getKeys()) {
            plot.setSectionPaint((String) key, colors[ci++ % colors.length]);
        }
        return wrapChart(chart);
    }

    private JPanel buildEngineScatter(List<Car> cars) {
        XYSeries series = new XYSeries("Cars");
        for (Car c : cars) {
            if (c.getEngine() > 0 && c.getSellingPrice() > 0) {
                series.add(c.getEngine(), c.getSellingPrice() / 1000.0);
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Engine Size vs Price", "Engine (CC)", "Price (EGP Thousands)", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        applyDarkTheme(chart);
        XYPlot plot = chart.getXYPlot();
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotWidth(5);
        renderer.setDotHeight(5);
        renderer.setSeriesPaint(0, UITheme.ACCENT_CYAN);
        plot.setRenderer(renderer);
        return wrapChart(chart);
    }

    private JPanel buildMileageScatter(List<Car> cars) {
        XYSeries petrol = new XYSeries("Petrol");
        XYSeries diesel = new XYSeries("Diesel");
        XYSeries other = new XYSeries("Other");

        for (Car c : cars) {
            if (c.getMileage() > 0 && c.getSellingPrice() > 0) {
                double price = c.getSellingPrice() / 1000.0;
                switch (c.getFuel().toLowerCase()) {
                    case "petrol":
                        petrol.add(c.getMileage(), price);
                        break;
                    case "diesel":
                        diesel.add(c.getMileage(), price);
                        break;
                    default:
                        other.add(c.getMileage(), price);
                        break;
                }
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(petrol);
        dataset.addSeries(diesel);
        dataset.addSeries(other);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Mileage vs Price", "Mileage (kmpl)", "Price (EGP Thousands)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        applyDarkTheme(chart);
        XYPlot plot = chart.getXYPlot();
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotWidth(5);
        renderer.setDotHeight(5);
        renderer.setSeriesPaint(0, UITheme.ACCENT_AMBER);
        renderer.setSeriesPaint(1, UITheme.ACCENT_BLUE);
        renderer.setSeriesPaint(2, UITheme.ACCENT_GREEN);
        plot.setRenderer(renderer);
        return wrapChart(chart);
    }

    private JPanel wrapChart(JFreeChart chart) {
        ChartPanel cp = new ChartPanel(chart);
        cp.setBackground(UITheme.BG_PANEL);
        cp.setMouseWheelEnabled(false);
        cp.setDomainZoomable(false);
        cp.setRangeZoomable(false);
        cp.setPreferredSize(new Dimension(480, 320));

        cp.setMinimumDrawWidth(480);
        cp.setMaximumDrawWidth(480);
        cp.setMinimumDrawHeight(320);
        cp.setMaximumDrawHeight(320);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.BG_PANEL);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        wrapper.add(cp, BorderLayout.CENTER);

        JPanel fixedContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        fixedContainer.setOpaque(false);
        fixedContainer.add(wrapper);

        return fixedContainer;
    }

    private void applyDarkTheme(JFreeChart chart) {
        chart.setBackgroundPaint(UITheme.BG_PANEL);
        chart.getTitle().setPaint(UITheme.TEXT_PRIMARY);
        chart.getTitle().setFont(UITheme.FONT_HEADING);

        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(UITheme.BG_PANEL);
            chart.getLegend().setItemPaint(UITheme.TEXT_SECONDARY);
        }

        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(UITheme.BG_PANEL);
        plot.setOutlinePaint(UITheme.BORDER_COLOR);

        if (plot instanceof CategoryPlot) {
            CategoryPlot cp = (CategoryPlot) plot;
            cp.setDomainGridlinePaint(UITheme.SEPARATOR_CLR);
            cp.setRangeGridlinePaint(UITheme.SEPARATOR_CLR);
            cp.getDomainAxis().setLabelPaint(UITheme.TEXT_SECONDARY);
            cp.getDomainAxis().setTickLabelPaint(UITheme.TEXT_SECONDARY);
            cp.getRangeAxis().setLabelPaint(UITheme.TEXT_SECONDARY);
            cp.getRangeAxis().setTickLabelPaint(UITheme.TEXT_SECONDARY);
        }
        if (plot instanceof XYPlot) {
            XYPlot xyp = (XYPlot) plot;
            xyp.setDomainGridlinePaint(UITheme.SEPARATOR_CLR);
            xyp.setRangeGridlinePaint(UITheme.SEPARATOR_CLR);
            xyp.getDomainAxis().setLabelPaint(UITheme.TEXT_SECONDARY);
            xyp.getDomainAxis().setTickLabelPaint(UITheme.TEXT_SECONDARY);
            xyp.getRangeAxis().setLabelPaint(UITheme.TEXT_SECONDARY);
            xyp.getRangeAxis().setTickLabelPaint(UITheme.TEXT_SECONDARY);
        }
        if (plot instanceof PiePlot) {
            PiePlot pp = (PiePlot) plot;
            pp.setLabelPaint(UITheme.TEXT_SECONDARY);
            pp.setLabelFont(UITheme.FONT_SMALL);
            pp.setSectionOutlinesVisible(false);
            pp.setShadowPaint(null);
            pp.setBackgroundPaint(UITheme.BG_PANEL);
        }
    }

    public void navigateTo(String page) {
        cardLayout.show(contentPanel, page);
        if (PAGE_TABLE.equals(page))
            dataTablePage.refresh();
        if (PAGE_REC.equals(page))
            recPage.refresh();

        if (backPanel != null) {
            backPanel.setVisible(!PAGE_HOME.equals(page));
        }

        setStatus("Showing: " + pageTitle(page));
    }

    private String pageTitle(String p) {
        switch (p) {
            case PAGE_HOME:
                return "Home";
            case PAGE_TABLE:
                return "Data Table";
            case PAGE_PREFS:
                return "My Preferences";
            case PAGE_REC:
                return "Recommendations";
            default:
                return p;
        }
    }

    public List<Car> getCarDataset() {
        return carDataset;
    }

    public void setCarDataset(List<Car> d) {
        carDataset = d;
        setStatus(d.size() + " cars loaded.");
        refreshHomeCharts();
        refreshHomeTable();
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public void setStatus(String msg) {
        if (statusBar != null)
            statusBar.setText("  " + msg + "  |  " + AuthManager.getCurrentUser());
    }

    private String countUnique(String f) {
        if ("fuel".equals(f))
            return String.valueOf(carDataset.stream().map(Car::getFuel).distinct().count());
        if ("trans".equals(f))
            return String.valueOf(carDataset.stream().map(Car::getTransmission).distinct().count());
        return "—";
    }

    private void loadSampleData() {
        try (InputStream is = getClass().getResourceAsStream("/sample_cars.csv");
                java.io.BufferedReader br = new java.io.BufferedReader(
                        new java.io.InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8))) {

            List<Car> cars = new java.util.ArrayList<>();
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] cols = line.split(",", -1);
                if (cols.length < 13)
                    continue;

                try {
                    Car car = new Car();
                    car.setName(cols[0].trim().replaceAll("^\"|\"$", ""));
                    car.setYear(Integer.parseInt(cols[1].trim()));
                    car.setSellingPrice(Double.parseDouble(cols[2].trim()));
                    car.setKmDriven(Double.parseDouble(cols[3].trim()));
                    car.setFuel(normaliseFuel(cols[4].trim()));
                    car.setSellerType(cols[5].trim());
                    car.setTransmission(normaliseTrans(cols[6].trim()));
                    car.setOwner(cols[7].trim());
                    car.setMileage(parseNum(cols[8]));
                    car.setEngine(parseNum(cols[9]));
                    car.setMaxPower(parseNum(cols[10]));
                    car.setTorque(cols[11].trim());
                    car.setSeats((int) parseNum(cols[12]));

                    if (!car.getName().isEmpty() && car.getSellingPrice() > 0 && car.getSeats() > 0)
                        cars.add(car);
                } catch (Exception ignored) {
                }
            }

            setCarDataset(cars);
            setStatus(cars.size() + " cars loaded from built-in dataset.");

        } catch (Exception e) {
            setStatus("Could not load sample data: " + e.getMessage());
        }
    }

    private static double parseNum(String raw) {
        if (raw == null || raw.isBlank())
            return 0;
        StringBuilder num = new StringBuilder();
        boolean dot = false;
        for (char ch : raw.trim().toCharArray()) {
            if (Character.isDigit(ch))
                num.append(ch);
            else if (ch == '.' && !dot) {
                num.append(ch);
                dot = true;
            } else if (num.length() > 0)
                break;
        }
        try {
            return num.length() > 0 ? Double.parseDouble(num.toString()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String normaliseFuel(String raw) {
        String s = raw.toLowerCase();
        if (s.contains("petrol"))
            return Car.FUEL_PETROL;
        if (s.contains("diesel"))
            return Car.FUEL_DIESEL;
        if (s.contains("cng"))
            return Car.FUEL_CNG;
        if (s.contains("electric") || s.equals("ev"))
            return Car.FUEL_ELECTRIC;
        if (s.contains("lpg"))
            return Car.FUEL_LPG;
        if (s.contains("hybrid"))
            return Car.FUEL_HYBRID;
        return Car.FUEL_UNKNOWN;
    }

    private static String normaliseTrans(String raw) {
        String s = raw.toLowerCase();
        if (s.contains("manual") || s.equals("mt"))
            return Car.TRANS_MANUAL;
        if (s.contains("automatic") || s.equals("at") || s.equals("amt")
                || s.contains("cvt") || s.contains("dct"))
            return Car.TRANS_AUTOMATIC;
        return Car.TRANS_UNKNOWN;
    }
}
