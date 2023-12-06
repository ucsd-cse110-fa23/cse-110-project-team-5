package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONObject;

public class DallERequestHandler implements HttpHandler {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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";
    private static final String MODEL = "dall-e-2";
    
    // HTTP client for making requests
    HttpClient client;

    // Constructor to initialize the handler with data
    public DallERequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
    }

    // Handle incoming HTTP requests
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            // Check the request method
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

        // Send the HTTP response
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    public String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract prompt from query
            // String message = value.replaceAll("_", " ");
            String message = URLDecoder.decode(value, "UTF-8");

            if (message != null) {
                try {
                    // Call the askGPT method to get a response
                    response = generateImage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "Error with DallE";
                }                
            } else {
                response = "No message query found";
            }
        }            
        return response;
    }

    public String generateImage(String prompt) throws IOException, InterruptedException, URISyntaxException {
        int n = 1;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");

        // Create the request object
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(URI.create(API_ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", String.format("Bearer %s", API_KEY))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);

        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
   
        System.out.println("DALL-E Response:");
        System.out.println(generatedImageURL);

        try {
            Files.delete(Paths.get("image.jpg"));
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", Paths.get("image.jpg"));
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", Paths.get("image.jpg"));
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }

        try (InputStream in = new URI(generatedImageURL).toURL().openStream()) {
            Files.copy(in, Paths.get("image.jpg"));
        }

        return generatedImageURL;
    }
}
