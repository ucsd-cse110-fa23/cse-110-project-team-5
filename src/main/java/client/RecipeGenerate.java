package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.File;
import javax.sound.sampled.*;

public class RecipeGenerate extends BorderPane {
    private boolean isRecording = false;
    Label recordingLabel = new Label("Recording...");
    private String recipeIntro = "Give_me_a_recipe_using_the_following_ingredients:";

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
            String mod = whisperResponse.replaceAll(" ", "_");
            gptResponse = model.performRequest("GET", "gpt", "300," + recipeIntro + mod);
        } catch (Exception e) {
            System.out.println("No input detected");
        }

        return gptResponse;
    }

}
