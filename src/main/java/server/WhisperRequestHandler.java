package server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.*;

import com.sun.net.httpserver.HttpExchange;

public class WhisperRequestHandler implements HttpHandler {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
    private static final String TOKEN = "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";
    private static final String MODEL = "whisper-1";

    HttpClient client;
    private final Map<String, String> data;

    public WhisperRequestHandler(Map<String, String> data) {
        this.client = HttpClient.newHttpClient();
        this.data = data;
    }

    // Handle incoming HTTP requests
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not a valid request method");
            }
        } catch (Exception e) {
            System.out.println("error");
            response = e.toString();
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    public String handleGet(HttpExchange httpExchange) throws IOException, InterruptedException, URISyntaxException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String currentDir = System.getProperty("user.dir");                     // NEED TO FIX TO RIGHT FILEPATH
            value = currentDir + "/" + value;
            // System.out.println("Current dir using System:" + currentDir);
            if (value != null) {
                File file = new File(value);
                response = askWhisper(file);
            } else {
                response = "no message query found";
            }
        }
        return response;
    }

    public String askWhisper(File file) throws IOException, InterruptedException, URISyntaxException {        
        // Set up HTTP connection
        URL url = new URI(API_ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        
        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; boundary=" + boundary
        );
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        
        // Set up output stream to write request body
        OutputStream outputStream = connection.getOutputStream();
        
        // Write model parameter to request body
        writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
        
        // Write file parameter to request body
        writeFileToOutputStream(outputStream, file, boundary);
        
        // Write closing boundary to request body
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
        
        // Flush and close output stream
        outputStream.flush();
        outputStream.close();
        
        // Get response code
        int responseCode = connection.getResponseCode();

        String out = "";
        
        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            out = handleSuccessResponse(connection);
        } else {
            handleErrorResponse(connection);
        }
        
        // Disconnect connection
        connection.disconnect();

        return out;
    }

    // Helper method to write a parameter to the output stream in multipart form data format
    private static void writeParameterToOutputStream(
        OutputStream outputStream,
        String parameterName,
        String parameterValue,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
            "Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n"
        ).getBytes()
        );
        outputStream.write((parameterValue + "\r\n").getBytes());
    }
    private static void writeFileToOutputStream(
        OutputStream outputStream,
        File file,
        String boundary
        ) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        (
        "Content-Disposition: form-data; name=\"file\"; filename=\"" +
        file.getName() +
        "\"\r\n"
        ).getBytes()
            );
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }

    // Helper method to handle a successful response
    private static String handleSuccessResponse(HttpURLConnection connection)
        throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject responseJson = new JSONObject(response.toString());

        String generatedText = responseJson.getString("text");

        return generatedText;
    }
    
    // Helper method to handle an error response
    private static void handleErrorResponse(HttpURLConnection connection)
        throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream())
        );
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        System.out.println("Error Result: " + errorResult);
    }
}
