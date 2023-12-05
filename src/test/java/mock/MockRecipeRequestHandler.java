package mock;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.bson.Document;
import org.json.JSONObject;

// Mock class for handling GPT requests
public class MockRecipeRequestHandler {
    
    // Default constructor
    public MockRecipeRequestHandler() {}

    // Method to handle GPT requests
    // Handle incoming HTTP requests
    public String handle(String method, String route, String query) {
        String response = "Invalid GET request";

        JSONObject res = new JSONObject();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract tokens and message from the query
            String username = value.substring(0, value.indexOf(","));
            String recipeName = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");


            if (username != null && recipeName != null) {
                try {
                    res.put("recipe_name", "Beef Curry");
                    res.put("recipe_tag", "Dinner");
                    res.put("recipe_details", "default response for recipe details");
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

