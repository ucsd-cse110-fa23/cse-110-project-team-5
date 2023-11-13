package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

<<<<<<< HEAD
// Main AppFrame for Pantry Pal App 
=======
// The main application frame
>>>>>>> main
class AppFrame extends BorderPane {
    // Declare instance variables
    private Header header;
    private Footer footer;
    private ShowDetails showDetails;
    private Label recordingLabel;
    private RecipeGenerate recipeGen;
    private Button createButton;
    private Scene scene;
    private RecipeList recipeList;

    // Constructor for AppFrame
    AppFrame() {
        // Initialize UI components
        header = new Header();
<<<<<<< HEAD
        recipeList = new RecipeList();
        recipeGen = new RecipeGenerate();
        recordingLabel = new Label("Recording...");
        recordingLabel.setVisible(false); 
=======
        recipeGen = new RecipeGenerate();
>>>>>>> main
        footer = new Footer();

        recipeList = new RecipeList();

        // Set up the ScrollPane for the recipe list
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

<<<<<<< HEAD
        // Configure layout of the BorderPane
=======
        // Add UI components to the BorderPane
>>>>>>> main
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

<<<<<<< HEAD
        // Initialize and configure button
        createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons
=======
        // Get the "Create" button from the footer
        createButton = footer.getCreateButton();

        // Add event listeners to the buttons
        addListeners();
>>>>>>> main
    }

    // Header section of the application
    class Header extends HBox {
        // Constructor for Header
        Header() {
<<<<<<< HEAD
            this.setPrefSize(500, 60); // Set size of the header
=======
            this.setPrefSize(500, 60);
>>>>>>> main
            this.setStyle("-fx-background-color: #F0F8FF;");

            // Title text for the header
            Text titleText = new Text("Recipe List");
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER);
        }
    }

    // Footer section of the application
    class Footer extends HBox {
        // Declare instance variable
        private Button createButton;

        // Constructor for Footer
        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // Default style for buttons
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF; -fx-font-weight: bold; -fx-font: 11 arial;";

            // Create the "New Recipe" button
            createButton = new Button("New Recipe");
            createButton.setStyle(defaultButtonStyle);

            // Add the button to the footer
            this.getChildren().addAll(createButton);
            this.setAlignment(Pos.CENTER);
        }

        // Getter for createButton
        public Button getCreateButton() {
            return createButton;
        }
    }

<<<<<<< HEAD
    // Method to add event listeners to buttons
    public void addListeners() {
        // Add button functionality
        createButton.setOnAction(e -> { showRecordingWindow();
        });
    }
    private void showRecordingWindow() {
        Stage recordingStage = new Stage();
        BorderPane recordingPane = new BorderPane();
        Text instructions = new Text("Specify Meal Type (Breakfast, Lunch, or Dinner)");
        instructions.setLayoutX(130);
        instructions.setLayoutY(60);
        recordingPane.getChildren().add(instructions);
        Button recordButton = new Button("Record");
        Button ingredientButton = new Button("Record Ingredients");
        ingredientButton.setDisable(true);

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
            boolean isRecording = recipeGen.toggleRecord();
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
                showDetails.setTitleAndDetails(recipeGen.fetchGeneratedRecipe("voiceinstructions.wav"));
                recordingStage.close();
                scene.setRoot(showDetails);
                Stage recipeDetailStage = new Stage();
                recipeDetailStage.setScene(scene);
                recipeDetailStage.show();
            }
=======
    // Add event listeners for buttons
    public void addListeners() {
        // Event listener for the "New Recipe" button
        createButton.setOnAction(e -> {
            // Create a new stage for recording
            Stage recordingStage = new Stage();
            BorderPane recordingPane = new BorderPane();
            Text instructions = new Text("Specify Meal Type (Breakfast, Lunch, or Dinner)");
            instructions.setLayoutX(130);
            instructions.setLayoutY(60);
            recordingPane.getChildren().add(instructions);

            // Create buttons for recording and recording ingredients
            Button recordButton = new Button("Record");
            Button ingredientButton = new Button("Record Ingredients");
            ingredientButton.setDisable(true);

            // Event listener for the recording button
            recordButton.setOnAction(e1 -> {
                if (!recipeGen.toggleRecord()) {
                    String response = recipeGen.retrieveVoiceCommandResponse().toLowerCase();
                    if(response.contains("breakfast") || response.contains("lunch") || response.contains("dinner")) {
                        ingredientButton.setDisable(false);
                        instructions.setText("Tell me your ingredients!");
                    } else {
                        instructions.setText("Please repeat the meal type (Breakfast, Lunch, or Dinner)");
                    }
                }
            });

            // Event listener for the ingredient recording button
            ingredientButton.setOnAction(e1 -> {
                if(!recipeGen.toggleRecord()) {
                    showDetails = new ShowDetails(recipeList);  
                    showDetails.setTitleAndDetails(recipeGen.fetchGeneratedRecipe());
                    recordingStage.close();
                    scene.setRoot(showDetails);
                    Stage recipeDetailStage = new Stage();
                    recipeDetailStage.setScene(scene);
                    recipeDetailStage.show();
                }
            });

            // Set up button layout
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            HBox buttonContainer = new HBox(10);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.getChildren().addAll(recordButton, ingredientButton);
            buttonBox.getChildren().addAll(buttonContainer, recipeGen.recordingLabel);
            recordingPane.setCenter(buttonBox);

            // Set up the scene and stage for recording
            scene = new Scene(recordingPane, 500, 600);
            recordingStage.setScene(scene);
            recordingStage.setTitle("Recording Window");
            recordingStage.show();
>>>>>>> main
        });
    }
}
