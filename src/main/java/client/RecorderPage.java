package client;

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

public class RecorderPage {

    private RecipeList recipeList;
    private Recorder recorder;

    private Scene scene;
    private Label recordingLabel;
    private Text instructions;
    
    Stage recordingStage;

    private ServerError serverError;

    public RecorderPage(RecipeList recipeList) {
        recordingStage = new Stage();

        recordingLabel = new Label("Recording...");
        recordingLabel.setVisible(false);

        instructions = new Text("Specify Meal Type (Breakfast, Lunch, or Dinner)");
        instructions.setLayoutX(130);
        instructions.setLayoutY(60);
        
        BorderPane recordingPane = new BorderPane();
        recordingPane.getChildren().add(instructions);
        
        Button recordButton = new Button("Record");

        Button ingredientButton = new Button("Record Ingredients");
        ingredientButton.setDisable(true);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(recordButton, ingredientButton);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttonContainer, recordingLabel);
        
        recordingPane.setCenter(buttonBox);
        scene = new Scene(recordingPane, 500, 600);
        recordingStage.setScene(scene);
        recordingStage.setTitle("Recording Window");

        recorder = new Recorder();
        
        // Stops recording when the window is closed
        recordingStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                // recordingLabel.setVisible(false);
                if (recorder.getIsRecording()) {
                    recorder.toggleRecord();
                }
            }
        });
        // Set up event handler for recordButton
        recordButton.setOnAction(e1 -> {
            this.serverError = new ServerError(recordButton);
            if (this.serverError.isAvailable()) {
                recordingLabel.setVisible(!(recorder.getIsRecording()));
                Platform.runLater(() -> {
                    recorder.recordMealType(instructions, ingredientButton);
                });
            }
        });
        // Set up event handler for ingredientButton
        ingredientButton.setOnAction(e1 -> {
            this.serverError = new ServerError(ingredientButton);
            if (this.serverError.isAvailable()) {
                // TODO add listener outside class to check after toggleRecord
                recordingLabel.setVisible(!(recorder.getIsRecording()));

                Platform.runLater(() -> {
                    String newRecipe = recorder.processIngredients();
                    if (newRecipe != null) {
                        if (newRecipe == "NO INPUT") {
                            instructions.setText("Please Repeat Ingredients");
                        } else {
                            RecipeDetails recipeDetails = new RecipeDetails(recipeList);
                            recipeDetails.setTitleAndDetails(newRecipe);
                            recipeDetails.setMealtype(recorder.getMealType());
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
        });
    }

    public void show() {
        recordingStage.show();
    }
}
