package mock;

// Mock class for handling Whisper requests
public class MockWhisperRequestHandler {

    // Default constructor
    public MockWhisperRequestHandler() {}

    // Method to handle Whisper requests
    public String handle(String method, String route, String query) {
        // Placeholder for the response
        String response = "Invalid GET request";

        // Check if the query is not null
        if (query != null) {
            // Extract information from the query string
            String value = query.substring(query.indexOf("=") + 1);

            // Get the current directory and append it to the file path
            String currentDir = System.getProperty("user.dir");
            value = currentDir + "/" + value;

            // Uncomment the line below for debugging purposes
            // System.out.println("Current dir using System:" + currentDir);

            // Check if the modified value is not null
            if (value != null) {
                // Simulate a response for a successful request
                response = "breakfast";
            } else {
                // Handle the case where no message query is found
                response = "no message query found";
            }
        }

        // Return the final response
        return response;
    }
}

