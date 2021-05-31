package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Invoice extends Observable<Invoice> {

    private final int id;
    private Return aReturn;
    private double amount;
    private double durationPenalty;
    private double fuelCharge;
    private double damagePenalty;

    public Invoice(int id, Return aReturn, double amount, double durationPenalty, double fuelCharge, double damagePenalty) {
        this.id = id;
        this.aReturn = aReturn;
        this.amount = amount;
        this.durationPenalty = durationPenalty;
        this.fuelCharge = fuelCharge;
        this.damagePenalty = damagePenalty;
    }

    public int getId() {
        return id;
    }

    public Return getReturn() {
        return aReturn;
    }

    public void setReturn(Return aReturn) {
        this.aReturn = aReturn;
        notifyObservers();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        notifyObservers();
    }

    public double getDurationPenalty() {
        return durationPenalty;
    }

    public void setDurationPenalty(double durationPenalty) {
        this.durationPenalty = durationPenalty;
        notifyObservers();
    }

    public double getFuelCharge() {
        return fuelCharge;
    }

    public void setFuelCharge(double fuelCharge) {
        this.fuelCharge = fuelCharge;
        notifyObservers();
    }

    public double getDamagePenalty() {
        return damagePenalty;
    }

    public void setDamagePenalty(double damagePenalty) {
        this.damagePenalty = damagePenalty;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("return", getReturn() != null ? getReturn().toJSON() : null);
        json.put("amount", getAmount());
        json.put("duration_penalty", getDurationPenalty());
        json.put("fuel_charge", getFuelCharge());
        json.put("damage_penalty", getDamagePenalty());
        return json;
    }

}
