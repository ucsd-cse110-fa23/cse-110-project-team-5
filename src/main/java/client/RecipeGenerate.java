package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.File;
import javax.sound.sampled.*;

// The RecipeGenerate class handles voice recording, user input processing, and API requests
public class RecipeGenerate extends BorderPane {
    private boolean isRecording = false;
    Label recordingLabel = new Label("Recording...");
    public String mealType; // The determined meal type from the user's voice command
    private String recipeIntro = "Give_me_a_recipe_for_";
    private String recipeIntro2 = "using_only_the_following_ingredients_";
    public String whisperResponse; // The response from the 'Whisper' API
    private TargetDataLine targetLine; // Audio input line for recording
    private File outputFile; // File to write the recorded audio to
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    // Constructor for RecipeGenerate
    public RecipeGenerate() {
        ListView<String> recipeList = new ListView<>();
        this.setCenter(recipeList);
        HBox footer = new HBox(10);
        Button recordButton = new Button();
        recordingLabel.setStyle(defaultLabelStyle);

        footer.getChildren().addAll(recordButton, recordingLabel);
        this.setBottom(footer);
        recordButton.setOnAction(e -> toggleRecord());
    }

    // Toggles the recording state and returns the new state
    public boolean toggleRecord() {
        if (isRecording) {
            stopAudioRecording();
        } else {
            startAudioRecording();
        }
        isRecording = !isRecording;
        return isRecording;
    }
    /**
     * Initializes and starts the audio recording process.
     * This method sets up audio recording as well as creates the file to write to
     * and starts a new thread to handle the audio input stream. The input stream is
     * written to "voiceinstructions.wav".
     * The label on the UI is also updated to indicate that recording has started.
     */
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

            // Set the thread as a daemon so it does not prevent the application from exiting
            recordThread.setDaemon(true);
            // Start the recording thread
            recordThread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void stopAudioRecording() {
        targetLine.stop();
        targetLine.close();
        recordingLabel.setVisible(false);
    }
    /**
     * Retrieves the response from user voice command by recording voice input to an audio file.
     * Method sends a request to the 'whisper' API to process the 'voiceinstructions.wav' file, 
     * while simultaneously determines the meal type. 
     *
     * @return The voice input as a String from the 'Whisper' API, with spaces replaced
     * by underscores.
     */
    public String retrieveVoiceCommandResponse() {
        Model model = new Model();
        String mod = "";
        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", "voiceinstructions.wav");
            String mealTypecheck = whisperResponse.toLowerCase();
            // Determine the meal type based on the response content
            if(mealTypecheck.contains("breakfast")) {
                mealType = "breakfast";
            } else if (mealTypeCheck.contains("lunch")) {
                mealType = "lunch";
            } else if (mealTypeCheck.contains("dinner")) {
                mealType = "dinner";
            }
            // Replace spaces with underscores for subsequent API request formatting
            mod = whisperResponse.replaceAll(" ", "_");
        }
        catch (Exception e) {
            System.err.println("No input detected");
        }
        return mod;
    }

    /**
     * Generates a recipe based on the voice input from the user by
     * passing voice input to the 'GPT' API, which generates a recipe.
     * The meal type and ingredients from the voice command response
     * are included in the request.
     *
     * @return The recipe generated from the 'GPT' API, or an empty string
     * if an exception occurs.
     */
    public String fetchGeneratedRecipe() {
        Model model = new Model();
        String gptResponse = "";
        try {
            // Retrieve ingredients from the voice command response
            String ingredients = retrieveVoiceCommandResponse();
            // Construct and perform a GET request to the 'gpt' endpoint with the necessary parameters
            gptResponse = model.performRequest("GET", "gpt", "500," + recipeIntro + mealType + recipeIntro2 + ingredients);
        } catch (Exception e) {
            System.out.println("No input detected");
        }
        return gptResponse;
    }
}
