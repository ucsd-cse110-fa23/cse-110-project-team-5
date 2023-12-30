package server;

import static com.mongodb.client.model.Filters.eq;

import mock.MockLoginHandler;
// Import necessary classes for the test
import mock.MockModel;
import mock.MockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.bson.Document;

// Test class for the server functionality
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerTest {
    // Instances of MockModel and MockServer for testing
    MockModel model;
    MockServer server;
    MockLoginHandler login;

    String uri = System.getenv("MONGO_URI");

    private MongoDB mongoDB;

    // Set up method to initialize objects before each test
    @BeforeEach
    void setup() {
        model = new MockModel();
        server = new MockServer();
        login = new MockLoginHandler();
        mongoDB = new MongoDB();
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
    @Test
    public void dallETest() {
        // Perform a Whisper request and check the response
        String response = model.performRequest("GET", "dalle", "beef_curry");
        final String correctResponse = "successfully generated image";
        assertEquals(response, correctResponse); // Assert that the response matches the expected value
    }

    // Test case for recipe route
    @Test
    public void RecipeTest() {
        String response = model.performRequest("GET", "recipe", "username,recipe_name"); 
        final String correctResponse = "{\"recipe_tag\":\"Dinner\",\"recipe_name\":\"Beef Curry\",\"recipe_details\":\"default response for recipe details\"}";        // CHANGE LATER ONCE READ RECIPE IS IMPLEMENTED
        assertEquals(response, correctResponse);
    }

    @Test
    public void ShareTest() {
        mongoDB.createUser("ryan reynolds", "deadpool");
        mongoDB.createAndUpdateRecipe("ryan reynolds", "test name", "dinner", "test details", "0000", "test.link");
        Document recipe = mongoDB.readRecipe("ryan reynolds", "test name");

        String response = model.performRequest("GET", "share", "ryan reynolds,test name"); 
        final StringBuilder correctResponse = new StringBuilder();
        correctResponse
                .append("<html>")
                .append("<body>")
                .append("<h1>")
                .append(recipe.getString("recipe_name"))
                .append("</h1>")
                .append("<h3>")
                .append(recipe.getString("recipe_tag"))
                .append("</h3>")
                .append("<p>")
                .append(recipe.getString("recipe_details"))
                .append("</p>")
                .append("<img src=\"" + recipe.getString("image_link") + "\">")
                .append("</body>")
                .append("</html>");       // CHANGE LATER ONCE READ RECIPE IS IMPLEMENTED
        assertEquals(response, correctResponse.toString());
    }

    @AfterAll
    void cleanup() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            usersCollection.deleteOne(eq("username", "ryan reynolds"));
        } catch (Exception e) {}
    }
}
