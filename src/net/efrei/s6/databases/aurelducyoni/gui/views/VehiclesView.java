package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.RentalController;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.controllers.VehicleController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.models.Rental;
import net.efrei.s6.databases.aurelducyoni.models.Vehicle;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VehiclesView extends AuthenticatedView {

    private final RentalController rentalController;
    private final VehicleController vehicleController;

    public VehiclesView(UserController userController, RentalController rentalController, VehicleController vehicleController) throws IOException {
        super("vehicles", userController);
        this.rentalController = rentalController;
        this.vehicleController = vehicleController;
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        Map<String, String> query = getQuery(exchange);
        String search = query.get("q");

        assert template != null;
        String body = template;

        List<Map<String, ?>> rentals = new LinkedList<>();
        for (Rental rental : rentalController.getOngoingRentalList())
            rentals.add(rental.toJSON());
        List<Map<String, ?>> vehicles = new LinkedList<>();
        for (Vehicle vehicle : vehicleController.searchAvailableVehicles(search))
            vehicles.add(vehicle.toJSON());
        body = body.replace("{{rentals}}", JSON.stringify(rentals));
        body = body.replace("{{vehicles}}", JSON.stringify(vehicles));
        body = body.replace("{{query}}", JSON.stringify(search));

        return new HTTPResponse(body);
    }

}
