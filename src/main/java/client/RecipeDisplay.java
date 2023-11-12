package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// Represents a displayed recipe on the recipe list
class RecipeDisplay extends HBox {
    private Label index;
    private TextField recipeName;
    private Button detailButton;
    private Recipe recipe;

    // Constructor for RecipeDisplay
    RecipeDisplay(Recipe recipe) {
        this.recipe = recipe;
        this.setPrefSize(500, 20); // Set the size of the RecipeDisplay
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // Set background
                                                                                                     // color

        // Index label for displaying the recipe number
        index = new Label();
        index.setText("");
        index.setPrefSize(40, 20);
        index.setTextAlignment(TextAlignment.CENTER);
        index.setPadding(new Insets(10, 0, 10, 0));
        this.getChildren().add(index);

        // TextField for displaying/editing the recipe name
        recipeName = new TextField();
        recipeName.setPrefSize(380, 20);
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        index.setTextAlignment(TextAlignment.LEFT);
        recipeName.setPadding(new Insets(10, 0, 10, 0));
        this.getChildren().add(recipeName);

        // Button for viewing recipe details
        detailButton = new Button("View Details");
        detailButton.setPrefSize(100, 20);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        detailButton.setOnAction(e -> {
            // Open a new stage to display recipe details
            RecipeDetails root = new RecipeDetails(this);
            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(root, 500, 600);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();
        });

        // Set the recipe name in the display
        this.setRecipeName(recipe);

        // Add components to the RecipeDisplay
        this.getChildren().add(detailButton);
    }

    // Set the index and prompt text for the recipe
    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // Convert num to String
        this.recipeName.setPromptText("Recipe " + num);
    }

    public Button getDetailButton() {
        return this.detailButton;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipeName(Recipe recipe) {
        this.recipeName.setText(recipe.getRecipeName());
    }
}