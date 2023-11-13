package mock;

import client.Model;

public class MockRecipeGenerate {
    String whisperResponse;
    String mealType;

    public MockRecipeGenerate() {}

    public String getMealType() {
        return this.mealType;
    }

    // Retrieve the response from the 'Whisper' API based on user voice command
    public String retrieveVoiceCommandResponse(String fileString) {
        MockModel model = new MockModel();
        String mod = "";
        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", fileString);
            String mealTypecheck = whisperResponse.toLowerCase();
            // Determine the meal type based on the response content
            if (mealTypecheck.contains("breakfast")) {
                mealType = "breakfast";
            } else if (mealTypecheck.contains("lunch")) {
                mealType = "lunch";
            } else if (mealTypecheck.contains("dinner")) {
                mealType = "dinner";
            }
            // Replace spaces with underscores for subsequent API request formatting
            mod = whisperResponse.replaceAll(" ", "_");
        } catch (Exception e) {
            System.err.println("No input detected");
        }
        return mod;
    }
}
