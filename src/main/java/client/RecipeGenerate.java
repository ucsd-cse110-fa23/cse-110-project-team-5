package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.Insets;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.*;

public class RecipeGenerate extends BorderPane {
    private boolean isRecording = false;
    Label recordingLabel = new Label("Recording...");
    public String mealType;
    private TargetDataLine targetLine;
    private File outputFile;
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

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

    public boolean toggleRecord() {
        if (isRecording) {
            stopRecord();
        } else {
            startRecord();
        }
        isRecording = !isRecording;
        return isRecording;
    }

    public void startRecord() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(format);
            targetLine.start();
            recordingLabel.setVisible(true);

            outputFile = new File("src/voiceinstructions.wav");
            Thread recordThread = new Thread(() -> {
                try {
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

    public void stopRecord() {
        targetLine.stop();
        targetLine.close();
        recordingLabel.setVisible(false);
    }

    public String getResponse() {
        Model model = new Model();
        String gptResponse = "";
        try {
            String whisperResponse = model.performRequest("GET", "whisper", "voiceinstructions.wav");
            // System.out.println(whisperResponse);
            String mod = whisperResponse.replaceAll(" ", "_");

            // System.out.println(mod);
            // gptResponse = model.performRequest("GET", "gpt", "300," + mod); //TODO FIX
            // FRENCH
            gptResponse = model.performRequest("GET", "gpt", "300,dinner");
            System.out.println(gptResponse);
        } catch (Exception e) {
            System.out.println("No input detected");
        }

        return gptResponse;
        // return "this is a test string"; // MOCK
    }

}
