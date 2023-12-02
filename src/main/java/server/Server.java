package server;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {

  // Initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "localhost";

  public static void main(String[] args) throws IOException {
    // Create a thread pool to handle requests
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    // Create a map to store data
    Map<String, String> data = new HashMap<>();

    // Create an HTTP server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    // Define and create contexts for different endpoints, associating them with
    // their respective request handlers
    // Example: HttpContext recipeContext = server.createContext("/recipe", new RequestHandler(data));
    HttpContext GptContext = server.createContext("/gpt", new GptRequestHandler(data));
    HttpContext whisperContext = server.createContext("/whisper", new WhisperRequestHandler(data));
    HttpContext accountContext = server.createContext("/saveUser", new AccountRequestHandler(data));
    HttpContext loginContext = server.createContext("/loginUser", new LoginRequestHandler(data));

    HttpContext MockGptContext = server.createContext("/mockgpt", new MockGptRequestHandler(data));
    HttpContext MockwhisperContext = server.createContext("/mockwhisper", new MockWhisperRequestHandler(data));

    // Set the thread pool executor for the server
    server.setExecutor(threadPoolExecutor);

    // Start the server
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);
  }
}
