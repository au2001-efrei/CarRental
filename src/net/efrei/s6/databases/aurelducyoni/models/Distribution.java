package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.*;
import java.util.stream.Collectors;

public class Distribution extends Observable<Distribution> {

    private final int id;
    private Employee employee;
    private Truck truck;
    private Date date;
    private Agency source;
    private Agency destination;
    private List<Vehicle> vehicles;

    public Distribution(int id, Employee employee, Truck truck, Date date, Agency source, Agency destination, List<Vehicle> vehicles) {
        this.id = id;
        this.employee = employee;
        this.truck = truck;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.source = source;
        this.destination = destination;
        this.vehicles = vehicles != null ? new ArrayList<>(vehicles) : Collections.emptyList();
    }

    public int getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        notifyObservers();
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
        notifyObservers();
    }

    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }

    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : null;
        notifyObservers();
    }

    public Agency getSource() {
        return source;
    }

    public void setSource(Agency source) {
        this.source = source;
        notifyObservers();
    }

    public Agency getDestination() {
        return destination;
    }

    public void setDestination(Agency destination) {
        this.destination = destination;
        notifyObservers();
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? new ArrayList<>(vehicles) : Collections.emptyList();
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("employee", getEmployee() != null ? getEmployee().toJSON() : null);
        json.put("truck", getTruck() != null ? getTruck().toJSON() : null);
        json.put("date", getDate());
        json.put("source", getSource() != null ? getSource().toJSON() : null);
        json.put("destination", getDestination() != null ? getDestination().toJSON() : null);
        json.put("vehicles", getVehicles().stream().map(Vehicle::toJSON).collect(Collectors.toList()));
        return json;
    }

}
