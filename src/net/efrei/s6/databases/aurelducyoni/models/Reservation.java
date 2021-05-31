package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Reservation extends Observable<Reservation> {

    private final int id;
    private Agency agency;
    private Customer customer;
    private Vehicle vehicle;
    private Employee employee;
    private int expectedDuration;

    public Reservation(int id, Agency agency, Customer customer, Vehicle vehicle, Employee employee, int expectedDuration) {
        this.id = id;
        this.agency = agency;
        this.customer = customer;
        this.vehicle = vehicle;
        this.employee = employee;
        this.expectedDuration = expectedDuration;
    }

    public int getId() {
        return id;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
        notifyObservers();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        notifyObservers();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        notifyObservers();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        notifyObservers();
    }

    public int getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(int expectedDuration) {
        this.expectedDuration = expectedDuration;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("agency", getAgency() != null ? getAgency().toJSON() : null);
        json.put("customer", getCustomer() != null ? getCustomer().toJSON() : null);
        json.put("vehicle", getVehicle() != null ? getVehicle().toJSON() : null);
        json.put("employee", getEmployee() != null ? getEmployee().toJSON() : null);
        json.put("expected_duration", getExpectedDuration());
        return json;
    }

}
