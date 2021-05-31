package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Return extends Observable<Return> {

    private final Rental rental;
    private Agency agency;
    private Employee employee;
    private Date date;
    private int fuelConsumption;
    private int damageLevel;

    public Return(Rental rental, Agency agency, Employee employee, Date date, int fuelConsumption, int damageLevel) {
        this.rental = rental;
        this.agency = agency;
        this.employee = employee;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.fuelConsumption = fuelConsumption;
        this.damageLevel = damageLevel;
    }

    public Rental getRental() {
        return rental;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
        notifyObservers();
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

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        notifyObservers();
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("rental", getRental());
        json.put("agency", getAgency() != null ? getAgency().toJSON() : null);
        json.put("employee", getEmployee() != null ? getEmployee().toJSON() : null);
        json.put("date", getDate() != null ? getDate().getTime() : null);
        json.put("fuel_consumption", getFuelConsumption());
        json.put("damage_level", getDamageLevel());
        return json;
    }

}
