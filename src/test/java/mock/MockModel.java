package mock;

// Mock class representing a model for handling requests
public class MockModel {

    // Instance of MockServer for simulating server interactions
    MockServer server;

    // Constructor to initialize the MockServer instance
    public MockModel() {
        server = new MockServer();
    }

    // Method to simulate performing a request
    public String performRequest(String method, String route, String query) {
        // Delegate the request handling to the MockServer's route method
        // and retrieve the response
        String response = server.route(method, route, query);

        // Return the response to simulate the result of the request
        return response;
    }
}
