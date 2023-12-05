package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class RecorderPage {
    private Label recordingLabel;
    private Text instructions;
    private Button ingredientButton;
    private Scene scene;

    private ServerError serverError;

    public RecorderPage(RecorderPresenter recorderPresenter) {
        instructions = new Text("Specify Meal Type\n(Breakfast, Lunch, Dinner)");
        instructions.setLayoutX(130);
        instructions.setLayoutY(60);
        instructions.setTextAlignment(TextAlignment.CENTER);
        
        Button recordButton = new Button("Record");

        ingredientButton = new Button("Record Ingredients");
        ingredientButton.setDisable(true);

        recordingLabel = new Label("Recording...");
        recordingLabel.setVisible(false);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(recordButton, ingredientButton);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttonContainer, recordingLabel);
        
        BorderPane recordingPane = new BorderPane();
        recordingPane.getChildren().add(instructions);
        recordingPane.setCenter(buttonBox);

        scene = new Scene(recordingPane, 500, 600);

        // Set up event handler for recordButton
        recordButton.setOnAction(e1 -> {
            if (serverIsAvailable(recordButton)) {
                recorderPresenter.notifyMealType();
            }
        });
        // Set up event handler for ingredientButton
        ingredientButton.setOnAction(e1 -> {
            if (serverIsAvailable(ingredientButton)) {
                recorderPresenter.notifyIngredients();
            }
        });
    }

    public Scene getScene() {
        return this.scene;
    }

    public void updateRecordingLabel(boolean isRecording) {
        recordingLabel.setVisible(isRecording);
    }

    public void enableRecordIngredients() {
        this.ingredientButton.setDisable(false);
        instructions.setText("Tell me your ingredients!");
    }

    public void setInstructions(String message) {
        instructions.setText(message);
    }

    private boolean serverIsAvailable(Button button) {
        this.serverError = new ServerError(button);
        return serverError.checkServerAvailability();
    }
}
