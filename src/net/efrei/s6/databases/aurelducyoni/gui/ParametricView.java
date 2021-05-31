package net.efrei.s6.databases.aurelducyoni.gui;

import com.sun.net.httpserver.HttpExchange;
import net.efrei.s6.databases.aurelducyoni.controllers.UserController;

import java.io.IOException;

public abstract class ParametricView<T> extends AuthenticatedView {

    private final String prefix;

    public ParametricView(String prefix, UserController userController) {
        super(userController);
        this.prefix = prefix;
    }

    public ParametricView(String prefix, String templateName, UserController userController) throws IOException {
        super(templateName, userController);
        this.prefix = prefix;
    }

    @Override
    protected HTTPResponse handleGet(HttpExchange exchange) {
        T object = getObject(exchange);
        return handleGet(object, exchange);
    }

    @Override
    protected HTTPResponse handlePost(HttpExchange exchange) {
        T object = getObject(exchange);
        return handlePost(object, exchange);
    }

    protected T getObject(HttpExchange exchange) {
        assert exchange.getRequestURI().getPath().startsWith(prefix);
        String slug = exchange.getRequestURI().getPath().substring(prefix.length());
        if (slug.contains("/")) slug = slug.substring(0, slug.indexOf('/'));
        return getObject(slug);
    }

    protected abstract T getObject(String slug);

    protected HTTPResponse handleGet(T object, HttpExchange exchange) {
        return new HTTPResponse(405);
    }

    protected HTTPResponse handlePost(T object, HttpExchange exchange) {
        return new HTTPResponse(405);
    }

}
