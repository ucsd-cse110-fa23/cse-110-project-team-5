package mock;

import java.util.HashMap;

// Mock class for handling account creation requests
public class MockAccountRequestHandler {
    private HashMap<String, String> mockDB = new HashMap<>();

    // Default constructor
    public MockAccountRequestHandler() {}

    // Method to handle account creation requests
    public String handle(String method, String route, String query) {
            // Default response for an invalid GET request
            String response = "Invalid GET request";

            // Check if the query is not null
            if (query != null) {
                // Extract information from the query string
                String username = query.substring(query.indexOf("=") + 1, query.indexOf("&"));
                String password = query.substring(query.indexOf("=", query.indexOf("=") + 1), query.length());
    
                // Check if the account info is not null
                if (username != null && password != null) {
                    try {
                        mockDB.put(username, password);
                        // Simulate a successful response from mongo
                        response = "Account created for " + username;
                    } catch (Exception e) {
                        // Handle the case where an error occurs with ChatGPT
                        response = "Invalid parameters";
                    }                
                } else {
                    // Handle the case where no message query is found
                    response = "No message query found";
                }
            }            
            
            // Return the final response
            return response;
    }
}