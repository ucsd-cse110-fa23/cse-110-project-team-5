package server;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDBTest {

    String uri = "mongodb://rsaito:Nimono8871@ac-7nibm9a-shard-00-00.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-01.idfww8h.mongodb.net:27017,ac-7nibm9a-shard-00-02.idfww8h.mongodb.net:27017/?ssl=true&replicaSet=atlas-12jat1-shard-0&authSource=admin&retryWrites=true&w=majority";
    private MongoDB mongoDB;
    private Document user;

    @BeforeEach
    void setUp() {
        this.mongoDB = new MongoDB();
    }

    @Test
    public void testCreateUser() {
        mongoDB.createUser("abcd", "1234");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            this.user = usersCollection.find(eq("username", "abcd")).first();
            
        } catch(Exception e){
        }

        assertEquals(this.user.get("username"), "abcd");
    }
}
