package mock;

public class MockGptRequestHandler {
    public MockGptRequestHandler() {}

    public String handle(String method, String route, String query) {
        String response = "Invalid GET request";
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            // query will be of the form "query=<tokens>,<message to gpt>"
            int tokens = Integer.parseInt(value.substring(0, value.indexOf(",")));
            String message = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");
            if (message != null) {
                try {
                    response = "default success response for testing";
                } catch (Exception e) {
                    response = "Error with chatgpt";
                }                
            } else {
                response = "No message query found";
            }
        }            
        return response;
    }
}
