package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Rental extends Observable<Rental> {

    private final Reservation reservation;
    private Employee employee;
    private Date date;
    private boolean damageInsurance;
    private boolean accidentInsurance;
    private int duration;

    public Rental(Reservation reservation, Employee employee, Date date, boolean damageInsurance, boolean accidentInsurance, int duration) {
        this.reservation = reservation;
        this.employee = employee;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.damageInsurance = damageInsurance;
        this.accidentInsurance = accidentInsurance;
        this.duration = duration;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        notifyObservers();
    }

    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }

    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : null;
        notifyObservers();
    }

    public boolean hasDamageInsurance() {
        return damageInsurance;
    }

    public void setDamageInsurance(boolean damageInsurance) {
        this.damageInsurance = damageInsurance;
        notifyObservers();
    }

    public boolean hasAccidentInsurance() {
        return accidentInsurance;
    }

    public void setAccidentInsurance(boolean accidentInsurance) {
        this.accidentInsurance = accidentInsurance;
        notifyObservers();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("reservation", getReservation());
        json.put("employee", getEmployee() != null ? getEmployee().toJSON() : null);
        json.put("date", getDate() != null ? getDate().getTime() : null);
        json.put("damage_insurance", hasDamageInsurance());
        json.put("accident_insurance", hasAccidentInsurance());
        return json;
    }

}
