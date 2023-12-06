package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class RecorderPage {
    private Label recordingLabel;
    private Text instructions;
    private Button ingredientButton;
    private Scene scene;

    private ServerError serverError;

    public RecorderPage(RecorderPresenter recorderPresenter) {
        // Create instructions
        instructions = new Text("Specify Meal Type\n(Breakfast, Lunch, Dinner)");
        instructions.setFont(Font.font("Tahoma", 25)); // Set Instruction Style
        instructions.setTextAlignment(TextAlignment.CENTER); // Center Text
        // Create container for instruction
        VBox instructBox = new VBox(instructions);
        instructBox.setPrefHeight(300);
        instructBox.setAlignment(Pos.CENTER);
        HBox instructHBox = new HBox(instructBox);
        instructHBox.setAlignment(Pos.CENTER);
        // Create Buttons
        Button recordButton = new Button("Record");
        ingredientButton = new Button("Record Ingredients");
        ingredientButton.setDisable(true);
        // Create Recording Label
        recordingLabel = new Label("Recording...");
        recordingLabel.setVisible(false);
        // Create Container for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(recordButton, ingredientButton);
        buttonContainer.setAlignment(Pos.CENTER);
        
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttonContainer, recordingLabel);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        
        BorderPane recordingPane = new BorderPane();
        recordingPane.setStyle("-fx-background-color: #CCE3DE;"); // Set background
        recordingPane.setTop(instructHBox);
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
