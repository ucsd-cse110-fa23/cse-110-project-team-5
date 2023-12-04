package mock;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;

public class MockDallERequestHandler {

    public MockDallERequestHandler() {}

    // Handle incoming HTTP requests
    public String handle(String method, String route, String query) {
        String response = "Invalid GET request";

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract prompt from query
            String message = value.replaceAll("_", " ");
            // String message = URLDecoder.decode(value, "UTF-8");

            if (message != null) {
                try {
                    // Call the askGPT method to get a response
                    response = "successfully generated image";
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "Error with DallE";
                }                
            } else {
                response = "No message query found";
            }
        }            
        return response;
    }
}
