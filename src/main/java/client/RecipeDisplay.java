package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// Represents the graphical display of a single recipe in the RecipeList
class RecipeDisplay extends HBox {
    // Instance variables
    private Label index;
    private TextField recipeName;
    private Button detailButton;
    private Recipe recipe;

    // Constructor for RecipeDisplay
    RecipeDisplay(Recipe recipe) {
        this.recipe = recipe;
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // Set background
                                                                                                     // color and style

        // Create and configure the index label
        index = new Label();
        index.setText(""); // Initialize index label text
        index.setPrefSize(40, 20); // Set size of the index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the recipe
        this.getChildren().add(index); // Add index label to the RecipeDisplay

        // Create and configure the recipe name text field
        recipeName = new TextField();
        recipeName.setPrefSize(380, 20); // Set size of the text field
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // Set background color of the text
                                                                                    // field
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the text field
        this.getChildren().add(recipeName); // Add text label to the RecipeDisplay

        // Create and configure the "View Details" button
        detailButton = new Button("View Details");
        detailButton.setPrefSize(100, 20); // Set size of the button
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // Set style of the button
        detailButton.setOnAction(e -> { // Add button functionality to open RecipeDetails window
            RecipeDetails root = new RecipeDetails(this);
            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(root, 500, 600);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();
        });
        this.getChildren().add(detailButton); // Add "View Details" button to the RecipeDisplay

        this.setRecipeDisplayName(recipe); // Set the displayed name of the recipe
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
        return this.recipe;
    }
}
