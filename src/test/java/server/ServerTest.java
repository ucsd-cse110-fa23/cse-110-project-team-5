package server;

import mock.MockLoginHandler;
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
    MockLoginHandler login;

    // Set up method to initialize objects before each test
    @BeforeEach
    void setup() {
        model = new MockModel();
        server = new MockServer();
        login = new MockLoginHandler();
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
    @Test
    public void AccountCreationTest() {
        // Perform a GPT request and check the response
        String response = model.performRequest("POST", "saveUser", "username=kevinyan&password=testing");
        final String correctResponse = "Account created for kevinyan";
        assertEquals(response, correctResponse); // Assert that the response matches the expected value
    }
    @Test
    public void LoginTest() {
        // Perform a GPT request and check the response
        String response = login.handle("GET", "/login", "username=testUser&password=testPass");
        assertEquals("Account created for testUser", response);
        assertEquals("testPass", login.getPasswordForUser("testUser"));
    }
    @Test
    public void LoginFailedTest() {
        String response = login.handle("POST", "/login", "username=testUser&password=testPass");
        assertEquals("Unsupported method or route", response);
    }
    @Test
    public void LoginNoUsernameTest() {
        String response = login.handle("GET", "/login", "password=testPass");
        assertEquals("Invalid GET request", response);
    }
    @Test
    public void LoginNoPasswordTest() {
        String response = login.handle("GET", "/login", "username=testUser");
        assertEquals("Invalid GET request", response);
    }
}

