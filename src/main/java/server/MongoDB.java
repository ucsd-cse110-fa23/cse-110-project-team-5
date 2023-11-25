package server;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import java.util.*;

public class MongoDB {
    Random rand = new Random();
    JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
    String uri = "mongodb+srv://ezhou:vlFgsIIGUyJSwYRw@project.c56b0r4.mongodb.net/?retryWrites=true&w=majority";

    public void createUser(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("110_db");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Document user = new Document("_id", new ObjectId());

            user.append("user_id", rand.nextDouble() * 100)
                   .append("username", username)
                   .append("password", password)
                   .append("recipes", new ArrayList<Document>());

            usersCollection.insertOne(user);
        }

    }

    public String readUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("110_db");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Document user = usersCollection.find(eq("username", username)).first();
            if (user != null) {
                return user.getString("_id");
            } else {
                return "no user found for " + username;
            }
        }
    }

    public void createRecipe(String userId, String recipeName, String tag, String details) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("110_db");
            MongoCollection<Document> recipesCollection = sampleTrainingDB.getCollection("recipes");

            Document recipe = new Document("_id", new ObjectId());

            recipe.append("recipe_id", rand.nextDouble() * 100)
                   .append("user_id", userId) //change this later
                   .append("recipe_name", recipeName)
                   .append("tag", tag)
                   .append("details", details);

            recipesCollection.insertOne(recipe);
        }
    }

    public String readRecipe(String recipeName) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("110_db");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("recipes");

            Document recipe = usersCollection.find(eq("recipe_name", recipeName)).first();
            if (recipe != null) {
                return recipe.getString("_id");
            } else {
                return "no user found for " + recipeName;
            }
        }
    }

    public String readAllRecipes() {

        return "";
    }

    public String updateRecipe() {
        
        return "";
    }

    public void deleteRecipe(String value) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("110_db");
            MongoCollection<Document> recipesCollection = sampleTrainingDB.getCollection("recipes");

            Bson filter = eq("recipe", value);
            DeleteResult result = recipesCollection.deleteOne(filter);
            System.out.println(result);
        }
    }
}
