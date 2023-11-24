package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;

public class LoginRequestHandler implements HttpHandler {

    private Map<String, String> loginData;

    public LoginRequestHandler(Map<String, String> loginData) {
        this.loginData = loginData;
    }
