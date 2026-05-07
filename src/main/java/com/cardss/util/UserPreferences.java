package com.cardss.util;

public class UserPreferences {

    private double budget = 500_000;
    private String fuelType = "Any";
    private String transmission = "Any";
    private int minSeats = 4;
    private int minEngine = 0;
    private int mileagePriority = 3;
    private int maintenancePriority = 3;

    public UserPreferences() {}

    public double getBudget()              { return budget; }
    public String getFuelType()            { return fuelType; }
    public String getTransmission()        { return transmission; }
    public int    getMinSeats()            { return minSeats; }
    public int    getMinEngine()           { return minEngine; }
    public int    getMileagePriority()     { return mileagePriority; }
    public int    getMaintenancePriority() { return maintenancePriority; }

    public void setBudget(double budget)              { this.budget = budget; }
    public void setFuelType(String fuelType)          { this.fuelType = fuelType; }
    public void setTransmission(String transmission)  { this.transmission = transmission; }
    public void setMinSeats(int minSeats)             { this.minSeats = minSeats; }
    public void setMinEngine(int minEngine)           { this.minEngine = minEngine; }
    public void setMileagePriority(int p)             { this.mileagePriority = p; }
    public void setMaintenancePriority(int p)         { this.maintenancePriority = p; }
}
