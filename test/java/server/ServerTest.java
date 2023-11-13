package server;

import mock.MockModel;
import mock.MockServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerTest {
    MockModel model;
    MockServer server;

    @BeforeEach
    void setUp() {
        model = new MockModel();
        server = new MockServer();
    }

    @Test
    public void GptTest() {
        String response = model.performRequest("GET", "gpt", "500,mock_test_for_gpt");
        final String correctResponse = "default success response for testing";
        assertEquals(response, correctResponse);
    }

    @Test
    public void WhisperTest() {
        String response = model.performRequest("GET", "whisper", "voiceinstructions.wav");
        final String correctResponse = "default success response for testing";
        assertEquals(response, correctResponse);
    }
}
