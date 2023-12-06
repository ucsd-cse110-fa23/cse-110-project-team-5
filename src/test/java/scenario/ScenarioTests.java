package scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.*;

import server.MongoDB;

public class ScenarioTests {

    String uri = "mongodb://rsaito:Nimono8871@ac-7nibm9a-shard-00-00.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-01.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-02.idfww8h.mongodb.net:27017/?ssl=true&replicaSet=atlas-12jat1-shard-0&authSource=admin&retryWrites=true&w=majority";
    private MongoDB mongoDB;
    private Document user;

    @BeforeEach
    void Setup() {
        this.mongoDB = new MongoDB();
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
    
}
