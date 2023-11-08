package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.*;

public class MockGptRequestHandler implements HttpHandler {
    HttpClient client;
    private final Map<String, String> data;

    public MockGptRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
        this.data = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            // query will be of the form "query=<tokens>,<message to gpt>"
            int tokens = Integer.parseInt(value.substring(0, value.indexOf(",")));
            String message = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");
            if (message != null) {
                try {
                    response = "default success response for testing";
                } catch (Exception e) {
                    response = "Error with chatgpt";
                }                
            } else {
                response = "No message query found";
            }
        }            
        return response;
    }
}
