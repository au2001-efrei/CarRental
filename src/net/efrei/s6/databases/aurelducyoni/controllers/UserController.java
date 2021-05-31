package net.efrei.s6.databases.aurelducyoni.controllers;

import net.efrei.s6.databases.aurelducyoni.dbms.Datastore;
import net.efrei.s6.databases.aurelducyoni.models.Customer;
import net.efrei.s6.databases.aurelducyoni.models.Employee;

import java.util.*;

public class UserController {

    private final Datastore datastore;
    private final Map<UUID, Employee> authTokens;

    public UserController(Datastore datastore) {
        this.datastore = datastore;
        this.authTokens = new HashMap<>();
    }

    public Employee getAuthenticatedEmployee(String token) {
        if (token == null)
            return null;
        return getAuthenticatedEmployee(UUID.fromString(token));
    }

    public Employee getAuthenticatedEmployee(UUID token) {
        return authTokens.get(token);
    }

    public UUID getAuthenticationToken(Employee employee) {
        UUID token = UUID.randomUUID();
        authTokens.put(token, employee);
        return token;
    }

    public List<Employee> getEmployeeList() {
        return datastore.getEmployeeList();
    }

    public Employee getEmployee(int id) {
        return datastore.getEmployee(id);
    }

    public List<Customer> getCustomerList() {
        List<Customer> customers = new ArrayList<>(datastore.getCustomerList());
        customers.sort(new Comparator<Customer>() {

            @Override
            public int compare(Customer a, Customer b) {
                return (a.getFirstName() + " " + a.getLastName()).compareToIgnoreCase(b.getFirstName() + " " + b.getLastName());
            }

        });
        return customers;
    }

    public Customer getCustomer(int id) {
        return datastore.getCustomer(id);
    }

    public Customer createCustomer(String firstName, String lastName, String streetNumber, String streetName, String postalCode, String city, String phone) {
        assert firstName != null;
        assert lastName != null;
        if (streetNumber != null && streetNumber.isEmpty()) streetNumber = null;
        if (streetName != null && streetName.isEmpty()) streetName = null;
        if (postalCode != null && postalCode.isEmpty()) postalCode = null;
        if (city != null && city.isEmpty()) city = null;
        if (phone != null && phone.isEmpty()) phone = null;

        return datastore.createCustomer(firstName, lastName, streetNumber, streetName, postalCode, city, phone);
    }

}
