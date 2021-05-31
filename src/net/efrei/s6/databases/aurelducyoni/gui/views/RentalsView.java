package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.RentalController;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.controllers.VehicleController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.models.Agency;
import net.efrei.s6.databases.aurelducyoni.models.Customer;
import net.efrei.s6.databases.aurelducyoni.models.Rental;
import net.efrei.s6.databases.aurelducyoni.models.Vehicle;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RentalsView extends AuthenticatedView {

    private final RentalController rentalController;
    private final VehicleController vehicleController;

    public RentalsView(UserController userController, RentalController rentalController, VehicleController vehicleController) throws IOException {
        super("rentals", userController);
        this.rentalController = rentalController;
        this.vehicleController = vehicleController;
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        assert template != null;
        String body = template;

        List<Map<String, ?>> customers = new LinkedList<>();
        for (Customer customer : userController.getCustomerList())
            customers.add(customer.toJSON());
        List<Map<String, ?>> vehicles = new LinkedList<>();
        for (Vehicle vehicle : vehicleController.getAvailableVehiclesList())
            vehicles.add(vehicle.toJSON());
        List<Map<String, ?>> agencies = new LinkedList<>();
        for (Agency agency : rentalController.getAgencyList())
            agencies.add(agency.toJSON());
        List<Map<String, ?>> rentals = new LinkedList<>();
        for (Rental rental : rentalController.getOngoingRentalList())
            rentals.add(rental.toJSON());
        body = body.replace("{{customers}}", JSON.stringify(customers));
        body = body.replace("{{vehicles}}", JSON.stringify(vehicles));
        body = body.replace("{{agencies}}", JSON.stringify(agencies));
        body = body.replace("{{rentals}}", JSON.stringify(rentals));

        return new HTTPResponse(body);
    }

}
