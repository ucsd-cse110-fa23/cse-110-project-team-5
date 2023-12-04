package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URI;

// Class responsible for making HTTP requests to a server
public class Model {

    // Method to perform an HTTP request
    public String performRequest(String method, String route, String query) {
        // Implement your HTTP request logic here and return the response

        try {
            // Construct the URL for the HTTP request
            String urlString = "http://localhost:8100/" + route;
            if (query != null) {
                urlString += "?v=" + query;
            }
            URL url = new URI(urlString).toURL();

            // Open a connection to the specified URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            // If the HTTP method is POST or PUT, write data to the connection's output
            // stream
            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(query);
                // out.write(language + "," + year);
                out.flush();
                out.close();
            }

            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();

            // If the route is "gpt," parse and concatenate multiple lines of the response
            if (route.equals("gpt")) {
                StringBuilder content = new StringBuilder();
                response = in.readLine(); // Get the title of the recipe
                content.append(response);
                while ((response = in.readLine()) != null) { // Get recipe details
                    content.append(response);
                    content.append(System.lineSeparator());
                }
                return content.toString();
            }
            // Close the BufferedReader
            in.close();

            // Return the response
            return response; // content.toString();
        } catch (Exception ex) {
            // Handle exceptions by printing the stack trace and returning an error message
            return "Error";
        }
    }

    public String sendSignupRequest(String username, String password) {
        try {
            String route = "userInfo";
            String method = "POST";
            String query = "username=" + URLEncoder.encode(username, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8");
            return performRequest(method, route, query);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String sendLoginRequest(String username, String password) {
        try {
            String route = "userInfo";
            String method = "GET";
            String query = "username=" + URLEncoder.encode(username, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8");
            return performRequest(method, route, query);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String sendPostRecipeRequest(String username, Recipe recipe) {
        try {
            System.out.println("SENDING POST REQUEST");
            String route = "recipe";
            String method = "POST";
            String query = "username=" + URLEncoder.encode(username, "UTF-8") +
                    "&Name=" + URLEncoder.encode(recipe.getRecipeName(), "UTF-8") +
                    "&Tag=" + URLEncoder.encode(recipe.getMealType(), "UTF-8") +
                    "&Details=" + URLEncoder.encode(recipe.getRecipeDetails(), "UTF-8") +
                    "&Time=" + URLEncoder.encode(recipe.getTimeString(), "UTF-8") +
                    "&Link=" + URLEncoder.encode(recipe.getImageLink(), "UTF-8");
            return performRequest(method, route, query);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String sendRecipeRetrieveRequest(String username) {
        try {
            String route = "recipe";
            String method = "GET";
            String query = "username=" + URLEncoder.encode(username, "UTF-8");
            return performRequest(method, route, query);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String sendRecipeDeleteRequest(String username, Recipe recipe) {
        try {
            String route = "recipe";
            String method = "DELETE";
            String query = "username=" + URLEncoder.encode(username, "UTF-8") +
                    "&Name=" + URLEncoder.encode(recipe.getRecipeName(), "UTF-8");
            return performRequest(method, route, query);
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String sendImageRetrieveRequest(String contents) {
        try {
            String route = "dalle";
            String method = "GET";
            String query = "link=" + URLEncoder.encode(contents, "UTF-8");
            return performRequest(method, route, query);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
