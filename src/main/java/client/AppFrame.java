package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

// Main AppFrame for Pantry Pal App 
class AppFrame extends BorderPane {
    // Declare instance variables
    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private ShowDetails showDetails;
    private Label recordingLabel;
    private RecipeGenerate recipeGen;
    private boolean isRecording;
    private Text instructions;
    private Button createButton;
    private Scene scene;

    // Constructor for AppFrame
    AppFrame() {
        // Initialize UI components
        header = new Header();
        recipeList = new RecipeList();
        recipeGen = new RecipeGenerate();
        recordingLabel = new Label("Recording...");
        recordingLabel.setVisible(false); 
        footer = new Footer();
        ScrollPane scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Configure layout of the BorderPane
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        // Initialize and configure button
        createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons
    }

    // App Header
    class Header extends HBox {
        // Constructor for Header
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #F0F8FF;");

            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
        }
    }

    // App Footer
    class Footer extends HBox {
        // Declare instance variable
        private Button createButton;

        // Constructor for Footer
        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // set a default style for buttons - background color, font size, italics
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            createButton = new Button("New Recipe"); // text displayed on add button
            createButton.setStyle(defaultButtonStyle); // styling the button

            this.getChildren().addAll(createButton); // adding buttons to footer
            this.setAlignment(Pos.CENTER); // aligning the buttons to center
        }

        // Getter for createButton
        public Button getCreateButton() {
            return createButton;
        }
    }

    // Method to add event listeners to buttons
    public void addListeners() {
        // Add button functionality
        createButton.setOnAction(e -> { showRecordingWindow();
        });
    }
    private void showRecordingWindow() {
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
                if(isRecording) {
                    recipeGen.toggleRecord();
                }
            }
        });
        // Set up event handler for recordButton
        recordButton.setOnAction(e1 -> toggleRecording(instructions, ingredientButton));

        // Set up event handler for ingredientButton
        ingredientButton.setOnAction(e1 -> processIngredients(recordingStage));

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
        private void toggleRecording(Text instructions, Button ingredientButton) {
        Platform.runLater(() -> {
            isRecording = recipeGen.toggleRecord();
            recordingLabel.setVisible(isRecording);
            if (!isRecording) {
                String response = recipeGen.retrieveVoiceCommandResponse("voiceinstructions.wav").toLowerCase();
                if (response.contains("breakfast") || response.contains("lunch") || response.contains("dinner")) {
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
            boolean isRecording = recipeGen.toggleRecord();
            recordingLabel.setVisible(isRecording);
            if (!isRecording) {
                showDetails = new ShowDetails(recipeList);
                String gptOutput = recipeGen.fetchGeneratedRecipe("voiceinstructions.wav");
                if(gptOutput == "NO INPUT") {
                    instructions.setText("Please Repeat Ingredients");
                }
                else {
                    showDetails.setTitleAndDetails(gptOutput);
                    recordingStage.close();
                    scene.setRoot(showDetails);
                    Stage recipeDetailStage = new Stage();
                    recipeDetailStage.setScene(scene);
                    recipeDetailStage.show();
                }
            }
        });
    }
}