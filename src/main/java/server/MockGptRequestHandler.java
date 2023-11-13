package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.util.*;

// HTTP handler for processing mock GPT requests
public class MockGptRequestHandler implements HttpHandler {

    // HTTP client for making requests
    HttpClient client;

    // Constructor to initialize the handler with data
    public MockGptRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
    }

    // Handle incoming HTTP requests
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            // Check the request method
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

        // Send the HTTP response
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    // Handle GET requests
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);

            // Extract tokens and message from the query
            String message = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");

            if (message != null) {
                try {
                    // Provide a default success response for testing
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
