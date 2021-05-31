package net.efrei.s6.databases.aurelducyoni.gui;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.models.Employee;

import java.io.IOException;

public abstract class AuthenticatedView extends View {

    protected final UserController userController;

    public AuthenticatedView(UserController userController) {
        super();
        this.userController = userController;
    }

    public AuthenticatedView(String templateName, UserController userController) throws IOException {
        super(templateName);
        this.userController = userController;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Employee employee = userController.getAuthenticatedEmployee(getCookie(exchange, "Auth-Token"));
        if (employee == null) {
            exchange.getResponseHeaders().set("Location", "/login");
            exchange.sendResponseHeaders(302, 0);
            exchange.getResponseBody().close();
            return;
        }

        super.handle(exchange);
    }

}
