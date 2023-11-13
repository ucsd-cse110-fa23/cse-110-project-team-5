package mock;

// Import necessary classes
import client.Model;

// Mock class for generating recipes based on voice commands
public class MockRecipeGenerate {

    // Variables to store the response from the 'Whisper' API and the determined
    // meal type
    String whisperResponse;
    String mealType;

    // Default constructor
    public MockRecipeGenerate() {
    }

    // Getter method for retrieving the meal type
    public String getMealType() {
        return this.mealType;
    }

    // Method to retrieve the response from the 'Whisper' API based on user voice
    // command
    public String retrieveVoiceCommandResponse(String fileString) {
        // Create an instance of MockModel for simulating model interactions
        MockModel model = new MockModel();

        // Placeholder for the modified response
        String mod = "";

        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", fileString);

            // Convert the response to lowercase for case-insensitive matching
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
            // Handle the case where no input is detected
            System.err.println("No input detected");
        }

        // Return the modified response
        return mod;
    }
}
