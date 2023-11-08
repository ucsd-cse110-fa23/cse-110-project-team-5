package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.*;

public class MockWhisperRequestHandler implements HttpHandler{
    HttpClient client;
    private final Map<String, String> data;

    public MockWhisperRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
        this.data = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not a valid request method");
            }
        } catch (Exception e) {
            System.out.println("error");
            response = e.toString();
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    public String handleGet(HttpExchange httpExchange) {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String currentDir = System.getProperty("user.dir");                     // NEED TO FIX TO RIGHT FILEPATH
            value = currentDir + "/" + value;
            // System.out.println("Current dir using System:" + currentDir);
            if (value != null) {
                File file = new File(value);
                response = "default response for testing";
            } else {
                response = "no message query found";
            }
        }
        return response;
    }
}
