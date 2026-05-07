package com.cardss.dashboard;

import com.cardss.model.Car;
import com.cardss.model.CarTableModel;
import com.cardss.util.UITheme;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataTablePage extends JPanel {

    private final DashboardPage dashboard;

    private JTable table;
    private CarTableModel carModel;
    private TableRowSorter<CarTableModel> sorter;

    private JTextField searchField;
    private JComboBox<String> fuelFilter;
    private JComboBox<String> transFilter;
    private JLabel recordCount;

    public DataTablePage(DashboardPage dashboard) {
        this.dashboard = dashboard;
        setBackground(UITheme.BG_DARK);
        setLayout(new BorderLayout(0, 0));
        buildUI();
    }

    private void buildUI() {
        JPanel header = new JPanel(null);
        header.setBackground(UITheme.BG_PANEL);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 80));

        JLabel title = UITheme.titleLabel("📋  Car Dataset");
        title.setBounds(24, 16, 400, 30);
        header.add(title);

        JLabel sub = UITheme.bodyLabel("Browse, search, and filter all car records in the dataset.");
        sub.setBounds(24, 48, 600, 20);
        header.add(sub);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(header, BorderLayout.NORTH);
        topSection.add(buildToolbar(), BorderLayout.SOUTH);
        add(topSection, BorderLayout.NORTH);

        carModel = new CarTableModel(new ArrayList<>());
        table    = new JTable(carModel);

        sorter = new TableRowSorter<>(carModel);
        table.setRowSorter(sorter);

        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBackground(UITheme.BG_PANEL);
        scroll.getViewport().setBackground(UITheme.BG_PANEL);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        add(scroll, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 4));
        statusBar.setBackground(UITheme.BG_SIDEBAR);
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));

        recordCount = new JLabel("0 records");
        recordCount.setFont(UITheme.FONT_SMALL);
        recordCount.setForeground(UITheme.TEXT_SECONDARY);
        statusBar.add(recordCount);

        JLabel hint = new JLabel("  Click a column header to sort");
        hint.setFont(UITheme.FONT_SMALL);
        hint.setForeground(UITheme.TEXT_MUTED);
        statusBar.add(hint);

        add(statusBar, BorderLayout.SOUTH);
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bar.setBackground(UITheme.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));

        JLabel searchIco = new JLabel("🔍");
        searchIco.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        bar.add(searchIco);

        searchField = UITheme.styledTextField(22);
        searchField.setToolTipText("Search by car name…");
        searchField.setPreferredSize(new Dimension(220, 32));
        bar.add(searchField);

        JLabel fuelLbl = new JLabel("  ⛽ Fuel:");
        fuelLbl.setFont(UITheme.FONT_BODY);
        fuelLbl.setForeground(UITheme.TEXT_SECONDARY);
        bar.add(fuelLbl);
        fuelFilter = UITheme.styledComboBox(new String[]{"All",
                Car.FUEL_PETROL, Car.FUEL_DIESEL, Car.FUEL_CNG,
                Car.FUEL_ELECTRIC, Car.FUEL_LPG, Car.FUEL_HYBRID});
        fuelFilter.setPreferredSize(new Dimension(110, 32));
        bar.add(fuelFilter);

        JLabel transLbl = new JLabel("  ⚙ Trans:");
        transLbl.setFont(UITheme.FONT_BODY);
        transLbl.setForeground(UITheme.TEXT_SECONDARY);
        bar.add(transLbl);
        transFilter = UITheme.styledComboBox(new String[]{"All",
                Car.TRANS_MANUAL, Car.TRANS_AUTOMATIC});
        transFilter.setPreferredSize(new Dimension(110, 32));
        bar.add(transFilter);

        JButton clearBtn = UITheme.jSecondaryButton("Clear");
        clearBtn.setPreferredSize(new Dimension(80, 32));
        bar.add(clearBtn);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { applyFilters(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { applyFilters(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
        });

        fuelFilter.addActionListener(e  -> applyFilters());
        transFilter.addActionListener(e -> applyFilters());

        clearBtn.addActionListener(e -> {
            searchField.setText("");
            fuelFilter.setSelectedIndex(0);
            transFilter.setSelectedIndex(0);
            carModel.resetFilters();
            updateCount();
        });

        return bar;
    }

    public void refresh() {
        List<Car> cars = dashboard.getCarDataset();
        carModel.setData(cars != null ? cars : new ArrayList<>());
        updateCount();
    }

    private void applyFilters() {
        carModel.setSearchQuery((String) null);
        carModel.setSearchQuery(searchField.getText());
        carModel.setFuelFilter((String) fuelFilter.getSelectedItem());
        carModel.setTransmissionFilter((String) transFilter.getSelectedItem());
        updateCount();
    }

    private void updateCount() {
        recordCount.setText(carModel.getVisibleCarCount() + " of " +
                carModel.getTotalCarCount() + " records");
    }

    private void styleTable(JTable t) {
        t.setBackground(UITheme.BG_PANEL);
        t.setForeground(UITheme.TEXT_PRIMARY);
        t.setFont(UITheme.FONT_BODY);
        t.setRowHeight(30);
        t.setGridColor(UITheme.SEPARATOR_CLR);
        t.setSelectionBackground(new Color(0x4A, 0x9E, 0xFF, 80));
        t.setSelectionForeground(UITheme.TEXT_PRIMARY);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(false);
        t.setIntercellSpacing(new Dimension(12, 0));
        t.getTableHeader().setBackground(UITheme.BG_SIDEBAR);
        t.getTableHeader().setForeground(UITheme.ACCENT_BLUE);
        t.getTableHeader().setFont(UITheme.FONT_LABEL);
        t.getTableHeader().setReorderingAllowed(false);
        t.getTableHeader().setPreferredSize(new Dimension(0, 36));

        for (int i = 0; i < CarTableModel.PREFERRED_WIDTHS.length && i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setPreferredWidth(CarTableModel.PREFERRED_WIDTHS[i]);
        }

        TableCellRenderer renderer = new PriceAwareRenderer();
        for (int i = 0; i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private static class PriceAwareRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val,
                boolean selected, boolean focused, int row, int col) {
            super.getTableCellRendererComponent(t, val, selected, focused, row, col);

            if (selected) {
                setBackground(new Color(0x4A, 0x9E, 0xFF, 80));
                setForeground(UITheme.TEXT_PRIMARY);
            } else {
                setBackground(row % 2 == 0 ? UITheme.BG_PANEL : UITheme.BG_TABLE_ALT);
                setForeground(col == CarTableModel.COL_PRICE ? UITheme.ACCENT_GREEN : UITheme.TEXT_PRIMARY);
            }

            setFont(UITheme.FONT_BODY);
            setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

            if (col == CarTableModel.COL_PRICE && val instanceof Double) {
                setText(UITheme.formatPrice((Double) val));
                setHorizontalAlignment(RIGHT);
            } else if (val instanceof Double) {
                setText(val.equals(0.0) ? "—" : String.format("%.1f", (Double) val));
                setHorizontalAlignment(RIGHT);
            } else if (val instanceof Integer) {
                setHorizontalAlignment(RIGHT);
            } else {
                setHorizontalAlignment(LEFT);
            }

            return this;
        }
    }
}
