package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.*;


public class Model {
    public String performRequest(String method, String route, String query) {
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/" + route;
            if (query != null) {
                urlString += "?v=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                // out.write(language + "," + year);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            if (route.equals("gpt")) {
                // String response = in.readLine();
                StringBuilder content = new StringBuilder();
                response = in.readLine();   //get the title of the recipe
                content.append(response);
                while ((response = in.readLine())  != null){    //get recipe details
                    content.append(response);           
                    content.append(System.lineSeparator());
                }
                return content.toString();

            }
            
            // String all = response;
            // while (response != null){
            //     all += response;
            //     response = in.readLine();
            // }
            
            in.close();
            return response; //content.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }
}