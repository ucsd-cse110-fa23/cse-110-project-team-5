package mock;

public class MockWhisperRequestHandler {
    public MockWhisperRequestHandler () {}

    public String handle(String method, String route, String query) {
        String response = "Invalid GET request";
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            String currentDir = System.getProperty("user.dir");
            value = currentDir + "/" + value;
            // System.out.println("Current dir using System:" + currentDir);
            if (value != null) {
                response = "default success response for testing";
            } else {
                response = "no message query found";
            }
        }
        return response;
    }
}
