package com.cardss.model;

public class Car {

    public static final String FUEL_PETROL   = "Petrol";
    public static final String FUEL_DIESEL   = "Diesel";
    public static final String FUEL_CNG      = "CNG";
    public static final String FUEL_ELECTRIC = "Electric";
    public static final String FUEL_LPG      = "LPG";
    public static final String FUEL_HYBRID   = "Hybrid";
    public static final String FUEL_UNKNOWN  = "Unknown";

    public static final String TRANS_MANUAL    = "Manual";
    public static final String TRANS_AUTOMATIC = "Automatic";
    public static final String TRANS_UNKNOWN   = "Unknown";

    private String name;
    private int    year;
    private double sellingPrice;
    private double kmDriven;
    private String fuel;
    private String sellerType;
    private String transmission;
    private String owner;
    private double mileage;
    private double engine;
    private double maxPower;
    private String torque;
    private int    seats;

    private double dssScore;
    private double matchPercentage;
    private String explanation;

    public Car() {
        name         = "";
        fuel         = FUEL_UNKNOWN;
        sellerType   = "";
        transmission = TRANS_UNKNOWN;
        owner        = "";
        torque       = "";
        explanation  = "";
    }

    public Car(String name, int year, double sellingPrice, double kmDriven,
               String fuel, String sellerType, String transmission, String owner,
               double mileage, double engine, double maxPower, String torque, int seats) {
        this.name         = name         != null ? name.trim()         : "";
        this.year         = year;
        this.sellingPrice = sellingPrice;
        this.kmDriven     = kmDriven;
        this.fuel         = fuel         != null ? fuel.trim()         : FUEL_UNKNOWN;
        this.sellerType   = sellerType   != null ? sellerType.trim()   : "";
        this.transmission = transmission != null ? transmission.trim() : TRANS_UNKNOWN;
        this.owner        = owner        != null ? owner.trim()        : "";
        this.mileage      = mileage;
        this.engine       = engine;
        this.maxPower     = maxPower;
        this.torque       = torque       != null ? torque.trim()       : "";
        this.seats        = seats;
        this.explanation  = "";
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() && year >= 1990 && sellingPrice > 0 && seats > 0;
    }

    public String getName()              { return name; }
    public int    getYear()             { return year; }
    public double getSellingPrice()     { return sellingPrice; }
    public double getKmDriven()         { return kmDriven; }
    public String getFuel()             { return fuel; }
    public String getSellerType()       { return sellerType; }
    public String getTransmission()     { return transmission; }
    public String getOwner()            { return owner; }
    public double getMileage()          { return mileage; }
    public double getEngine()           { return engine; }
    public double getMaxPower()         { return maxPower; }
    public String getTorque()           { return torque; }
    public int    getSeats()            { return seats; }
    public double getDssScore()         { return dssScore; }
    public double getMatchPercentage()  { return matchPercentage; }
    public String getExplanation()      { return explanation; }

    public void setName(String v)             { this.name         = v != null ? v.trim() : ""; }
    public void setYear(int v)               { this.year         = v; }
    public void setSellingPrice(double v)    { this.sellingPrice = v; }
    public void setKmDriven(double v)        { this.kmDriven     = v; }
    public void setFuel(String v)            { this.fuel         = v != null ? v.trim() : FUEL_UNKNOWN; }
    public void setSellerType(String v)      { this.sellerType   = v != null ? v.trim() : ""; }
    public void setTransmission(String v)    { this.transmission = v != null ? v.trim() : TRANS_UNKNOWN; }
    public void setOwner(String v)           { this.owner        = v != null ? v.trim() : ""; }
    public void setMileage(double v)         { this.mileage      = v; }
    public void setEngine(double v)          { this.engine       = v; }
    public void setMaxPower(double v)        { this.maxPower     = v; }
    public void setTorque(String v)          { this.torque       = v != null ? v.trim() : ""; }
    public void setSeats(int v)             { this.seats        = v; }
    public void setDssScore(double v)        { this.dssScore        = v; }
    public void setMatchPercentage(double v) { this.matchPercentage = v; }
    public void setExplanation(String v)     { this.explanation     = v != null ? v : ""; }

    @Override
    public String toString() {
        return name + " (" + year + ") - EGP " + (int) sellingPrice;
    }
}
