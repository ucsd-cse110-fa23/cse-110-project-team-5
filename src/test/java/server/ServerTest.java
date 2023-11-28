package server;

// Import necessary classes for the test
import mock.MockModel;
import mock.MockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Test class for the server functionality
public class ServerTest {
    // Instances of MockModel and MockServer for testing
    MockModel model;
    MockServer server;

    // Set up method to initialize objects before each test
    @BeforeEach
    void setUp() {
        model = new MockModel();
        server = new MockServer();
    }

    // Test case for GPT-related request
    @Test
    public void GptTest() {
        // Perform a GPT request and check the response
        String response = model.performRequest("GET", "gpt", "500,mock_test_for_gpt");
        final String correctResponse = "default success response for testing";
        assertEquals(response, correctResponse); // Assert that the response matches the expected value
    }

    // Test case for Whisper-related request
    @Test
    public void WhisperTest() {
        // Perform a Whisper request and check the response
        String response = model.performRequest("GET", "whisper", "voiceinstructions.wav");
        final String correctResponse = "breakfast";
        assertEquals(response, correctResponse); // Assert that the response matches the expected value
    }

    // Test case for recipe route
    @Test
    public void RecipeTest() {
        String response = model.performRequest("GET", "recipe", "username,recipe_name"); 
                                                    // CHANGE QUERY ONCE READ RECIPE IS IMPLEMENTED
        final String correctResponse = "{\"recipe_tag\":\"Dinner\",\"recipe_name\":\"Beef Curry\",\"recipe_details\":\"default response for recipe details\"}";        // CHANGE LATER ONCE READ RECIPE IS IMPLEMENTED
        assertEquals(response, correctResponse);
    }
}
