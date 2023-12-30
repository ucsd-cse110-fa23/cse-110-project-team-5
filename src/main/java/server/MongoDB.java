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
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;


import java.util.*;

public class MongoDB {

    JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
    String uri = System.getenv("MONGO_URI");
    public boolean createUser(String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");

            Document user = new Document("_id", new ObjectId());

            user.append("username", username)
                    .append("password", password)
                    .append("recipe_list", new ArrayList<Document>());

            if (this.readUser(username) == null) {
                usersCollection.insertOne(user);
                return true;
            }
            System.out.println("User Already Exists");
            return false;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error In Adding User");
            return false;
        }

    }

    public Document readUser(String username) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");

            Document user = usersCollection.find(eq("username", username)).first();
            if (user != null) {
                return user;
            }
            System.out.println("No User Found For " + username);
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error In Reading User");
            return null;
        }
    }

    public void createAndUpdateRecipe(String username, String recipeName, String tag, String details, String time, String imageLink) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");

            Document user = usersCollection.find(eq("username", username)).first();

            Document recipe = new Document("_id", new ObjectId());
            recipe.append("recipe_name", recipeName);
            recipe.append("recipe_tag", tag);
            recipe.append("recipe_details", details);
            recipe.append("creation_time", time);
            recipe.append("image_link", imageLink);

            Bson update = Updates.addToSet("recipe_list", recipe);
            UpdateOptions option = new UpdateOptions().upsert(true);

            usersCollection.updateOne(user, update, option);

        } catch (Exception e) {
            System.out.println("Error In Adding Recipe");
        }
    }

    public Document readRecipe(String username, String recipeName) {
        try {
            ArrayList<Document> recipeList = readAllRecipes(username);
            for (Document x : recipeList) {
                if (x.get("recipe_name").toString().equals(recipeName)) {
                    return x;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error In Reading Recipe");
            return null;
        }
    }

    public ArrayList<Document> readAllRecipes(String username) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");

            Document user = usersCollection.find(eq("username", username)).first();

            ArrayList<Document> recipeList = (ArrayList<Document>) user.get("recipe_list");
            return recipeList;
        } catch (Exception e) {
            System.out.println("Error In Reading Recipe");
            return null;
        }
    }

    public void deleteRecipe(String username, String recipeName) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");

            Document user = usersCollection.find(eq("username", username)).first();
            ArrayList<Document> recipeList = readAllRecipes(username);

            for (Document x : recipeList) {
                if (x.get("recipe_name").toString().equals(recipeName)) {
                    recipeList.remove(x);
                    break;
                }
            }

            Bson update = Updates.set("recipe_list", recipeList);
            usersCollection.updateOne(user, update);

        } catch (Exception e) {
            System.out.println("Error In Deleting Recipe");
        }
    }

    public void dropCollection() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("Pantry_Pal");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("Users");
            usersCollection.drop();
        } catch (Exception e) {
            System.out.println("Error In Dropping Collection");
        }
    }

}
