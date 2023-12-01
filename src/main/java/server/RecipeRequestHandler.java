package server;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecipeRequestHandler implements HttpHandler {
    // HTTP client for making requests
    HttpClient client;
    MongoDB db;

    // Constructor to initialize the handler with data
    public RecipeRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
        db = new MongoDB();
    }

    // Handle incoming HTTP requests
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            // Check the request method
            if (method.equals("GET")) {
                response = handleGet(httpExchange).toString();
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

        JSONObject res = new JSONObject();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract tokens and message from the query
            String username = value.substring(0, value.indexOf(","));
            String recipeName = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");

            if (username != null && recipeName != null) {
                try {
                    // retrieve recipe data from database
                    Document recipe;
                    recipe = db.readRecipe(username, recipeName);
                    res.put("recipe_name", recipe.getString("recipe_name"));
                    res.put("recipe_tag", recipe.getString("recipe_tag"));
                    res.put("recipe_details", recipe.getString("recipe_details"));
                    res.put("image", recipe.get("recipe_image_link"));
                } catch (Exception e) {
                    response = "Error with chatgpt";
                }
            } else {
                response = "No message query found";
            }
        }
        return res.toString();
    }
}
