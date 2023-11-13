package mock;

import mock.MockServer;

public class MockModel {
    MockServer server;

    public MockModel() {
        server = new MockServer();
    }

    public String performRequest(String method, String route, String query) {
        String response = server.route(method, route, query);
        return response;
    }
}
