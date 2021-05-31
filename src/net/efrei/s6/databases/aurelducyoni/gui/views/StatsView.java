package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.StatsController;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.AuthenticatedView;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.models.Customer;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatsView extends AuthenticatedView {

    private final StatsController statsController;

    public StatsView(UserController userController, StatsController statsController) throws IOException {
        super("stats", userController);
        this.statsController = statsController;
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        assert template != null;
        String body = template;

        body = body.replace("{{customers}}", JSON.stringify(statsController.getGoldCustomerList()));
        body = body.replace("{{agencies}}", JSON.stringify(new ArrayList<>()));
        body = body.replace("{{turnovers}}", JSON.stringify(statsController.getAgenciesTurnovers()));

        return new HTTPResponse(body);
    }

}
