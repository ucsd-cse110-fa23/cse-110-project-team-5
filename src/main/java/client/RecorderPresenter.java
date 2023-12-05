package client;

// import javafx.application.Platform;
import javafx.event.EventHandler;
// import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RecorderPresenter {
    private RecipePresenter recipePresenter;
    private RecorderPage recorderPage;
    private Recorder recorder;
    
    private String mealType;
    private String ingredients;

    private RecipeGenerate recipeGenerate;
    
    Stage recordingStage;

    RecorderPresenter(RecipePresenter recipePresenter) {
        this.recipePresenter = recipePresenter;

        this.recorderPage = new RecorderPage(this);
        this.recorder = new Recorder();
        this.recipeGenerate = new RecipeGenerate();

        recordingStage = new Stage();
        recordingStage.setScene(recorderPage.getScene());
        recordingStage.setTitle("Recording Window");
        recordingStage.show();
        // Stops recording when the window is closed
        recordingStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                stopRecording();
            }
        });
    }

    public void notifyMealType() {
        recorder.toggleRecord();
        boolean isRecording = recorder.isRecording();
        recorderPage.updateRecordingLabel(isRecording);
        
        // Platform.runLater(() -> {});
        if (!isRecording) {
            String recording = recorder.retrieveVoiceCommandResponse("voiceinstructions.wav");
            // this.mealType = "Dinner";
            this.mealType = retrieveMealType(recording);
            if (this.mealType != null) {
                recorderPage.enableRecordIngredients();
            } else {
                recorderPage.setInstructions("Please repeat the meal type (Breakfast, Lunch, or Dinner)");
            }
        }
    }

    // @require this.mealType != null
    public void notifyIngredients() {
        recorder.toggleRecord();
        boolean isRecording = recorder.isRecording();
        recorderPage.updateRecordingLabel(isRecording);
        
        // Platform.runLater(() -> {});
        if (!isRecording) {
            // this.ingredients = "chicken";
            this.ingredients = recorder.retrieveVoiceCommandResponse("voiceinstructions.wav");
            String newRecipe = recipeGenerate.fetchGeneratedRecipe(this.ingredients, this.mealType);
            if (newRecipe.equals("NO INPUT")) {
                recorderPage.setInstructions("Please Repeat Ingredients");
            } else {
                recipePresenter.notifyRecorder(newRecipe);
                recordingStage.close();
            }
        }
    }

    public void stopRecording() {
        if (recorder.isRecording())
            recorder.toggleRecord();
    }

    // @require containsMealType(recording)
    // @require recording.toLowerCase()
    private String retrieveMealType(String recording) {
        String result = recording.toLowerCase();
        if (result.contains("breakfast"))
            return "Breakfast";
        if (result.contains("lunch"))
            return "Lunch";
        if (result.contains("dinner"))
            return "Dinner";
        return null;
    }

    public String getMealType() {
        return this.mealType;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public Stage getStage() {
        return this.recordingStage;
    }
}