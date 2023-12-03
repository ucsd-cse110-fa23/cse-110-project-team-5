package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Scanner;
import org.bson.Document;

public class AccountRequestHandler implements HttpHandler {

    private Map<String, String> loginData;
    private MongoDB mongoDB;

    public AccountRequestHandler(Map<String, String> loginData) {
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

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        InputStream inStream = httpExchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String postData = scanner.nextLine();
        // System.out.println(); System.out.println(); System.out.println(); System.out.println(); System.out.println(); System.out.println();
        // System.out.println(httpExchange.getRequestURI().getQuery());
        // System.out.println(postData);
        String username = postData.substring(postData.indexOf("=") + 1, postData.indexOf("&"));
        String password = postData.substring(postData.indexOf("=", postData.indexOf("=") + 1) + 1, postData.length());
        System.out.println(password);
        // Store data in hashmap
        loginData.put(username, password);
        String response;
        if (mongoDB.createUser(username, password)) {
            response = "Posted login credentials {" + username + ", " + password + "}";
        } else {
            response = "registererror";
        }

        System.out.println(response);
        scanner.close();

        return response;
    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "loginerror";
        String httpResponse = httpExchange.getRequestURI().getQuery();
        String username = httpResponse.substring(httpResponse.indexOf("username=") + 9, httpResponse.indexOf("&"));
        Document user = mongoDB.readUser(username);
        String document = user.toString();
        String passwordKey = "password=";
        int passwordStartIndex = document.indexOf(passwordKey);

        if (passwordStartIndex != -1) {
            // Move the start index to the start of the actual password, after "password="
            passwordStartIndex += passwordKey.length();

            // Find the end of the password value, which is either a comma or a closing
            // brace
            int passwordEndIndex = document.indexOf(',', passwordStartIndex);
            if (passwordEndIndex == -1) { // If there's no comma, the password ends before the closing brace
                passwordEndIndex = document.indexOf('}', passwordStartIndex);
            }

            // Extract the password
            String password = document.substring(passwordStartIndex, passwordEndIndex);
            System.out.println("loginrequest output:" + password); // Output: chu
            return password;
        } else {
            System.out.println("Password field not found in the document");
            return response;
        }
    }
}