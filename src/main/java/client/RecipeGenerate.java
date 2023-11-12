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
            stopRecord();
        } else {
            startRecord();
        }
        isRecording = !isRecording;
        return isRecording;
    }

    // Initializes and starts the audio recording process
    public void startRecord() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(format);
            targetLine.start();
            recordingLabel.setVisible(true);

            outputFile = new File("voiceinstructions.wav");
            Thread recordThread = new Thread(() -> {
                try {
                    // Write the audio input stream to the specified file
                    AudioSystem.write(new AudioInputStream(targetLine), AudioFileFormat.Type.WAVE, outputFile);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
            recordThread.setDaemon(true);
            recordThread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // Stops the audio recording
    public void stopRecord() {
        targetLine.stop();
        targetLine.close();
        recordingLabel.setVisible(false);
    }

    // Retrieves the response from user voice command by recording voice input to an
    // audio file
    public String getWhisperResponse() {
        Model model = new Model();
        String mod = "";
        try {
            // Perform a request to the 'whisper' API to process the 'voiceinstructions.wav'
            // file
            whisperResponse = model.performRequest("GET", "whisper", "voiceinstructions.wav");
            // Determine the meal type from the response
            String mealTypeCheck = whisperResponse.toLowerCase();
            if (mealTypeCheck.contains("breakfast")) {
                mealType = "breakfast";
            } else if (mealTypeCheck.contains("lunch")) {
                mealType = "lunch";
            } else if (mealTypeCheck.contains("dinner")) {
                mealType = "dinner";
            }
            // Replace spaces with underscores in the response
            mod = whisperResponse.replaceAll(" ", "_");
            System.out.println(mod);
        } catch (Exception e) {
            System.err.println("No input detected");
        }
        return mod;
    }

    // Retrieves the response from the GPT API based on the user's voice command
    public String getResponse() {
        Model model = new Model();
        String gptResponse = "";
        try {
            // Get the ingredients from the 'whisper' API response
            String ingredients = getWhisperResponse();
            // Perform a request to the 'gpt' API with the determined meal type and
            // ingredients
            gptResponse = model.performRequest("GET", "gpt",
                    "500," + recipeIntro + mealType + recipeIntro2 + ingredients);
            System.out.println(gptResponse);
        } catch (Exception e) {
            System.out.println("No input detected");
        }
        return gptResponse;
    }
}
