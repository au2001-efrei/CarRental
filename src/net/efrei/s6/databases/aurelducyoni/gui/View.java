package net.efrei.s6.databases.aurelducyoni.gui;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class View implements HttpHandler {

    protected final String template;

    public View() {
        this.template = null;
    }

    public View(String templateName) throws IOException {
        this.template = getTemplate(templateName);
    }

    private static String getTemplate(String name) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream templateStream = classLoader.getResourceAsStream("views/" + name + ".html")) {
            if (templateStream == null)
                throw new FileNotFoundException("Resource views/" + name + ".html is null");
            InputStreamReader templateReader = new InputStreamReader(templateStream, StandardCharsets.UTF_8);
            return new BufferedReader(templateReader).lines().collect(Collectors.joining("\n"));
        }
    }

    protected static Map<String, String> getQuery(HttpExchange exchange) {
        String queryString = exchange.getRequestURI().getQuery();

        Map<String, String> body = new HashMap<>();
        if (queryString.isEmpty())
            return body;

        for (String param : queryString.split("&")) {
            int length = param.indexOf('=');
            String name = length >= 0 ? param.substring(0, length) : param;
            String value = length >= 0 ? param.substring(length + 1) : null;
            try {
                name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
                if (value != null)
                    value = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            body.put(name, value);
        }

        return body;
    }

    protected static Map<String, String> getRequestBody(HttpExchange exchange) {
        InputStreamReader templateReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        String queryString = new BufferedReader(templateReader).lines().collect(Collectors.joining("\n"));

        Map<String, String> body = new HashMap<>();
        if (queryString.isEmpty())
            return body;

        for (String param : queryString.split("&")) {
            int length = param.indexOf('=');
            String name = length >= 0 ? param.substring(0, length) : param;
            String value = length >= 0 ? param.substring(length + 1) : null;
            try {
                name = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
                if (value != null)
                    value = URLDecoder.decode(value, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            body.put(name, value);
        }

        return body;
    }

    protected static Map<String, String> getCookies(HttpExchange exchange) {
        Map<String, String> cookies = new HashMap<>();

        if (!exchange.getRequestHeaders().containsKey("Cookie"))
            return cookies;

        for (String header : exchange.getRequestHeaders().get("Cookie")) {
            for (String cookie : header.split("; ")) {
                int length = cookie.indexOf('=');
                String name = length >= 0 ? cookie.substring(0, length) : cookie;
                String value = length >= 0 ? cookie.substring(length + 1) : null;
                cookies.put(name, value);
            }
        }

        return cookies;
    }

    protected static String getCookie(HttpExchange exchange, String name) {
        if (!exchange.getRequestHeaders().containsKey("Cookie"))
            return null;

        for (String header : exchange.getRequestHeaders().get("Cookie")) {
            for (String cookie : header.split("; ")) {
                if (cookie.startsWith(name + "="))
                    return cookie.substring(name.length() + 1);
            }
        }

        return null;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HTTPResponse response;
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    response = this.handleGet(exchange);
                    break;
                case "POST":
                    response = this.handlePost(exchange);
                    break;
                default:
                    response = new HTTPResponse(405);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new HTTPResponse(500);
        }

        if (response == null)
            return;

        exchange.getResponseHeaders().putAll(response.getHeaders());
        if (!exchange.getResponseHeaders().containsKey("Content-Type") && response.getBody().length > 0)
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(response.getCode(), response.getBody().length);
        exchange.getResponseBody().write(response.getBody());
        exchange.getResponseBody().flush();
        exchange.getResponseBody().close();
    }

    protected HTTPResponse handleGet(HttpExchange exchange) {
        return new HTTPResponse(405);
    }

    protected HTTPResponse handlePost(HttpExchange exchange) {
        return new HTTPResponse(405);
    }

}
