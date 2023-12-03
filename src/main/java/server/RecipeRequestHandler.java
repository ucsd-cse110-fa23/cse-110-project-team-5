package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.bson.Document;

public class RecipeRequestHandler implements HttpHandler {
    private Map<String, String> recipeData;
    private MongoDB mongoDB;
    private Gson gson;

    public RecipeRequestHandler(Map<String, String> data) {
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
            } else if (method.equals("POST")) {
                handlePost(httpExchange);
            } else if (method.equals("DELETE")) {
                handleDelete(httpExchange);
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
        String query = httpExchange.getRequestURI().getQuery();
        String username = query.substring(query.indexOf("username=") + 9, query.length());
        if (mongoDB.readUser(username) != null) {
            ArrayList<Document> recipes = mongoDB.readAllRecipes(username);
            return gson.toJson(recipes);
        }
        return "ERROR";
    }

    private void handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String query = scanner.nextLine();

        String username = query.substring(query.indexOf("username=") + 9, query.indexOf("&"));
        String recipeName = query.substring(query.indexOf("Name=") + 5, query.indexOf("&", query.indexOf("&") + 1));
        String recipeTag = query.substring(query.indexOf("Tag=") + 4, query.indexOf("&", query.indexOf("&", query.indexOf("&") + 1) + 1));
        String recipeDetails = query.substring(query.indexOf("Details=") + 8, query.indexOf("&", query.indexOf("&", query.indexOf("&", query.indexOf("&") + 1) + 1) + 1));
        String creationTime = query.substring(query.indexOf("Time=") + 5, query.length());

        mongoDB.createAndUpdateRecipe(URLDecoder.decode(username, "UTF-8"), URLDecoder.decode(recipeName, "UTF-8"), URLDecoder.decode(recipeTag, "UTF-8"), URLDecoder.decode(recipeDetails, "UTF-8"), URLDecoder.decode(creationTime, "UTF-8"));
    }

    private void handleDelete(HttpExchange httpExchange) throws IOException {
        String query = httpExchange.getRequestURI().getQuery();
        
        String username = query.substring(query.indexOf("username=") + 9, query.indexOf("&"));
        String recipeName = query.substring(query.indexOf("Name=") + 5, query.length());

        mongoDB.deleteRecipe(URLDecoder.decode(username, "UTF-8"), URLDecoder.decode(recipeName, "UTF-8"));
    }
}
