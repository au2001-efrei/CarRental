package net.efrei.s6.databases.aurelducyoni.dbms;

import net.efrei.s6.databases.aurelducyoni.models.*;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MySQLDatastore implements Datastore {

    private final Connection connection;

    public MySQLDatastore(String url) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url);
    }

    @Override
    public Agency createAgency(String name, String phone, String streetNumber, String streetName, String postalCode, String city, double longitude, double latitude, int capacity) {
        try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Agency " +
                "(name, phone, street_number, street_name, postal_code, city, longitude, latitude, capacity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, streetNumber);
            statement.setString(4, streetName);
            statement.setString(5, postalCode);
            statement.setString(6, city);
            statement.setDouble(7, longitude);
            statement.setDouble(8, latitude);
            statement.setInt(9, capacity);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Agency agency = new Agency(id, name, phone, streetNumber, streetName, postalCode, city, longitude, latitude, capacity);
                agency.addObserver(this::updateAgency);
                return agency;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Agency getAgency(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Agency WHERE Agency.id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseAgency(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Agency> getAgencyList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Agency")) {
                List<Agency> agencies = new LinkedList<>();
                while (resultSet.next())
                    agencies.add(parseAgency(resultSet));
                return Collections.unmodifiableList(agencies);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateAgency(Agency agency) {
        return false; // TODO
    }

    @Override
    public boolean deleteAgency(Agency agency) {
        return deleteAgency(agency.getId());
    }

    @Override
    public boolean deleteAgency(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Agency WHERE Agency.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Category createCategory(String name, double dailyRate, double deposit) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Category " +
                    "(name, daily_rate, deposit) " +
                    "VALUES (?, ?, ?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setString(1, name);
            statement.setDouble(2, dailyRate);
            statement.setDouble(3, deposit);
            if (statement.executeUpdate() == 0)
                return null;

            Category category = new Category(name, dailyRate, deposit);
            category.addObserver(this::updateCategory);
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category getCategory(String name) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Category WHERE Category.name = ?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseCategory(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Category> getCategoryList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Category")) {
                List<Category> categories = new LinkedList<>();
                while (resultSet.next())
                    categories.add(parseCategory(resultSet));
                return Collections.unmodifiableList(categories);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        return false; // TODO
    }

    @Override
    public boolean deleteCategory(Category category) {
        return deleteCategory(category.getName());
    }

    @Override
    public boolean deleteCategory(String name) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Category WHERE Category.name = ?")) {
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String streetNumber, String streetName, String postalCode, String city, String phone) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Customer " +
                    "(first_name, last_name, street_number, street_name, postal_code, city, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, streetNumber);
            statement.setString(4, streetName);
            statement.setString(5, postalCode);
            statement.setString(6, city);
            statement.setString(7, phone);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Customer customer = new Customer(id, firstName, lastName, streetNumber, streetName, postalCode, city, phone);
                customer.addObserver(this::updateCustomer);
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer getCustomer(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE Customer.id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseCustomer(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Customer> getCustomerList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer")) {
                List<Customer> customers = new LinkedList<>();
                while (resultSet.next())
                    customers.add(parseCustomer(resultSet));
                return Collections.unmodifiableList(customers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Customer> searchCustomers(String search) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(CONCAT('%', ?, '%'))")) {
            statement.setString(1, search);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Customer> customers = new LinkedList<>();
                while (resultSet.next())
                    customers.add(parseCustomer(resultSet));
                return Collections.unmodifiableList(customers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false; // TODO
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return deleteCustomer(customer.getId());
    }

    @Override
    public boolean deleteCustomer(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer WHERE Customer.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Distribution createDistribution(Employee employee, Truck truck, Date date, Agency source, Agency destination, List<Vehicle> vehicles) {
        return null; // TODO
    }

    @Override
    public Distribution getDistribution(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Distribution " +
                "LEFT JOIN Employee ON Employee.id = Distribution.employee_id " +
                "LEFT JOIN Truck ON Truck.license_plate = Distribution.truck_license_plate " +
                "LEFT JOIN Agency AS SourceAgency ON SourceAgency.id = Distribution.source_agency_id " +
                "LEFT JOIN Agency AS DestinationAgency ON DestinationAgency.id = Distribution.destination_agency_id " +
                "WHERE Distribution.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseDistribution(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Distribution> getDistributionList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Distribution" +
                    "LEFT JOIN Employee ON Employee.id = Distribution.employee_id " +
                    "LEFT JOIN Truck ON Truck.license_plate = Distribution.truck_license_plate " +
                    "LEFT JOIN Agency AS SourceAgency ON SourceAgency.id = Distribution.source_agency_id " +
                    "LEFT JOIN Agency AS DestinationAgency ON DestinationAgency.id = Distribution.destination_agency_id"
            )) {
                List<Distribution> distributions = new LinkedList<>();
                while (resultSet.next())
                    distributions.add(parseDistribution(resultSet));
                return Collections.unmodifiableList(distributions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateDistribution(Distribution distribution) {
        return false; // TODO
    }

    @Override
    public boolean deleteDistribution(Distribution distribution) {
        return deleteDistribution(distribution.getId());
    }

    @Override
    public boolean deleteDistribution(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Distribution WHERE Distribution.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Employee createEmployee(Agency agency, String firstName, String lastName, String streetNumber, String streetName, String postalCode, String city, String phone, String passwordHash) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Employee " +
                    "(agency_id, first_name, last_name, street_number, street_name, postal_code, city, phone, password_hash) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, agency.getId());
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, streetNumber);
            statement.setString(5, streetName);
            statement.setString(6, postalCode);
            statement.setString(7, city);
            statement.setString(8, phone);
            statement.setString(9, passwordHash);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Employee employee = new Employee(id, agency, firstName, lastName, streetNumber, streetName, postalCode, city, phone, passwordHash);
                employee.addObserver(this::updateEmployee);
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployee(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Employee " +
                "LEFT JOIN Agency ON Agency.id = Employee.agency_id " +
                "WHERE Employee.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseEmployee(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employee> getEmployeeList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Employee " +
                    "LEFT JOIN Agency ON Agency.id = Employee.agency_id"
            )) {
                List<Employee> employees = new LinkedList<>();
                while (resultSet.next())
                    employees.add(parseEmployee(resultSet));
                return Collections.unmodifiableList(employees);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE Employee SET" +
                "Employee.agency_id = ?, " +
                "Employee.first_name = ?, " +
                "Employee.last_name = ?, " +
                "Employee.street_number = ?, " +
                "Employee.street_name = ?, " +
                "Employee.postal_code = ?, " +
                "Employee.city = ?, " +
                "Employee.phone = ?, " +
                "Employee.password_hash = ? " +
                "WHERE Employee.id = ?"
        )) {
            if (employee.getAgency() != null)
                statement.setInt(1, employee.getAgency().getId());
            else statement.setNull(1, Types.INTEGER);
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getStreetNumber());
            statement.setString(5, employee.getStreetName());
            statement.setString(6, employee.getPostalCode());
            statement.setString(7, employee.getCity());
            statement.setString(8, employee.getPhone());
            statement.setString(9, employee.getPasswordHash());
            statement.setInt(10, employee.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return deleteEmployee(employee.getId());
    }

    @Override
    public boolean deleteEmployee(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE Employee.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Invoice createInvoice(Return aReturn, double amount, double durationPenalty, double fuelCharge, double damagePenalty) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Invoice " +
                    "(return_rental_reservation_id, amount, duration_penalty, fuel_charge, damage_penalty) " +
                    "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, aReturn.getRental().getReservation().getId());
            statement.setDouble(2, amount);
            statement.setDouble(3, durationPenalty);
            statement.setDouble(4, fuelCharge);
            statement.setDouble(5, damagePenalty);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Invoice invoice = new Invoice(id, aReturn, amount, durationPenalty, fuelCharge, damagePenalty);
                invoice.addObserver(this::updateInvoice);
                return invoice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Invoice getInvoice(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Invoice " +
                "LEFT JOIN `Return` ON `Return`.rental_reservation_id = Invoice.return_rental_reservation_id " +
                "WHERE Invoice.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseInvoice(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Invoice> getInvoiceList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Invoice " +
                    "LEFT JOIN `Return` ON `Return`.rental_reservation_id = Invoice.return_rental_reservation_id"
            )) {
                List<Invoice> invoices = new LinkedList<>();
                while (resultSet.next())
                    invoices.add(parseInvoice(resultSet));
                return Collections.unmodifiableList(invoices);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateInvoice(Invoice invoice) {
        return false; // TODO
    }

    @Override
    public boolean deleteInvoice(Invoice invoice) {
        return deleteInvoice(invoice.getId());
    }

    @Override
    public boolean deleteInvoice(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Invoice WHERE Invoice.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LoyaltyProgram createLoyaltyProgram(String name, int duration, String description, double price, double discountRate) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO LoyaltyProgram " +
                    "(name, duration, description, price, discount_rate) " +
                    "VALUES (?, ?, ?, ?, ?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setString(1, name);
            statement.setInt(2, duration);
            statement.setString(3, description);
            statement.setDouble(4, price);
            statement.setDouble(5, discountRate);
            if (statement.executeUpdate() == 0)
                return null;

            LoyaltyProgram loyaltyProgram = new LoyaltyProgram(name, duration, description, price, discountRate);
            loyaltyProgram.addObserver(this::updateLoyaltyProgram);
            return loyaltyProgram;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoyaltyProgram getLoyaltyProgram(String name) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LoyaltyProgram WHERE LoyaltyProgram.name = ?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseLoyaltyProgram(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LoyaltyProgram> getLoyaltyProgramList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM LoyaltyProgram")) {
                List<LoyaltyProgram> loyaltyPrograms = new LinkedList<>();
                while (resultSet.next())
                    loyaltyPrograms.add(parseLoyaltyProgram(resultSet));
                return Collections.unmodifiableList(loyaltyPrograms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        return false; // TODO
    }

    @Override
    public boolean deleteLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        return deleteLoyaltyProgram(loyaltyProgram.getName());
    }

    @Override
    public boolean deleteLoyaltyProgram(String name) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM LoyaltyProgram WHERE LoyaltyProgram.name = ?")) {
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LoyaltySubscription createLoyaltySubscription(Customer customer, LoyaltyProgram loyaltyProgram, Date subscriptionDate) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO LoyaltySubscription " +
                    "(customer_id, loyalty_program_name, subscription_date) " +
                    "VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, customer.getId());
            statement.setString(2, loyaltyProgram.getName());
            statement.setDate(3, new java.sql.Date(subscriptionDate.getTime()));
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                LoyaltySubscription loyaltySubscription = new LoyaltySubscription(id, customer, loyaltyProgram, subscriptionDate);
                loyaltySubscription.addObserver(this::updateLoyaltySubscription);
                return loyaltySubscription;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoyaltySubscription getLoyaltySubscription(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM LoyaltySubscription " +
                "LEFT JOIN Customer ON Customer.id = LoyaltySubscription.customer_id " +
                "LEFT JOIN LoyaltyProgram ON LoyaltyProgram.name = LoyaltySubscription.loyalty_program_name " +
                "WHERE LoyaltySubscription.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseLoyaltySubscription(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LoyaltySubscription> getLoyaltySubscriptionList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM LoyaltySubscription " +
                    "LEFT JOIN Customer ON Customer.id = LoyaltySubscription.customer_id " +
                    "LEFT JOIN LoyaltyProgram ON LoyaltyProgram.name = LoyaltySubscription.loyalty_program_name"
            )) {
                List<LoyaltySubscription> loyaltySubscriptions = new LinkedList<>();
                while (resultSet.next())
                    loyaltySubscriptions.add(parseLoyaltySubscription(resultSet));
                return Collections.unmodifiableList(loyaltySubscriptions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateLoyaltySubscription(LoyaltySubscription loyaltySubscription) {
        return false; // TODO
    }

    @Override
    public boolean deleteLoyaltySubscription(LoyaltySubscription loyaltySubscription) {
        return deleteLoyaltySubscription(loyaltySubscription.getId());
    }

    @Override
    public boolean deleteLoyaltySubscription(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM LoyaltySubscription WHERE LoyaltySubscription.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Quote createQuote(Reservation reservation, double amount) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Quote " +
                    "(reservation_id, amount) " +
                    "VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, reservation.getId());
            statement.setDouble(2, amount);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Quote quote = new Quote(id, reservation, amount);
                quote.addObserver(this::updateQuote);
                return quote;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Quote getQuote(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Quote " +
                "LEFT JOIN Reservation ON Reservation.id = Quote.reservation_id " +
                "WHERE Quote.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseQuote(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Quote> getQuoteList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Quote" +
                    "LEFT JOIN Reservation ON Reservation.id = Quote.reservation_id"
            )) {
                List<Quote> quotes = new LinkedList<>();
                while (resultSet.next())
                    quotes.add(parseQuote(resultSet));
                return Collections.unmodifiableList(quotes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateQuote(Quote quote) {
        return false; // TODO
    }

    @Override
    public boolean deleteQuote(Quote quote) {
        return deleteQuote(quote.getId());
    }

    @Override
    public boolean deleteQuote(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Quote WHERE Quote.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Rental createRental(Reservation reservation, Employee employee, Date date, boolean damageInsurance, boolean accidentInsurance, int duration) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Employee " +
                        "(reservation_id, employee_id, date, damage_insurance, accident_insurance, duration) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setInt(1, reservation.getId());
            statement.setInt(2, employee.getId());
            statement.setDate(3, new java.sql.Date(date.getTime()));
            statement.setBoolean(4, damageInsurance);
            statement.setBoolean(5, accidentInsurance);
            statement.setInt(6, duration);
            if (statement.executeUpdate() == 0)
                return null;

            Rental rental = new Rental(reservation, employee, date, damageInsurance, accidentInsurance, duration);
            rental.addObserver(this::updateRental);
            return rental;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Rental getRental(Reservation reservation) {
        return getRental(reservation.getId());
    }

    @Override
    public Rental getRental(int reservationId) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Rental " +
                "LEFT JOIN Reservation ON Reservation.id = Rental.reservation_id " +
                "LEFT JOIN Employee ON Employee.id = Rental.employee_id " +
                "WHERE Rental.reservation_id = ?"
        )) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseRental(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Rental> getRentalList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Rental " +
                    "LEFT JOIN Reservation ON Reservation.id = Rental.reservation_id " +
                    "LEFT JOIN Employee ON Employee.id = Rental.employee_id"
            )) {
                List<Rental> rentals = new LinkedList<>();
                while (resultSet.next())
                    rentals.add(parseRental(resultSet));
                return Collections.unmodifiableList(rentals);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateRental(Rental rental) {
        return false; // TODO
    }

    @Override
    public boolean deleteRental(Rental rental) {
        return deleteRental(rental.getReservation());
    }

    @Override
    public boolean deleteRental(Reservation reservation) {
        return deleteRental(reservation.getId());
    }

    @Override
    public boolean deleteRental(int reservationId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Rental WHERE Rental.reservation_id = ?")) {
            statement.setInt(1, reservationId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reservation createReservation(Agency agency, Customer customer, Vehicle vehicle, Employee employee, int expectedDuration) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Reservation " +
                    "(agency_id, customer_id, vehicle_license_plate, employee_id, expected_duration) " +
                    "VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setInt(1, agency.getId());
            statement.setInt(2, customer.getId());
            statement.setString(3, vehicle.getLicensePlate());
            statement.setInt(4, employee.getId());
            statement.setInt(5, expectedDuration);
            if (statement.executeUpdate() == 0)
                return null;

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next())
                    throw new SQLException("Creating Database entry failed, no ID obtained.");

                int id = generatedKeys.getInt(1);
                Reservation reservation = new Reservation(id, agency, customer, vehicle, employee, expectedDuration);
                reservation.addObserver(this::updateReservation);
                return reservation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Reservation getReservation(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Reservation " +
                "LEFT JOIN Agency ON Agency.id = Reservation.agency_id " +
                "LEFT JOIN Customer ON Customer.id = Reservation.customer_id " +
                "LEFT JOIN Vehicle ON Vehicle.license_plate = Reservation.vehicle_license_plate " +
                "LEFT JOIN Employee ON Employee.id = Reservation.employee_id " +
                "WHERE Reservation.id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseReservation(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reservation> getReservationList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Reservation " +
                    "LEFT JOIN Agency ON Agency.id = Reservation.agency_id " +
                    "LEFT JOIN Customer ON Customer.id = Reservation.customer_id " +
                    "LEFT JOIN Vehicle ON Vehicle.license_plate = Reservation.vehicle_license_plate " +
                    "LEFT JOIN Employee ON Employee.id = Reservation.employee_id"
            )) {
                List<Reservation> reservations = new LinkedList<>();
                while (resultSet.next())
                    reservations.add(parseReservation(resultSet));
                return Collections.unmodifiableList(reservations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        return false; // TODO
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        return deleteReservation(reservation.getId());
    }

    @Override
    public boolean deleteReservation(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservation WHERE Reservation.id = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Return createReturn(Rental rental, Agency agency, Employee employee, Date date, int fuelConsumption, int damageLevel) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO `Return` " +
                    "(rental_reservation_id, agency_id, employee_id, date, fuel_consumption, damage_level) " +
                    "VALUES (?, ?, ?, ?, ?, ?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setInt(1, rental.getReservation().getId());
            statement.setInt(2, agency.getId());
            statement.setInt(3, employee.getId());
            statement.setDate(4, new java.sql.Date(date.getTime()));
            statement.setInt(5, fuelConsumption);
            statement.setInt(6, damageLevel);
            if (statement.executeUpdate() == 0)
                return null;

            Return aReturn = new Return(rental, agency, employee, date, fuelConsumption, damageLevel);
            aReturn.addObserver(this::updateReturn);
            return aReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Return getReturn(Rental rental) {
        return getReturn(rental.getReservation());
    }

    @Override
    public Return getReturn(Reservation rentalReservation) {
        return getReturn(rentalReservation.getId());
    }

    @Override
    public Return getReturn(int rentalReservationId) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM `Return` " +
                "LEFT JOIN Rental ON Rental.reservation_id = `Return`.rental_reservation_id " +
                "LEFT JOIN Agency ON Agency.id = `Return`.agency_id " +
                "LEFT JOIN Employee ON Employee.id = `Return`.employee_id " +
                "WHERE `Return`.rental_reservation_id = ?"
        )) {
            statement.setInt(1, rentalReservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseReturn(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Return> getReturnList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM `Return` " +
                    "LEFT JOIN Rental ON Rental.reservation_id = `Return`.rental_reservation_id " +
                    "LEFT JOIN Agency ON Agency.id = `Return`.agency_id " +
                    "LEFT JOIN Employee ON Employee.id = `Return`.employee_id"
            )) {
                List<Return> returns = new LinkedList<>();
                while (resultSet.next())
                    returns.add(parseReturn(resultSet));
                return Collections.unmodifiableList(returns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateReturn(Return aReturn) {
        return false; // TODO
    }

    @Override
    public boolean deleteReturn(Return aReturn) {
        return deleteReturn(aReturn.getRental());
    }

    @Override
    public boolean deleteReturn(Rental rental) {
        return deleteReturn(rental.getReservation());
    }

    @Override
    public boolean deleteReturn(Reservation rentalReservation) {
        return deleteReturn(rentalReservation.getId());
    }

    @Override
    public boolean deleteReturn(int rentalReservationId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM `Return` WHERE `Return`.rental_reservation_id = ?")) {
            statement.setInt(1, rentalReservationId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Truck createTruck(String licensePlate) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Truck " +
                    "(license_plate) " +
                    "VALUES (?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setString(1, licensePlate);
            if (statement.executeUpdate() == 0)
                return null;

            Truck truck = new Truck(licensePlate);
            truck.addObserver(this::updateTruck);
            return truck;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Truck getTruck(String licensePlate) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Truck WHERE Truck.license_plate = ?")) {
            statement.setString(1, licensePlate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseTruck(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Truck> getTruckList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Truck")) {
                List<Truck> trucks = new LinkedList<>();
                while (resultSet.next())
                    trucks.add(parseTruck(resultSet));
                return Collections.unmodifiableList(trucks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateTruck(Truck truck) {
        return false; // TODO
    }

    @Override
    public boolean deleteTruck(Truck truck) {
        return deleteTruck(truck.getLicensePlate());
    }

    @Override
    public boolean deleteTruck(String licensePlate) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Truck WHERE Truck.license_plate = ?")) {
            statement.setString(1, licensePlate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Vehicle createVehicle(String licensePlate, String brand, String model, int mileage, boolean automatic, boolean airConditioned, String fuel, Category category) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Vehicle " +
                    "(license_plate, brand, model, mileage, automatic, air_conditioned, fuel, category) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.NO_GENERATED_KEYS
        )) {
            statement.setString(1, licensePlate);
            statement.setString(2, brand);
            statement.setString(3, model);
            statement.setInt(4, mileage);
            statement.setBoolean(5, automatic);
            statement.setBoolean(6, airConditioned);
            statement.setString(7, fuel);
            statement.setString(8, category.getName());
            if (statement.executeUpdate() == 0)
                return null;

            Vehicle vehicle = new Vehicle(licensePlate, brand, model, mileage, automatic, airConditioned, fuel, category);
            vehicle.addObserver(this::updateVehicle);
            return vehicle;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Vehicle getVehicle(String licensePlate) {
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Vehicle " +
                "LEFT JOIN Category ON Category.name = Vehicle.category_name " +
                "WHERE Vehicle.license_plate = ?"
        )) {
            statement.setString(1, licensePlate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    return parseVehicle(resultSet);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vehicle> getVehicleList() {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Vehicle")) {
                List<Vehicle> vehicles = new LinkedList<>();
                while (resultSet.next())
                    vehicles.add(parseVehicle(resultSet));
                return Collections.unmodifiableList(vehicles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        return false; // TODO
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
        return deleteVehicle(vehicle.getLicensePlate());
    }

    @Override
    public boolean deleteVehicle(String licensePlate) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Vehicle WHERE Vehicle.license_plate = ?")) {
            statement.setString(1, licensePlate);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Agency parseAgency(ResultSet resultSet) throws SQLException {
        return parseAgency(resultSet, "Agency");
    }

    private Agency parseAgency(ResultSet resultSet, String tableName) throws SQLException {
        int id = resultSet.getInt(tableName + ".id");
        String name = resultSet.getString(tableName + ".name");
        String phone = resultSet.getString(tableName + ".phone");
        String streetNumber = resultSet.getString(tableName + ".street_number");
        String streetName = resultSet.getString(tableName + ".street_name");
        String postalCode = resultSet.getString(tableName + ".postal_code");
        String city = resultSet.getString(tableName + ".city");
        double longitude = resultSet.getDouble(tableName + ".longitude");
        double latitude = resultSet.getDouble(tableName + ".latitude");
        int capacity = resultSet.getInt(tableName + ".capacity");

        Agency agency = new Agency(id, name, phone, streetNumber, streetName, postalCode, city, longitude, latitude, capacity);
        agency.addObserver(this::updateAgency);
        return agency;
    }

    private Category parseCategory(ResultSet resultSet) throws SQLException {
        return parseCategory(resultSet, "Category");
    }

    private Category parseCategory(ResultSet resultSet, String tableName) throws SQLException {
        String name = resultSet.getString(tableName + ".name");
        double dailyRate = resultSet.getDouble(tableName + ".daily_rate");
        double deposit = resultSet.getDouble(tableName + ".deposit");

        Category category = new Category(name, dailyRate, deposit);
        category.addObserver(this::updateCategory);
        return category;
    }

    private Customer parseCustomer(ResultSet resultSet) throws SQLException {
        return parseCustomer(resultSet, "Customer");
    }

    private Customer parseCustomer(ResultSet resultSet, String tableName) throws SQLException {
        int id = resultSet.getInt(tableName + ".id");
        String firstName = resultSet.getString(tableName + ".first_name");
        String lastName = resultSet.getString(tableName + ".last_name");
        String streetNumber = resultSet.getString(tableName + ".street_number");
        String streetName = resultSet.getString(tableName + ".street_name");
        String postalCode = resultSet.getString(tableName + ".postal_code");
        String city = resultSet.getString(tableName + ".city");
        String phone = resultSet.getString(tableName + ".phone");

        Customer customer = new Customer(id, firstName, lastName, streetNumber, streetName, postalCode, city, phone);
        customer.addObserver(this::updateCustomer);
        return customer;
    }

    private Distribution parseDistribution(ResultSet resultSet) throws SQLException {
        return parseDistribution(resultSet, "Distribution");
    }

    private Distribution parseDistribution(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Employee parseEmployee(ResultSet resultSet) throws SQLException {
        return parseEmployee(resultSet, "Employee");
    }

    private Employee parseEmployee(ResultSet resultSet, String tableName) throws SQLException {
        int id = resultSet.getInt(tableName + ".id");
        Agency agency = parseAgency(resultSet);
        String firstName = resultSet.getString(tableName + ".first_name");
        String lastName = resultSet.getString(tableName + ".last_name");
        String streetNumber = resultSet.getString(tableName + ".street_number");
        String streetName = resultSet.getString(tableName + ".street_name");
        String postalCode = resultSet.getString(tableName + ".postal_code");
        String city = resultSet.getString(tableName + ".city");
        String phone = resultSet.getString(tableName + ".phone");
        String passwordHash = resultSet.getString(tableName + ".password_hash");

        Employee employee = new Employee(id, agency, firstName, lastName, streetNumber, streetName, postalCode, city, phone, passwordHash);
        employee.addObserver(this::updateEmployee);
        return employee;
    }

    private Invoice parseInvoice(ResultSet resultSet) throws SQLException {
        return parseInvoice(resultSet, "Invoice");
    }

    private Invoice parseInvoice(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private LoyaltyProgram parseLoyaltyProgram(ResultSet resultSet) throws SQLException {
        return parseLoyaltyProgram(resultSet, "LoyaltyProgram");
    }

    private LoyaltyProgram parseLoyaltyProgram(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private LoyaltySubscription parseLoyaltySubscription(ResultSet resultSet) throws SQLException {
        return parseLoyaltySubscription(resultSet, "LoyaltySubscription");
    }

    private LoyaltySubscription parseLoyaltySubscription(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Quote parseQuote(ResultSet resultSet) throws SQLException {
        return parseQuote(resultSet, "Quote");
    }

    private Quote parseQuote(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Rental parseRental(ResultSet resultSet) throws SQLException {
        return parseRental(resultSet, "Rental");
    }

    private Rental parseRental(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Reservation parseReservation(ResultSet resultSet) throws SQLException {
        return parseReservation(resultSet, "Reservation");
    }

    private Reservation parseReservation(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Return parseReturn(ResultSet resultSet) throws SQLException {
        return parseReturn(resultSet, "`Return`");
    }

    private Return parseReturn(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Truck parseTruck(ResultSet resultSet) throws SQLException {
        return parseTruck(resultSet, "Truck");
    }

    private Truck parseTruck(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    private Vehicle parseVehicle(ResultSet resultSet) throws SQLException {
        return parseVehicle(resultSet, "Vehicle");
    }

    private Vehicle parseVehicle(ResultSet resultSet, String tableName) throws SQLException {
        return null; // TODO
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
