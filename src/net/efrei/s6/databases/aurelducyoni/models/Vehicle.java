package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Vehicle extends Observable<Vehicle> {

    private final String licensePlate;
    private String brand;
    private String model;
    private int mileage;
    private boolean automatic;
    private boolean airConditioned;
    private String fuel;
    private Category category;

    public Vehicle(String licensePlate, String brand, String model, int mileage, boolean automatic, boolean airConditioned, String fuel, Category category) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.mileage = mileage;
        this.automatic = automatic;
        this.airConditioned = airConditioned;
        this.fuel = fuel;
        this.category = category;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
        notifyObservers();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
        notifyObservers();
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
        notifyObservers();
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
        notifyObservers();
    }

    public boolean isAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(boolean airConditioned) {
        this.airConditioned = airConditioned;
        notifyObservers();
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
        notifyObservers();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("license_plate", getLicensePlate());
        json.put("brand", getBrand());
        json.put("model", getModel());
        json.put("mileage", getMileage());
        json.put("automatic", isAutomatic());
        json.put("air_conditioned", isAirConditioned());
        json.put("fuel", getFuel());
        json.put("category", getCategory() != null ? getCategory().toJSON() : null);
        return json;
    }

}
