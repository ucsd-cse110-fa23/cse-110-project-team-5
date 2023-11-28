// package server;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;

// import org.bson.Document;

// public class UserDatabaseService {
    
//     private MongoCollection<Document> usersCollection;

//     public UserDatabaseService(MongoDatabase database) {
//         this.usersCollection = database.getCollection("users");
//     }

//     public void saveUser(String username, String password) {
//         Document newUser = new Document("username", username)
//                                 .append("password", password);
//         usersCollection.insertOne(newUser);
//     }

//     public boolean validateUser(String username, String password) {
//         Document userDocument = usersCollection.find(new Document("username", username)).first();
//         if (userDocument != null) {
//             if(userDocument.getString(password).equals(password)) {
//                 return true;
//             }
//         }
//         return false;
//     }
//     //Only temp until Luffy finishes readAll
//     public List<Document> getUserRecipes(String username) {
//         Document query = new Document("name", username);
//         Document userDoc = usersCollection.find(query).first();
//         List<HashMap<String, Object>> recipesList = new ArrayList<>();
//         if(userDoc != null) {
//             List<Document> recipes = (List<Document>) userDoc.get("recipes");
//             return recipes;
//         }
//         else {
//             return new ArrayList<>();
//         }
//     }
// }
