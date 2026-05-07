package com.cardss.model;

import com.cardss.util.UITheme;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CarTableModel extends AbstractTableModel {

    public static final String[] COLUMN_NAMES = {
            "#", "Name", "Year", "Price (EGP)", "Fuel",
            "Transmission", "Mileage (kmpl)", "Engine (CC)", "Max Power (bhp)", "Seats"
    };

    public static final int COL_RANK    = 0;
    public static final int COL_NAME    = 1;
    public static final int COL_YEAR    = 2;
    public static final int COL_PRICE   = 3;
    public static final int COL_FUEL    = 4;
    public static final int COL_TRANS   = 5;
    public static final int COL_MILEAGE = 6;
    public static final int COL_ENGINE  = 7;
    public static final int COL_POWER   = 8;
    public static final int COL_SEATS   = 9;

    public static final int COLUMN_COUNT = COLUMN_NAMES.length;

    public static final int[] PREFERRED_WIDTHS = {
            36, 210, 52, 120, 72, 100, 110, 90, 115, 48
    };

    private final List<Car> allCars;
    private List<Car> visibleCars;

    private String searchQuery        = "";
    private String fuelFilter         = "All";
    private String transmissionFilter = "All";

    public CarTableModel(List<Car> cars) {
        this.allCars     = new ArrayList<>(cars);
        this.visibleCars = new ArrayList<>(cars);
    }

    @Override public int getRowCount()    { return visibleCars.size(); }
    @Override public int getColumnCount() { return COLUMN_COUNT; }
    @Override public String getColumnName(int col) { return COLUMN_NAMES[col]; }
    @Override public boolean isCellEditable(int row, int col) { return false; }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case COL_RANK:
            case COL_YEAR:
            case COL_SEATS:   return Integer.class;
            case COL_PRICE:
            case COL_MILEAGE:
            case COL_ENGINE:
            case COL_POWER:   return Double.class;
            default:          return String.class;
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (row < 0 || row >= visibleCars.size()) return null;
        Car c = visibleCars.get(row);
        switch (col) {
            case COL_RANK:    return row + 1;
            case COL_NAME:    return c.getName();
            case COL_YEAR:    return c.getYear();
            case COL_PRICE:   return c.getSellingPrice();
            case COL_FUEL:    return c.getFuel();
            case COL_TRANS:   return c.getTransmission();
            case COL_MILEAGE: return c.getMileage() > 0 ? c.getMileage() : null;
            case COL_ENGINE:  return c.getEngine()  > 0 ? c.getEngine()  : null;
            case COL_POWER:   return c.getMaxPower()> 0 ? c.getMaxPower(): null;
            case COL_SEATS:   return c.getSeats();
            default:          return null;
        }
    }

    public Car getCarAt(int row) {
        if (row < 0 || row >= visibleCars.size()) return null;
        return visibleCars.get(row);
    }

    public void setData(List<Car> cars) {
        allCars.clear();
        if (cars != null) allCars.addAll(cars);
        resetFilters();
    }

    public int getTotalCarCount()   { return allCars.size(); }
    public int getVisibleCarCount() { return visibleCars.size(); }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query.trim() : "";
        applyFilters();
    }

    public void setFuelFilter(String fuel) {
        this.fuelFilter = fuel != null ? fuel.trim() : "All";
        applyFilters();
    }

    public void setTransmissionFilter(String trans) {
        this.transmissionFilter = trans != null ? trans.trim() : "All";
        applyFilters();
    }

    public void resetFilters() {
        searchQuery         = "";
        fuelFilter          = "All";
        transmissionFilter  = "All";
        applyFilters();
    }

    public void sort(int col, boolean ascending) {
        Comparator<Car> cmp;
        switch (col) {
            case COL_NAME:    cmp = Comparator.comparing(Car::getName, String.CASE_INSENSITIVE_ORDER); break;
            case COL_YEAR:    cmp = Comparator.comparingInt(Car::getYear);      break;
            case COL_PRICE:   cmp = Comparator.comparingDouble(Car::getSellingPrice); break;
            case COL_FUEL:    cmp = Comparator.comparing(Car::getFuel, String.CASE_INSENSITIVE_ORDER); break;
            case COL_TRANS:   cmp = Comparator.comparing(Car::getTransmission, String.CASE_INSENSITIVE_ORDER); break;
            case COL_MILEAGE: cmp = Comparator.comparingDouble(Car::getMileage); break;
            case COL_ENGINE:  cmp = Comparator.comparingDouble(Car::getEngine);  break;
            case COL_POWER:   cmp = Comparator.comparingDouble(Car::getMaxPower); break;
            case COL_SEATS:   cmp = Comparator.comparingInt(Car::getSeats);      break;
            default:          return;
        }
        if (!ascending) cmp = cmp.reversed();
        visibleCars.sort(cmp);
        fireTableDataChanged();
    }

    private void applyFilters() {
        visibleCars = allCars.stream()
                .filter(c -> searchQuery.isEmpty() || c.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                .filter(c -> "All".equalsIgnoreCase(fuelFilter) || c.getFuel().equalsIgnoreCase(fuelFilter))
                .filter(c -> "All".equalsIgnoreCase(transmissionFilter) || c.getTransmission().equalsIgnoreCase(transmissionFilter))
                .collect(Collectors.toList());
        fireTableDataChanged();
    }
}
