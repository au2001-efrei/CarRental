package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;

public class LogoutView extends AuthenticatedView {

    public LogoutView(UserController userController) {
        super(userController);
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        return handlePost(exchange);
    }

    @Override
    protected HTTPResponse handlePost(HttpExchange exchange) {
        HTTPResponse response = new HTTPResponse(302);
        response.getHeaders().set("Location", "/login");
        response.getHeaders().add("Set-Cookie", "Auth-Token=; HttpOnly; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
        return response;
    }

}
