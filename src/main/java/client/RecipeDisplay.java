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
    private Recipe recipe;

    // Constructor for RecipeDisplay
    RecipeDisplay(Recipe recipe) {
        this.recipe = recipe;
        this.setPrefSize(500, 20); // Set the size of the RecipeDisplay
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // Set background
                                                                                                     // color

        // Index label for displaying the recipe number
        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the recipe
        this.getChildren().add(index); // add index label to recipe

        recipeName = new TextField(); // create recipe name text field
        recipeName.setPrefSize(380, 20); // set size of text field
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(recipeName); // add textlabel to recipe

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

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipeName(Recipe recipe) {
        this.recipeName.setText(recipe.getRecipeName());
    }
}