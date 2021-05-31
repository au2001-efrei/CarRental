package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.gui.View;
import net.efrei.s6.databases.aurelducyoni.models.Employee;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LogoutView extends AuthenticatedView {

    public LogoutView(UserController userController) {
        super(userController);
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        HTTPResponse response = new HTTPResponse(302);
        response.getHeaders().set("Location", "/");
        return response;
    }

    @Override
    protected HTTPResponse handlePost(HttpExchange exchange) {
        HTTPResponse response = new HTTPResponse(302);
        response.getHeaders().set("Location", "/login");
        response.getHeaders().add("Set-Cookie", "Auth-Token=; HttpOnly; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
        return response;
    }

}
