package server;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserDatabaseService {
    
    private MongoCollection<Document> usersCollection;

    public UserDatabaseService(MongoDatabase database) {
        this.usersCollection = database.getCollection("users");
    }

    public void saveUser(String username, String password) {
        Document newUser = new Document("username", username)
                                .append("password", password);
        usersCollection.insertOne(newUser);
    }

    public boolean validateUser(String username, String password) {
        Document userDocument = usersCollection.find(new Document("username", username)).first();
        if (userDocument != null) {
            if(userDocument.getString(password).equals(password)) {
                return true;
            }
        }
        return false;
    }
}
