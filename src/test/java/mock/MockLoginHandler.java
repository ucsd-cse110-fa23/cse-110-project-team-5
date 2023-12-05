package mock;

import java.util.HashMap;

public class MockLoginHandler {
    private HashMap<String, String> mockDB = new HashMap<>();

    public MockLoginHandler() {}

    public String handle(String method, String route, String query) {
        if ("GET".equals(method) && "/login".equals(route)) {
            return handleLoginRequest(query);
        } else {
            // Handle other methods and routes or return a default error message
            return "Unsupported method or route";
        }
    }

    private String handleLoginRequest(String query) {
        if (query == null || !query.contains("username=") || !query.contains("password=")) {
            return "Invalid GET request";
        }

        // Extract username and password from the query
        String username = extractQueryValue(query, "username");
        String password = extractQueryValue(query, "password");

        if (username == null || password == null) {
            return "Invalid parameters";
        }

        // Simulate account creation
        mockDB.put(username, password);
        return "Account created for " + username;
    }

    private String extractQueryValue(String query, String key) {
        try {
            int start = query.indexOf(key + "=") + key.length() + 1;
            int end = query.indexOf("&", start);
            if (end == -1) {
                end = query.length();
            }
            return query.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    // This method can be used in unit tests to verify the state of the mock database
    public String getPasswordForUser(String username) {
        return mockDB.get(username);
    }
}
