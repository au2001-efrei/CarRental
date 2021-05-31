package net.efrei.s6.databases.aurelducyoni.gui.views;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;
import net.efrei.s6.databases.aurelducyoni.gui.HTTPResponse;
import net.efrei.s6.databases.aurelducyoni.gui.View;
import net.efrei.s6.databases.aurelducyoni.models.Employee;
import net.efrei.s6.databases.aurelducyoni.utils.JSON;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginView extends View {

    private final UserController userController;

    public LoginView(UserController userController) throws IOException {
        super("login");
        this.userController = userController;
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        Employee employee = userController.getAuthenticatedEmployee(getCookie(exchange, "Auth-Token"));
        if (employee != null) {
            HTTPResponse response = new HTTPResponse(302);
            response.getHeaders().set("Location", "/");
            return response;
        }

        assert template != null;
        String body = template;

        List<Map<String, ?>> employees = new LinkedList<>();
        for (Employee user : userController.getEmployeeList())
            employees.add(user.toJSON());
        body = body.replace("{{employees}}", JSON.stringify(employees));
        body = body.replace("{{error}}", JSON.stringify((String) null));
        body = body.replace("{{selected_id}}", JSON.stringify((Number) null));

        return new HTTPResponse(body);
    }

    @Override
    protected HTTPResponse handlePost(HttpExchange exchange) {
        Map<String, String> params = getRequestBody(exchange);

        Integer employeeId = params.containsKey("employee_id") ? Integer.parseInt(params.get("employee_id")) : null;
        String password = params.get("password");

        String error;
        if (employeeId != null) {
            Employee employee = userController.getEmployee(employeeId);
            if (employee != null) {
                if (employee.checkPassword(password)) {
                    UUID token = userController.getAuthenticationToken(employee);
                    HTTPResponse response = new HTTPResponse(302);
                    response.getHeaders().set("Location", "/");
                    response.getHeaders().add("Set-Cookie", "Auth-Token=" + token + "; HttpOnly");
                    return response;
                } else error = "Invalid password.";
            } else error = "Unknown employee ID.";
        } else error = "Please select your employee ID.";

        assert template != null;
        String body = template;

        List<Map<String, ?>> employees = new LinkedList<>();
        for (Employee user : userController.getEmployeeList())
            employees.add(user.toJSON());
        body = body.replace("{{employees}}", JSON.stringify(employees));
        body = body.replace("{{error}}", JSON.stringify(error));
        body = body.replace("{{selected_id}}", JSON.stringify(employeeId));

        return new HTTPResponse(body);
    }

}
