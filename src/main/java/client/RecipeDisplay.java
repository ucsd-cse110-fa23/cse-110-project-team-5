package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// Display Recipe Objects on the RecipeList
class RecipeDisplay extends HBox {
    private Label index;
    private TextField recipeName;
    private Button detailButton;
    private Recipe recipe;

    RecipeDisplay(Recipe recipe) {
        this.recipe = recipe;
        this.setPrefSize(500, 20); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the recipe
        this.getChildren().add(index); // add index label to recipe

        recipeName = new TextField(); // create recipe name text field
        recipeName.setPrefSize(380, 20); // set size of text field
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        // recipeName.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(recipeName); // add textlabel to recipe

        detailButton = new Button("View Details"); // create button for viewing recipe details
        detailButton.setPrefSize(100, 20); // set size of details button
        detailButton.setPrefHeight(Double.MAX_VALUE); // 
        detailButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        detailButton.setOnAction(e -> { // add button functionality
            RecipeDetails root = new RecipeDetails(this);
            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(root, 500, 600);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();
        });
        this.getChildren().add(detailButton);

        this.setRecipeDisplayName(recipe);
    }

    // Set Recipe Index
    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.recipeName.setPromptText("Recipe " + num);
    }

    // Return Detail button
    public Button getDetailButton() {
        return this.detailButton;
    }

    // Set RecipeDisplay Object name
    public void setRecipeDisplayName(Recipe recipe){
        this.recipeName.setText(recipe.getRecipeName());
    }

    // Return Recipe
    public Recipe getRecipe(){
        return this.recipe;
    }
}