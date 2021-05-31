package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.gui.ParametricView;
import net.efrei.s6.databases.aurelducyoni.models.Customer;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;

public class CustomerDetailsView extends ParametricView<Customer> {

    public CustomerDetailsView(UserController userController) throws IOException {
        super("/customer/", "customer_details", userController);
    }

    @Override
    protected HTTPResponse handleGet(Customer customer, HttpExchange exchange) {
        if (customer == null)
            return new HTTPResponse(404);

        assert template != null;
        String body = template;

        body = body.replace("{{customer}}", JSON.stringify(customer.toJSON()));

        return new HTTPResponse(body);
    }

    @Override
    protected Customer getObject(String slug) {
        try {
            return userController.getCustomer(Integer.parseInt(slug));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
