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
        mongoDB.createUser("abcd", "1234");
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
        mongoDB.createAndUpdateRecipe("abcd", "test update name", "dinner", "test update details");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "abcd")).first();

            ArrayList<Document> recipes = (ArrayList<Document>) this.user.get("recipe_list");
            for (Document x : recipes) {
                if (x.get("recipe_name").toString().equals("test name")) {
                    assertEquals(x.get("recipe_name"), "test update name");
                    assertEquals(x.get("tag"), "dinner");
                    assertEquals(x.get("details"), "test update details");
                    break;
                }
            }
            
        } catch(Exception e){
        }

        mongoDB.createAndUpdateRecipe("abcd", "test update name", "new tag", "new details");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "abcd")).first();

            ArrayList<Document> recipes = (ArrayList<Document>) this.user.get("recipe_list");
            for (Document x : recipes) {
                if (x.get("recipe_name").toString().equals("test name")) {
                    assertEquals(x.get("recipe_name"), "test update name");
                    assertEquals(x.get("tag"), "new tag");
                    assertEquals(x.get("details"), "new details");
                    break;
                }
            }
            
        } catch(Exception e){
        }
    }

    @Test
    public void testReadRecipe() {
        mongoDB.createUser("test read", "1234567890");
        
        mongoDB.createAndUpdateRecipe("test read", "test read recipe", "lunch", "test read recipe details");

        Document result = mongoDB.readRecipe("test read", "test read recipe");
        assertEquals(result.get("recipe_name"), "test read recipe");
        assertEquals(result.get("recipe_tag"), "lunch");
        assertEquals(result.get("recipe_details"), "test read recipe details");
    }

    @Test
    public void testReadAllRecipes() {
        mongoDB.createUser("test7", "777");
        mongoDB.createAndUpdateRecipe("test7", "recipe 1", "lunch", "details 1");

        ArrayList<Document> result = mongoDB.readAllRecipes("test7");
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteRecipe() {
        mongoDB.createUser("test delete", "delete");
        mongoDB.createAndUpdateRecipe("test delete", "recipe delete", "lunch", "details delete");

        ArrayList<Document> result = mongoDB.readAllRecipes("test delete");
        assertEquals(1, result.size());

        mongoDB.deleteRecipe("test delete", "recipe delete");

        ArrayList<Document> afterDeleteResult = mongoDB.readAllRecipes("test delete");
        assertEquals(0, afterDeleteResult.size());
    }

    @AfterAll
    void cleanup() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            usersCollection.deleteOne(eq("username", "abcd"));
            usersCollection.deleteOne(eq("username", "test7"));
            usersCollection.deleteOne(eq("username", "test delete"));
            usersCollection.deleteOne(eq("username", "test read"));
        } catch (Exception e) {}
    }
}
