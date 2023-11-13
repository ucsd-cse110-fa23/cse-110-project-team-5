package mock;

// Mock class for handling GPT requests
public class MockGptRequestHandler {
    
    // Default constructor
    public MockGptRequestHandler() {}

    // Method to handle GPT requests
    public String handle(String method, String route, String query) {
        // Default response for an invalid GET request
        String response = "Invalid GET request";

        // Check if the query is not null
        if (query != null) {
            // Extract information from the query string
            String value = query.substring(query.indexOf("=") + 1);
            
            // Query is expected to be of the form "query=<tokens>,<message to GPT>"
            // Extract tokens and the message to be sent to GPT
            int tokens = Integer.parseInt(value.substring(0, value.indexOf(",")));
            String message = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");

            // Check if the message is not null
            if (message != null) {
                try {
                    // Simulate a successful response from GPT
                    response = "default success response for testing";
                } catch (Exception e) {
                    // Handle the case where an error occurs with ChatGPT
                    response = "Error with ChatGPT";
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

