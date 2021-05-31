package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Quote extends Observable<Quote> {

    private final int id;
    private Reservation reservation;
    private double amount;

    public Quote(int id, Reservation reservation, double amount) {
        this.id = id;
        this.reservation = reservation;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        notifyObservers();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("reservation", getReservation() != null ? getReservation().toJSON() : null);
        json.put("amount", getAmount());
        return json;
    }

}
