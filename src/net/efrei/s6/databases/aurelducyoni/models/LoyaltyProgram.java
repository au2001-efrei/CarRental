package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class LoyaltyProgram extends Observable<LoyaltyProgram> {

    private final String name;
    private int duration;
    private String description;
    private double price;
    private double discountRate;

    public LoyaltyProgram(String name, int duration, String description, double price, double discountRate) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.price = price;
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        notifyObservers();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyObservers();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("name", getName());
        json.put("duration", getDuration());
        json.put("description", getDescription());
        json.put("price", getPrice());
        json.put("discount_rate", getDiscountRate());
        return json;
    }

}
