package net.efrei.s6.databases.aurelducyoni.controllers;

import net.efrei.s6.databases.aurelducyoni.dbms.Datastore;
import net.efrei.s6.databases.aurelducyoni.models.*;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleController {

    private final Datastore datastore;

    public VehicleController(Datastore datastore) {
        this.datastore = datastore;
    }

    public List<Vehicle> getAvailableVehiclesList() {
        return searchAvailableVehicles(null);
    }

    public List<Vehicle> searchAvailableVehicles(String search) {
        if (search != null && search.isEmpty())
            search = null;

        final String finalSearch = search;
        Set<Rental> returnedRentals = datastore.getReturnList().stream().map(Return::getRental).collect(Collectors.toSet());
        Set<Vehicle> rentedVehicles = datastore.getRentalList().stream().filter(rental -> !returnedRentals.contains(rental))
            .map(rental -> rental.getReservation().getVehicle()).collect(Collectors.toSet());

        return datastore.getVehicleList().stream().filter(vehicle -> {
            if (rentedVehicles.contains(vehicle)) return false;
            if (finalSearch == null) return true;
            String model = vehicle.getModel().toLowerCase();
            String brand = vehicle.getBrand().toLowerCase();
            String category = vehicle.getCategory().getName().toLowerCase();
            for (String word : finalSearch.toLowerCase().split(" "))
                if (!model.contains(word) && !brand.contains(word) && !category.contains(word))
                    return false;
            return true;
        }).collect(Collectors.toList());
    }

}
