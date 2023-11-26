// MOCKING PURPOSE -------- DELETE LATER
package client;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

import java.util.*;

public class MongoDB {
    JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
    String uri = "REPLACE_HERE";
    public boolean createUser(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Document user = new Document("_id", new ObjectId());

            user.append("username", username)
                    .append("password", password)
                    .append("recipe list", new ArrayList<Document>());

            if (this.readUser(username) == null) {
                usersCollection.insertOne(user);
                return true;
            }
            return false;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error In Adding User");
            return false;
        }

    }

    public String[] readUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Document user = usersCollection.find(eq("username", username)).first();
            if (user != null) {
                String[] userInfo = { user.getString("username"), user.getString("password") };
                return userInfo;
            }
            System.out.println("No User Found For " + username);
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error In Reading User");
            return null;
        }
    }

    public void createAndUpdateRecipe(String username, String recipeName, String tag, String details, int id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Document user = usersCollection.find(eq("username", username)).first();

            Document recipe = new Document("_id", id);
            recipe.append("recipe name", recipeName);
            recipe.append("tag", tag);
            recipe.append("details", details);

            Bson update = Updates.addToSet("recipe list", recipe);
            UpdateOptions option = new UpdateOptions().upsert(true);

            usersCollection.updateOne(user, update, option);

        } catch (Exception e) {
            System.out.println("Error In Adding Recipe");
        }
    }

    public String[] readRecipe(String username, int id) {

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Bson filter = eq("_id", id);

            Document recipe = usersCollection.find(eq("username", username)).projection(filter).first();

            if (recipe != null) {
                String[] recipeInfo = { recipe.get("recipeName").toString(),
                        recipe.get("tag").toString(),
                        recipe.get("details").toString() };
                return recipeInfo;
            }
            return null;

        } catch (Exception e) {
            System.out.println("Error In Reading Recipe");
            return null;
        }
    }

    public String readAllRecipes() {

        return "";
    }

    public void deleteRecipe(int id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            Bson filter = eq("_id", id);
            DeleteResult result = usersCollection.deleteOne(filter);
            System.out.println(result);
        }
    }
}
