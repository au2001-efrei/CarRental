package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;

import java.io.IOException;
import java.util.Map;

public class StatsView extends AuthenticatedView {

    public StatsView(UserController userController) throws IOException {
        super("stats", userController);
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        assert template != null;
        String body = template;

        // TODO

        return new HTTPResponse(body);
    }

}
