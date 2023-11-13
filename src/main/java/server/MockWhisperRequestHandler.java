package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;

import java.util.*;

// HTTP handler for processing mock Whisper requests
public class MockWhisperRequestHandler implements HttpHandler {

    // HTTP client for making requests
    HttpClient client;

    // Constructor to initialize the handler with data
    public MockWhisperRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
    }

    // Handle incoming HTTP requests
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request received";
        String method = httpExchange.getRequestMethod();

        try {
            // Check the request method
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not a valid request method");
            }
        } catch (Exception e) {
            System.out.println("Error");
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
    public String handleGet(HttpExchange httpExchange) {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);

            // Construct the file path (This needs fixing based on requirements)
            String currentDir = System.getProperty("user.dir");
            value = currentDir + "/" + value;

            if (value != null) {
                // Check if the file exists and provide a default response for testing
                File file = new File(value);
                response = "default response for testing";
            } else {
                response = "No message query found";
            }
        }
        return response;
    }
}
