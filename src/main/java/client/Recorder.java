package client;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Recorder {

    private RecipeList recipeList;
    private RecipeGenerate recipeGen;

    private Scene scene;
    private Label recordingLabel;
    private Text instructions;

    private TargetDataLine targetLine; // Target data line for audio recording
    private File outputFile; // File to store the recorded audio
    private String whisperResponse; // Response from the 'Whisper' API

    private String mealType;
    private String mealTypeCheck;
    private boolean isRecording;

    private ServerError serverError;

    public Recorder(RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recipeGen = new RecipeGenerate();
        this.isRecording = false;
        this.recordingLabel = new Label("Recording...");
        this.recordingLabel.setVisible(false);
    }

    public void showRecordingWindow() {
        Stage recordingStage = new Stage();
        BorderPane recordingPane = new BorderPane();
        instructions = new Text("Specify Meal Type (Breakfast, Lunch, or Dinner)");
        instructions.setLayoutX(130);
        instructions.setLayoutY(60);
        recordingPane.getChildren().add(instructions);
        Button recordButton = new Button("Record");
        Button ingredientButton = new Button("Record Ingredients");
        ingredientButton.setDisable(true);
        recordingStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                recordingLabel.setVisible(false);
                if (isRecording) {
                    toggleRecord();
                }
            }
        });

        // Set up event handler for recordButton
        recordButton.setOnAction(e1 -> {
            this.serverError = new ServerError(recordButton);
            if (this.serverError.checkServerAvailability()) {
                recordMealType(instructions, ingredientButton);
            }
        });

        // Set up event handler for ingredientButton
        ingredientButton.setOnAction(e1 -> {
            this.serverError = new ServerError(ingredientButton);
            if (this.serverError.checkServerAvailability()) {
                processIngredients(recordingStage);
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(recordButton, ingredientButton);
        buttonBox.getChildren().addAll(buttonContainer, recordingLabel);
        recordingPane.setCenter(buttonBox);
        scene = new Scene(recordingPane, 500, 600);
        recordingStage.setScene(scene);
        recordingStage.setTitle("Recording Window");
        recordingStage.show();
    }

    public void recordMealType(Text instructions, Button ingredientButton) {
        Platform.runLater(() -> {
            isRecording = this.toggleRecord();
            recordingLabel.setVisible(isRecording);
            if (!isRecording) {
                this.mealTypeCheck = "lunch";//this.retrieveVoiceCommandResponse("voiceinstructions.wav").toLowerCase();
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

    private void processIngredients(Stage recordingStage) {
        Platform.runLater(() -> {
            boolean isRecording = this.toggleRecord();
            recordingLabel.setVisible(isRecording);
            if (!isRecording) {
                RecipeDetails recipeDetails = new RecipeDetails(recipeList);
                String ingredients = "Chicken"; //this.retrieveVoiceCommandResponse("voiceinstructions.wav");
                String gptOutput = recipeGen.fetchGeneratedRecipe(ingredients, mealType);
                if (gptOutput == "NO INPUT") {
                    instructions.setText("Please Repeat Ingredients");
                } else {
                    recipeDetails.setTitleAndDetails(gptOutput);
                    recipeDetails.setMealtype(mealType);
                    recordingStage.close();
                    scene.setRoot(recipeDetails);
                    Stage recipeDetailStage = new Stage();
                    recipeDetailStage.setScene(scene);
                    recipeDetailStage.show();
                    recipeDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent event) {
                            scene.setRoot(new BorderPane());
                            recipeDetailStage.close();
                        }
                    });
                }
            }
        });
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
            String filePath = "src/main/java/voiceinstructions.wav";
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
        return this.isRecording;
    }

}
