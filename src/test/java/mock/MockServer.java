package mock;

import mock.MockGptRequestHandler;
import mock.MockWhisperRequestHandler;
import server.DallERequestHandler;

// Mock class representing a server for handling GPT and Whisper requests
public class MockServer {

    // Instances of MockGptRequestHandler and MockWhisperRequestHandler for simulating request handling
    MockGptRequestHandler gpt;
    MockWhisperRequestHandler whisper;
    MockAccountRequestHandler account;    
    MockRecipeRequestHandler recipe;

    MockDallERequestHandler dalle;

    // Constructor to initialize the MockGptRequestHandler and MockWhisperRequestHandler instances
    public MockServer() {
        gpt = new MockGptRequestHandler();
        whisper = new MockWhisperRequestHandler();
        account = new MockAccountRequestHandler();
        recipe = new MockRecipeRequestHandler();
        dalle = new MockDallERequestHandler();
    }

    // Method to route requests based on the provided route
    public String route(String method, String route, String query) {
        // Placeholder for the response
        String response = "";

        // Check the provided route and delegate the request handling accordingly
        if (route.equals("gpt")) {
            response = gpt.handle(method, route, query);
        } else if (route.equals("whisper")) {
            response = whisper.handle(method, route, query);
        } else if (route.equals("saveUser")) {
            response = account.handle(method, route, query);
        } else if (route.equals("recipe")) {
            response = recipe.handle(method, route, query);
        } else if (route.equals("dalle")) {
            response = dalle.handle(method, route, query);
        }

        // Return the response after handling the request
        return response;
    }
}

