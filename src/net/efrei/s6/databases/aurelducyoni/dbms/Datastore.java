package net.efrei.s6.databases.aurelducyoni.dbms;

import net.efrei.s6.databases.aurelducyoni.models.*;

import java.io.Closeable;
import java.util.Date;
import java.util.List;

public interface Datastore extends Closeable {

    Agency createAgency(String name, String phone, String streetNumber, String streetName, String postalCode, String city, double longitude, double latitude, int capacity);
    Agency getAgency(int id);
    List<Agency> getAgencyList();
    boolean updateAgency(Agency agency);
    boolean deleteAgency(Agency agency);
    boolean deleteAgency(int id);

    Category createCategory(String name, double dailyRate, double deposit);
    Category getCategory(String name);
    List<Category> getCategoryList();
    boolean updateCategory(Category category);
    boolean deleteCategory(Category category);
    boolean deleteCategory(String name);

    Customer createCustomer(String firstName, String lastName, String streetNumber, String streetName, String postalCode, String city, String phone);
    Customer getCustomer(int id);
    List<Customer> getCustomerList();
    List<Customer> searchCustomers(String search);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(Customer customer);
    boolean deleteCustomer(int id);

    Distribution createDistribution(Employee employee, Truck truck, Date date, Agency source, Agency destination, List<Vehicle> vehicles);
    Distribution getDistribution(int id);
    List<Distribution> getDistributionList();
    boolean updateDistribution(Distribution distribution);
    boolean deleteDistribution(Distribution distribution);
    boolean deleteDistribution(int id);

    Employee createEmployee(Agency agency, String firstName, String lastName, String streetNumber, String streetName, String postalCode, String city, String phone, String passwordHash);
    Employee getEmployee(int id);
    List<Employee> getEmployeeList();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(Employee employee);
    boolean deleteEmployee(int id);

    Invoice createInvoice(Return aReturn, double amount, double durationPenalty, double fuelCharge, double damagePenalty);
    Invoice getInvoice(int id);
    List<Invoice> getInvoiceList();
    boolean updateInvoice(Invoice invoice);
    boolean deleteInvoice(Invoice invoice);
    boolean deleteInvoice(int id);

    LoyaltyProgram createLoyaltyProgram(String name, int duration, String description, double price, double discountRate);
    LoyaltyProgram getLoyaltyProgram(String name);
    List<LoyaltyProgram> getLoyaltyProgramList();
    boolean updateLoyaltyProgram(LoyaltyProgram loyaltyProgram);
    boolean deleteLoyaltyProgram(LoyaltyProgram loyaltyprogram);
    boolean deleteLoyaltyProgram(String name);

    LoyaltySubscription createLoyaltySubscription(Customer customer, LoyaltyProgram loyaltyProgram, Date subscriptionDate);
    LoyaltySubscription getLoyaltySubscription(int id);
    List<LoyaltySubscription> getLoyaltySubscriptionList();
    boolean updateLoyaltySubscription(LoyaltySubscription loyaltySubscription);
    boolean deleteLoyaltySubscription(LoyaltySubscription loyaltysubscription);
    boolean deleteLoyaltySubscription(int id);

    Quote createQuote(Reservation reservation, double amount);
    Quote getQuote(int id);
    List<Quote> getQuoteList();
    boolean updateQuote(Quote quote);
    boolean deleteQuote(Quote quote);
    boolean deleteQuote(int id);

    Rental createRental(Reservation reservation, Employee employee, Date date, boolean damageInsurance, boolean accidentInsurance, int duration);
    Rental getRental(Reservation reservation);
    Rental getRental(int reservationId);
    List<Rental> getRentalList();
    boolean updateRental(Rental rental);
    boolean deleteRental(Rental rental);
    boolean deleteRental(Reservation reservation);
    boolean deleteRental(int reservationId);

    Reservation createReservation(Agency agency, Customer customer, Vehicle vehicle, Employee employee, int expectedDuration);
    Reservation getReservation(int id);
    List<Reservation> getReservationList();
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(Reservation reservation);
    boolean deleteReservation(int id);

    Return createReturn(Rental rental, Agency agency, Employee employee, Date date, int fuelConsumption, int damageLevel);
    Return getReturn(Rental rental);
    Return getReturn(Reservation rentalReservation);
    Return getReturn(int rentalReservationId);
    List<Return> getReturnList();
    boolean updateReturn(Return aReturn);
    boolean deleteReturn(Return aReturn);
    boolean deleteReturn(Rental rental);
    boolean deleteReturn(Reservation rentalReservation);
    boolean deleteReturn(int rentalReservationId);

    Truck createTruck(String licensePlate);
    Truck getTruck(String licensePlate);
    List<Truck> getTruckList();
    boolean updateTruck(Truck truck);
    boolean deleteTruck(Truck truck);
    boolean deleteTruck(String licensePlate);

    Vehicle createVehicle(String licensePlate, String brand, String model, int mileage, boolean automatic, boolean airConditioned, String fuel, Category category);
    Vehicle getVehicle(String licensePlate);
    List<Vehicle> getVehicleList();
    boolean updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(Vehicle vehicle);
    boolean deleteVehicle(String licensePlate);

}
