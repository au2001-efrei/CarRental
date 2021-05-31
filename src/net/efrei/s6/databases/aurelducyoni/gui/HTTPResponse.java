package net.efrei.s6.databases.aurelducyoni.gui;

import com.sun.net.httpserver.Headers;

import java.nio.charset.StandardCharsets;

public class HTTPResponse {

    private int code;
    private byte[] body;
    private final Headers headers;

    public HTTPResponse() {
        this((byte[]) null);
    }

    public HTTPResponse(int code) {
        this(code, (byte[]) null);
    }

    public HTTPResponse(String body) {
        this(200, body);
    }

    public HTTPResponse(byte[] body) {
        this(200, body);
    }

    public HTTPResponse(String body, Headers headers) {
        this(200, body, headers);
    }

    public HTTPResponse(byte[] body, Headers headers) {
        this(200, body, headers);
    }

    public HTTPResponse(int code, String body) {
        this(code, body, null);
    }

    public HTTPResponse(int code, byte[] body) {
        this(code, body, null);
    }

    public HTTPResponse(int code, String body, Headers headers) {
        this(code, body != null ? body.getBytes(StandardCharsets.UTF_8) : null, headers);
    }

    public HTTPResponse(int code, byte[] body, Headers headers) {
        this.code = code;
        this.body = body != null ? body : new byte[0];
        this.headers = headers != null ? headers : new Headers();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(String body) {
        setBody(body != null ? body.getBytes(StandardCharsets.UTF_8) : null);
    }

    public void setBody(byte[] body) {
        this.body = body != null ? body : new byte[0];
    }

    public Headers getHeaders() {
        return headers;
    }

}
