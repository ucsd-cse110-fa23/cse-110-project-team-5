package server;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MongoDBTest {

    String uri = "mongodb://rsaito:Nimono8871@ac-7nibm9a-shard-00-00.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-01.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-02.idfww8h.mongodb.net:27017/?ssl=true&replicaSet=atlas-12jat1-shard-0&authSource=admin&retryWrites=true&w=majority";
    private MongoDB mongoDB;
    private Document user;

    @BeforeEach
    void setup() {
        this.mongoDB = new MongoDB();
    }

    @Test
    public void testCreateUser() {
        mongoDB.createUser("abcd", "1234");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "abcd")).first();

        } catch (Exception e) {}

        assertEquals(this.user.get("username"), "abcd");
    }

    @Test
    public void testReadUser() {
        Document result = mongoDB.readUser("abcd");
        assertEquals(result.get("username"), "abcd");
        assertEquals(result.get("password"), "1234");
    }

    @Test
    public void testCreateRecipe() {
        mongoDB.createAndUpdateRecipe("abcd", "test name", "dinner", "test details");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "abcd")).first();

            ArrayList<Document> recipes = (ArrayList<Document>) this.user.get("recipe_list");
            for (Document x : recipes) {
                if (x.get("recipe_name").toString().equals("test name")) {
                    assertEquals(x.get("recipe_name"), "test name");
                    assertEquals(x.get("tag"), "dinner");
                    assertEquals(x.get("details"), "test details");
                    break;
                }
            }
            
        } catch(Exception e){
        }
    }

    @Test 
    public void testUpdateRecipe() {

    }

    @Test
    public void testReadRecipe() {

    }

    @Test
    public void testReadAllRecipes() {

    }

    @Test
    public void testDeleteRecipe() {

    }

    @AfterAll
    void cleanup() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            usersCollection.deleteOne(eq("username", "abcd"));
        } catch (Exception e) {}
    }
}
