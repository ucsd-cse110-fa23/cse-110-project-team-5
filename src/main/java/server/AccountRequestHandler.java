package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;

public class AccountRequestHandler implements HttpHandler {

    private Map<String, String> loginData;
    private UserDatabaseService userDbService;

    public AccountRequestHandler(Map<String, String> loginData) {
        this.loginData = loginData;
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

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        String username = postData.substring(0, postData.indexOf(","));
        String password = postData.substring(postData.indexOf(",") + 1);

        // Store data in hashmap
        loginData.put(username, password);
        userDbService.saveUser(username, password);
        String response = "Posted login credentials {" + username + ", " + password + "}";
        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        // Assuming the query parameter is the username
        String username = httpExchange.getRequestURI().getQuery();
        String password = loginData.get(username);
        boolean isUser = userDbService.validateUser(username, password);
        if(isUser) {
            response = "Retrieved login credentials for " + username + ": " + password;
        }
        else {
            response = "No login credentials found for " + username;
        }
        // if (username != null && loginData.containsKey(username)) {
        //     // String password = loginData.get(username);
        //     response = "Retrieved login credentials for " + username + ": " + password;
        //     System.out.println(response);
        // } else {
        //     response = "No login credentials found for " + username;
        // }
        return response;
    }
}