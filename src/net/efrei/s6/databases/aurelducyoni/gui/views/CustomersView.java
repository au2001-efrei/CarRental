package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.models.Customer;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomersView extends AuthenticatedView {

    public CustomersView(UserController userController) throws IOException {
        super("customers", userController);
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        assert template != null;
        String body = template;

        List<Map<String, ?>> customers = new LinkedList<>();
        for (Customer customer : userController.getCustomerList())
            customers.add(customer.toJSON());
        body = body.replace("{{customers}}", JSON.stringify(customers));

        return new HTTPResponse(body);
    }

    @Override
    protected HTTPResponse handlePost(HttpExchange exchange) {
        Map<String, String> params = getRequestBody(exchange);

        String firstName = params.get("first_name");
        String lastName = params.get("last_name");
        String streetNumber = params.get("street_number");
        String streetName = params.get("street_name");
        String postalCode = params.get("postal_code");
        String city = params.get("city");
        String phone = params.get("phone");

        if (firstName != null && lastName != null) {
            Customer customer = userController.createCustomer(firstName, lastName, streetNumber, streetName, postalCode, city, phone);
            if (customer != null) {
                HTTPResponse response = new HTTPResponse(303);
                // response.getHeaders().set("Location", "/customer/" + customer.getId());
                response.getHeaders().set("Location", "/customers");
                return response;
            }
        }

        assert template != null;
        String body = template;

        List<Map<String, ?>> customers = new LinkedList<>();
        for (Customer customer : userController.getCustomerList())
            customers.add(customer.toJSON());
        body = body.replace("{{customers}}", JSON.stringify(customers));

        return new HTTPResponse(body);
    }

}
