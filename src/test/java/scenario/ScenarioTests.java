package scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mock.MockUser;

import java.util.*;

import server.MongoDB;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScenarioTests {
    String uri = System.getenv("MONGO_URI");

    private MongoDB mongoDB;
    private Document user;
    private MockUser userinfo;
    @BeforeEach
    void Setup() {
        this.mongoDB = new MongoDB();
        this.userinfo = new MockUser("Bob Ross", "abcde");
    }

    @Test
    public void Scenario1() {
        // Test create user
        mongoDB.createUser("Mike Ross", "abcde");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "Mike Ross")).first();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        assertEquals(this.user.get("username"), "Mike Ross");
        assertEquals(this.user.get("password"), "abcde");

        // Test log in
        Document readResult = mongoDB.readUser("Mike Ross");
        assertEquals(readResult.get("username"), "Mike Ross");
        assertEquals(readResult.get("password"), "abcde");

        // Test create recipe
        mongoDB.createAndUpdateRecipe("Mike Ross", "Spaghetti and Meatballs", "Dinner", "Yummy Spaghetti and Meatballs", new Date().toString(), "test.link");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "Mike Ross")).first();

            ArrayList<Document> recipes = (ArrayList<Document>) this.user.get("recipe_list");
            for (Document x : recipes) {
                if (x.get("recipe_name").toString().equals("test name")) {
                    assertEquals(x.get("recipe_name"), "Spaghetti and Meatballs");
                    assertEquals(x.get("tag"), "Dinner");
                    assertEquals(x.get("details"), "Yummy Spaghetti and Meatballs");
                    break;
                }
            }
            
        } catch(Exception e){
            System.out.println("Something went wrong");
        }
    }
    @Test
    public void Scenario2() {
        // Test create user
        mongoDB.createUser("Bob Ross", "abcde");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "Bob Ross")).first();

        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        assertEquals(this.user.get("username"), "Bob Ross");
        assertEquals(this.user.get("password"), "abcde");
        //Selects Remember Me
        userinfo.saveLoginState(true);
        //Test if preference is saved 
        assertEquals(userinfo.getSavedUsername(), "Bob Ross");

        // Test create recipe
        mongoDB.createAndUpdateRecipe("Bob Ross", "Chicken Noodle Soup", "Dinner", "Chicken, Noodle", new Date().toString(), "test.link");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "Bob Ross")).first();

            ArrayList<Document> recipes = (ArrayList<Document>) this.user.get("recipe_list");
            for (Document x : recipes) {
                if (x.get("recipe_name").toString().equals("test name")) {
                    assertEquals(x.get("recipe_name"), "Chicken Noodle Soup");
                    assertEquals(x.get("tag"), "Dinner");
                    assertEquals(x.get("details"), "Chicken, Noodle");
                    break;
                }
            }
            
        } catch(Exception e){
            System.out.println("Something went wrong");
        }
    }
    @AfterAll
    void cleanup() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            usersCollection.deleteOne(eq("username", "Mike Ross"));
            usersCollection.deleteOne(eq("username", "Bob Ross"));
        } catch (Exception e) {}
    }
}
