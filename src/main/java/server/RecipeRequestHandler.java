package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class RecipeRequestHandler implements HttpHandler {
    private Map<String, String> recipeData;
    private MongoDB mongoDB;
    private Gson gson;

    public RecipeRequestHandler(Map<String, String> data){
        this.recipeData = data;
        this.mongoDB = new MongoDB();
        this.gson = new Gson();
    }

@Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }
        byte[] responseByte = response.getBytes();
        httpExchange.sendResponseHeaders(200, responseByte.length);
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(responseByte);
        outStream.close();
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "error";
        String username = httpExchange.getRequestURI().getQuery();
        System.out.println(username);
        username = username.substring(username.indexOf("=") + 10, username.indexOf("&"));
        
        if (mongoDB.readUser(username) != null) {
            ArrayList<Document> recipes = mongoDB.readAllRecipes(username);
            System.out.println(gson.toJson(recipes));
            return gson.toJson(recipes);
        }
        return response;        
    }
}