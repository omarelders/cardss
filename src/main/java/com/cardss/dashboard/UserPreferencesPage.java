package com.cardss.dashboard;

import com.cardss.util.UITheme;
import com.cardss.util.UserPreferences;

import javax.swing.*;
import java.awt.*;

public class UserPreferencesPage extends JPanel {

    private final DashboardPage dashboard;

    private JTextField budgetField;
    private JComboBox<String> fuelCombo;
    private JComboBox<String> transCombo;
    private JSpinner seatsSpinner;
    private JSpinner engineSpinner;
    private JSlider mileageSlider;
    private JSlider maintenanceSlider;

    public UserPreferencesPage(DashboardPage dashboard) {
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

        JLabel title = UITheme.titleLabel("My Preferences");
        title.setBounds(24, 16, 400, 30);
        header.add(title);

        JLabel sub = UITheme.bodyLabel("Set your filters and priorities to find the best car match.");
        sub.setBounds(24, 48, 600, 20);
        header.add(sub);

        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UITheme.BG_DARK);
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel inner = buildForm();
        form.add(inner, new GridBagConstraints());

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UITheme.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel p = new JPanel(null);
        p.setBackground(UITheme.BG_DARK);
        p.setPreferredSize(new Dimension(700, 500));

        addLabel(p, "Max Budget (EGP):", 0, 0);
        budgetField = UITheme.styledTextField(15);
        budgetField.setText("500000");
        budgetField.setBounds(0, 22, 250, 36);
        p.add(budgetField);

        addLabel(p, "Fuel Type:", 0, 78);
        fuelCombo = UITheme.styledComboBox(new String[] { "Any", "Petrol", "Diesel", "CNG", "Electric", "LPG" });
        fuelCombo.setBounds(0, 100, 200, 36);
        p.add(fuelCombo);

        addLabel(p, "Transmission:", 250, 78);
        transCombo = UITheme.styledComboBox(new String[] { "Any", "Manual", "Automatic" });
        transCombo.setBounds(250, 100, 180, 36);
        p.add(transCombo);

        addLabel(p, "Min Seats:", 0, 156);
        seatsSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 9, 1));
        seatsSpinner.setBounds(0, 178, 100, 36);
        styleSpinner(seatsSpinner);
        p.add(seatsSpinner);

        addLabel(p, "Min Engine (CC):", 160, 156);
        engineSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5000, 100));
        engineSpinner.setBounds(160, 178, 130, 36);
        styleSpinner(engineSpinner);
        p.add(engineSpinner);

        addLabel(p, "Mileage Priority (1-5):", 0, 234);
        mileageSlider = makePrioritySlider(3);
        mileageSlider.setBounds(0, 256, 300, 48);
        p.add(mileageSlider);

        addLabel(p, "Maintenance Priority (1-5):", 340, 234);
        maintenanceSlider = makePrioritySlider(3);
        maintenanceSlider.setBounds(340, 256, 300, 48);
        p.add(maintenanceSlider);

        JButton findBtn = UITheme.jPrimaryButton("Find Best Cars");
        findBtn.setBounds(0, 330, 200, 44);
        findBtn.setPreferredSize(null);
        findBtn.addActionListener(e -> saveAndRecommend());
        p.add(findBtn);

        JButton resetBtn = UITheme.jSecondaryButton("Reset");
        resetBtn.setBounds(216, 330, 120, 44);
        resetBtn.setPreferredSize(null);
        resetBtn.addActionListener(e -> resetDefaults());
        p.add(resetBtn);

        return p;
    }

    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_LABEL);
        l.setForeground(UITheme.TEXT_SECONDARY);
        l.setBounds(x, y, 300, 18);
        p.add(l);
    }

    private JSlider makePrioritySlider(int value) {
        JSlider s = new JSlider(1, 5, value);
        s.setBackground(UITheme.BG_DARK);
        s.setMajorTickSpacing(1);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setSnapToTicks(true);
        s.setLabelTable(s.createStandardLabels(1));
        s.setOpaque(false);
        return s;
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setBackground(UITheme.BG_INPUT);
        spinner.setForeground(UITheme.TEXT_PRIMARY);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(UITheme.BG_INPUT);
            tf.setForeground(UITheme.TEXT_PRIMARY);
            tf.setFont(UITheme.FONT_BODY);
        }
    }

    private void saveAndRecommend() {
        UserPreferences prefs = dashboard.getUserPreferences();

        try {
            prefs.setBudget(Double.parseDouble(budgetField.getText().replaceAll("[^0-9.]", "")));
        } catch (NumberFormatException e) {
            prefs.setBudget(500000);
        }

        prefs.setFuelType((String) fuelCombo.getSelectedItem());
        prefs.setTransmission((String) transCombo.getSelectedItem());
        prefs.setMinSeats((Integer) seatsSpinner.getValue());
        prefs.setMinEngine((Integer) engineSpinner.getValue());
        prefs.setMileagePriority(mileageSlider.getValue());
        prefs.setMaintenancePriority(maintenanceSlider.getValue());

        dashboard.navigateTo("REC");
    }

    private void resetDefaults() {
        budgetField.setText("500000");
        fuelCombo.setSelectedIndex(0);
        transCombo.setSelectedIndex(0);
        seatsSpinner.setValue(4);
        engineSpinner.setValue(0);
        mileageSlider.setValue(3);
        maintenanceSlider.setValue(3);
    }
}
