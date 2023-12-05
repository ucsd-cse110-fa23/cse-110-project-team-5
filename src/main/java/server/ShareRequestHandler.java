package server;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.util.*;

import org.bson.Document;
import org.json.JSONObject;

public class ShareRequestHandler implements HttpHandler {
    // HTTP client for making requests
    HttpClient client;
    MongoDB db;

    // Constructor to initialize the handler with data
    public ShareRequestHandler(Map<String, String> data) {
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

                    if (recipe.getString("recipe_name") == null) {
                        return "No recipe found";
                    }

                    res.put("recipe_name", recipe.getString("recipe_name"));
                    res.put("recipe_tag", recipe.getString("recipe_tag"));
                    res.put("recipe_details", recipe.getString("recipe_details"));
                    res.put("creation_time", recipe.getString("creation_time"));
                    res.put("image_link", recipe.get("image_link"));

                    StringBuilder htmlBuilder = new StringBuilder();
                    htmlBuilder
                            .append("<html>")
                            .append("<body>")
                            .append("<h1>")
                            .append(recipe.getString("recipe_name"))
                            .append("</h1>")
                            .append("<h3>")
                            .append(recipe.getString("recipe_tag"))
                            .append("</h3>")
                            .append("<p>")
                            .append(recipe.getString("recipe_details"))
                            .append("</p>")
                            .append("<img src=\"" + recipe.getString("image_link") + "\">")
                            .append("</body>")
                            .append("</html>");

                    // encode HTML content
                    response = htmlBuilder.toString();
                } catch (Exception e) {
                    response = "Error";
                }
            } else {
                response = "No message query found";
            }
        }

        return response;
    }
}