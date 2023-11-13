package mock;

import mock.MockGptRequestHandler;
import mock.MockWhisperRequestHandler;

public class MockServer {
    MockGptRequestHandler gpt;
    MockWhisperRequestHandler whisper;

    public MockServer() {
        gpt = new MockGptRequestHandler();
        whisper = new MockWhisperRequestHandler();
    }

    public String route(String method, String route, String query) {
        String response = "";
        if (route.equals("gpt")) {
            response = gpt.handle(method, route, query);
        } else if (route.equals("whisper")) {
            response = whisper.handle(method, route, query);
        }
        return response;
    }
}
