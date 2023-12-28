package server;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

// HTTP handler for processing GPT requests
public class GptRequestHandler implements HttpHandler {

    // OpenAI GPT-3 API endpoint and authentication details
    private final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private final String API_KEY = System.getenv("OPENAI_API_KEY");
    private final String MODEL = "text-davinci-003";

    // HTTP client for making requests
    HttpClient client;

    // Constructor to initialize the handler with data
    public GptRequestHandler(Map<String, String> data) {
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

    // Handle GET requests
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract tokens and message from the query
            int tokens = Integer.parseInt(value.substring(0, value.indexOf(",")));
            String message = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");

            if (message != null) {
                try {
                    // Call the askGPT method to get a response
                    response = askGPT(tokens, message);
                } catch (Exception e) {
                    response = "Error with chatgpt";
                }                
            } else {
                response = "No message query found";
            }
        }            
        return response;
    }

    // Ask ChatGPT for a response using a prompt
    public String askGPT(int maxTokens, String prompt) throws IOException, InterruptedException, URISyntaxException {
        // Create a JSON request body with model details and prompt
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP request object
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(new URI(API_ENDPOINT))
            .header("Content-Type", "application/json")
            .header("Authorization", String.format("Bearer %s", API_KEY))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
            .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        // Process the response from ChatGPT
        String responseBody = response.body();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");

        return generatedText;
    }
}
