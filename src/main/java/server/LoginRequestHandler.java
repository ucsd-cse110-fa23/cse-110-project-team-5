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

public class LoginRequestHandler implements HttpHandler {
    private Map<String, String> loginData;
    private MongoDB mongoDB;
    private Gson gson = new Gson();

    public LoginRequestHandler(Map<String, String> loginData) {
        this.loginData = loginData;
        this.mongoDB = new MongoDB();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else if (method.equals("POST")) {
                response = handlePost(httpExchange);
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
        // String response = "Invalid GET request";
        String username = httpExchange.getRequestURI().getQuery();
        // Add Luffy's readall method to get the recipe list
        ArrayList<Document> recipes = mongoDB.readAllRecipes(username);
        return gson.toJson(recipes);
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        String username = httpExchange.getRequestURI().getQuery();
        ArrayList<Document> recipes = mongoDB.readAllRecipes(username);
        return gson.toJson(recipes);
    }
}
