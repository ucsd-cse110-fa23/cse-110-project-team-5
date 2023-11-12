package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.File;
import javax.sound.sampled.*;

public class RecipeGenerate extends BorderPane {
    private boolean isRecording = false;
    Label recordingLabel = new Label("Recording...");
    public String mealType;
    private String recipeIntro = "Give_me_a_recipe_for_";
    private String recipeIntro2 = "using_only_the_following_ingredients_";
    public String whisperResponse;
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
            stopAudioRecording();
        } else {
            startAudioRecording();
        }
        isRecording = !isRecording;
        return isRecording;
    }

    public void startAudioRecording() {
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

    public void stopAudioRecording() {
        targetLine.stop();
        targetLine.close();
        recordingLabel.setVisible(false);
    }
    public String retrieveVoiceCommandResponse() {
        Model model = new Model();
        String mod = "";
        try {
            whisperResponse = model.performRequest("GET", "whisper", "voiceinstructions.wav");
            String mealTypecheck = whisperResponse.toLowerCase();
            if(mealTypecheck.contains("breakfast")) {
                mealType = "breakfast";
            }
            else if(mealTypecheck.contains("lunch")) {
                mealType = "lunch";
            }
            else if(mealTypecheck.contains("dinner")) {
                mealType = "dinner";
            }
            mod = whisperResponse.replaceAll(" ", "_");
        }
        catch (Exception e) {
            System.err.println("No input detected");
        }
        return mod;
    }
    public String fetchGeneratedRecipe() {
        Model model = new Model();
        String gptResponse = "";
        try {
            String ingredients = retrieveVoiceCommandResponse();
            gptResponse = model.performRequest("GET", "gpt", "500," + recipeIntro + mealType + recipeIntro2 + ingredients);
        } catch (Exception e) {
            System.out.println("No input detected");
        }
        return gptResponse;
    }

}
