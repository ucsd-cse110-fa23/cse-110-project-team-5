package client;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Recorder {
    private RecipeGenerate recipeGen;

    private TargetDataLine targetLine; // Target data line for audio recording
    private File outputFile; // File to store the recorded audio
    private String whisperResponse; // Response from the 'Whisper' API

    private String mealType;
    private String mealTypeCheck;
    private boolean isRecording;

    public Recorder() {
        recipeGen = new RecipeGenerate();
        isRecording = false;
    }

    public String processIngredients() {
        boolean isRecording = toggleRecord();
        if (!isRecording) {
            String ingredients = retrieveVoiceCommandResponse("voiceinstructions.wav");
            return recipeGen.fetchGeneratedRecipe(ingredients, mealType);
        }
        return null;
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
            // Create the output file where the audio data will be saved
            String filePath = "voiceinstructions.wav";
            // "src" + File.separator + "main" + File.separator + "java" + File.separator +
            // "voiceinstructions.wav";
            outputFile = new File(filePath);

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
        if (targetLine != null) {
            targetLine.stop();
            targetLine.close();
        }
    }

    public void recordMealType(Text instructions, Button ingredientButton) {
        isRecording = toggleRecord();
        Platform.runLater(() -> {
            if (!isRecording) {
                mealTypeCheck = retrieveVoiceCommandResponse("voiceinstructions.wav").toLowerCase();
                if (mealTypeCheck.contains("breakfast") || mealTypeCheck.contains("lunch")
                        || mealTypeCheck.contains("dinner")) {
                    ingredientButton.setDisable(false);
                    instructions.setText("Tell me your ingredients!");
                } else {
                    instructions.setText("Please repeat the meal type (Breakfast, Lunch, or Dinner)");
                }
            }
        });
    }

    // Retrieve the response from the 'Whisper' API based on user voice command
    public String retrieveVoiceCommandResponse(String fileString) {
        Model model = new Model();
        String mod = "";
        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", fileString);
            mealTypeCheck = whisperResponse.toLowerCase();
            // Determine the meal type based on the response content
            if (mealTypeCheck.contains("breakfast")) {
                mealType = "Breakfast";
            } else if (mealTypeCheck.contains("lunch")) {
                mealType = "Lunch";
            } else if (mealTypeCheck.contains("dinner")) {
                mealType = "Dinner";
            }
            // Replace spaces with underscores for subsequent API request formatting
            mod = whisperResponse.replaceAll(" ", "_");
        } catch (Exception e) {
            System.err.println("No input detected");
        }
        System.out.println(mod);
        return mod;
    }

    public boolean getIsRecording() {
        return isRecording;
    }

    public String getMealType() {
        return mealType;
    }
}