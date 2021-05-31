package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Category extends Observable<Category> {

    private final String name;
    private double dailyRate;
    private double deposit;

    public Category(String name, double dailyRate, double deposit) {
        this.name = name;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
    }

    public String getName() {
        return name;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
        notifyObservers();
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("name", getName());
        json.put("daily_rate", getDailyRate());
        json.put("deposit", getDeposit());
        return json;
    }

}
