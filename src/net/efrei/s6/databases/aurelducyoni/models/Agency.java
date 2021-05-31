package net.efrei.s6.databases.aurelducyoni.models;

import net.efrei.s6.databases.aurelducyoni.observers.Observable;

import java.util.HashMap;
import java.util.Map;

public class Agency extends Observable<Agency> {

    private final int id;
    private String name;
    private String phone;
    private String streetNumber;
    private String streetName;
    private String postalCode;
    private String city;
    private double longitude;
    private double latitude;
    private int capacity;

    public Agency(int id, String name, String phone, String streetNumber, String streetName, String postalCode, String city, double longitude, double latitude, int capacity) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyObservers();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        notifyObservers();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
        notifyObservers();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        notifyObservers();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        notifyObservers();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        notifyObservers();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        notifyObservers();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        notifyObservers();
    }

    public Map<String, ?> toJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("name", getName());
        json.put("phone", getPhone());
        json.put("street_number", getStreetNumber());
        json.put("street_name", getStreetName());
        json.put("postal_code", getPostalCode());
        json.put("city", getCity());
        json.put("longitude", getLongitude());
        json.put("latitude", getLatitude());
        json.put("capacity", getCapacity());
        return json;
    }

}
