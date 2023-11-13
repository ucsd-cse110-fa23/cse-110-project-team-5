package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.File;
import javax.sound.sampled.*;

// Represents the generation and handling of recipes through voice commands
public class RecipeGenerate extends BorderPane {
    boolean isRecording = false;
    Label recordingLabel = new Label("Recording..."); // Label indicating recording status
    public String mealType; // Meal type determined from voice command
    private String recipeIntro = "Give_me_a_recipe_for_"; // Introductory text for recipe request
    private String recipeIntro2 = "using_only_the_following_ingredients_"; // Additional text for recipe request
    public String whisperResponse; // Response from the 'Whisper' API
    private TargetDataLine targetLine; // Target data line for audio recording
    private File outputFile; // File to store the recorded audio
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    // Constructor for RecipeGenerate
    public RecipeGenerate() {
        ListView<String> recipeList = new ListView<>(); // List view for displaying recipes
        this.setCenter(recipeList);
        HBox footer = new HBox(10);
        Button recordButton = new Button();
        recordingLabel.setStyle(defaultLabelStyle);

        footer.getChildren().addAll(recordButton, recordingLabel);
        this.setBottom(footer);
        recordButton.setOnAction(e -> toggleRecord()); // Toggle recording when the button is pressed
    }

    // Toggle audio recording on/off
    public boolean toggleRecord() {
        if (isRecording) {
            stopAudioRecording(); // Stop recording if already recording
        } else {
            startAudioRecording(); // Start recording if not already recording
        }
        isRecording = !isRecording;
        return isRecording;
    }

    // Initialize and start the audio recording process
    public void startAudioRecording() {
        try {
            // Define the audio format with sample rate of 44100 Hz, 16 bits per sample,
            // mono, signed audio with little-endian byte order
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            // Set up the DataLine information with the specified audio format
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            // Get and open the target data line for capturing audio
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(format);
            // Start capturing audio data
            targetLine.start();
            // Make the recording label visible on the UI
            recordingLabel.setVisible(true);

            // Create the output file where the audio data will be saved
            outputFile = new File("voiceinstructions.wav");

            // Create and start a new thread to write the audio data to a file
            Thread recordThread = new Thread(() -> {
                try {
                    // Write the audio input stream to the output file in WAVE file format
                    AudioSystem.write(new AudioInputStream(targetLine), AudioFileFormat.Type.WAVE, outputFile);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });

            // Set the thread as a daemon so it does not prevent the application from
            // exiting
            recordThread.setDaemon(true);
            // Start the recording thread
            recordThread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // Stop audio recording
    public void stopAudioRecording() {
        targetLine.stop();
        targetLine.close();
        recordingLabel.setVisible(false); // Make the recording label invisible on the UI
    }

    // Retrieve the response from the 'Whisper' API based on user voice command
    public String retrieveVoiceCommandResponse() {
        Model model = new Model();
        String mod = "";
        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", "voiceinstructions.wav");
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

    // Generate a recipe based on the voice input from the user
    public String fetchGeneratedRecipe() {
        Model model = new Model();
        String gptResponse = "";
        try {
            // Retrieve ingredients from the voice command response
            String ingredients = retrieveVoiceCommandResponse();
            // Construct and perform a GET request to the 'gpt' endpoint with the necessary
            // parameters
            gptResponse = model.performRequest("GET", "gpt",
                    "500," + recipeIntro + mealType + recipeIntro2 + ingredients);
        } catch (Exception e) {
            System.out.println("No input detected");
        }
        return gptResponse;
    }
}
