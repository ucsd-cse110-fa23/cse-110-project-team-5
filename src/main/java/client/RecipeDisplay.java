package client;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

// Represents the graphical display of a single recipe in the RecipeList
class RecipeDisplay extends HBox {
    // Instance variables
    private Label index;
    // private TextField recipeName;
    private TextArea recipeName;
    private Button detailButton;
    private RecipeDetails recipeDetails;

    // Constructor for RecipeDisplay
    RecipeDisplay(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-font-weight: bold; -fx-background-radius: 5;"); // Set background
                                                                                                     // color and style
        
        

        // Create and configure the index label
        index = new Label();
        index.setText(""); // Initialize index label text
        index.setPrefSize(40, 20); // Set size of the index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the recipe
        this.getChildren().add(index); // Add index label to the RecipeDisplay

        // Create and configure the recipe name text area
        recipeName = new TextArea();
        recipeName.setPrefSize(380, 40); // Set size of the text area
        recipeName.setEditable(false); // Make the text area non-editable
        recipeName.setWrapText(true); // Enable text wrapping
        recipeName.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-padding: 5 0 5 0; -fx-background-radius: 10; -fx-text-alignment: center; -fx-font-size: 14px;");
        this.getChildren().add(recipeName); // Add text area to the RecipeDisplay

        // Create and configure the "View Details" button
        detailButton = new Button("View Details");
        detailButton.setPrefSize(100, 20); // Set size of the button
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0;"); // Set style of the button
        detailButton.setOnAction(e -> { // Add button functionality to open RecipeDetails window
            RecipeDetails root = this.recipeDetails;
            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(root, 500, 600);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();
            viewDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    viewDetailScene.setRoot(new BorderPane());
                    viewDetailStage.close();
                }
            });
        });
        this.getChildren().add(detailButton); // Add "View Details" button to the RecipeDisplay
    }

    // Set Recipe Index and prompt text for the text field
    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // Convert num to String and set index label text
        this.recipeName.setPromptText("Recipe " + num); // Set prompt text for the recipe name text field
    }

    public Button getDetailButton() {
        return this.detailButton;
    }

    // Set the displayed name of the recipe in the text field
    public void setRecipeDisplayName(Recipe recipe) {
        this.recipeName.setText(recipe.getRecipeName());
    }

    public Recipe getRecipe() {
        return this.recipeDetails.getRecipe();
    }

    public String getTitle() {
        return this.recipeName.getText();
    }
}
