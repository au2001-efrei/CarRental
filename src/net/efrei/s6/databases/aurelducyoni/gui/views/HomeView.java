package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;

public class HomeView extends AuthenticatedView {

    public HomeView(UserController userController) {
        super(userController);
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        HTTPResponse response = new HTTPResponse(302);
        response.getHeaders().set("Location", "/reservations");
        return response;
    }

}
