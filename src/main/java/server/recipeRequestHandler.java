package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;

public class RecipeRequestHandler {
    HttpClient client;
    private final Map<String, String> data;

    public WhisperRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
        this.data = data;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
            } else if (method.equals("PUT")) {
                response = handlePut(httpExchange);
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

    public String handleGet(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

    }

    public String handleDelete(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

    }

    public String handlePost(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

    }

    public String handlePut(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {

    }
    
}