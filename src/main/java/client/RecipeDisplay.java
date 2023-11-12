package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// Recipes displayed on the recipe list
class RecipeDisplay extends HBox {
    private Label index;
    private TextField recipeName;
    private String mealType;

    RecipeDisplay(String mealType) {
        this.mealType = mealType;
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
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        recipeName.setText(mealType);
        this.getChildren().add(recipeName); // add textlabel to recipe
    }

    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.recipeName.setPromptText("Recipe " + num);
        recipeName.setText(mealType + " " + num);
    }

    public String getMealType(){
        return this.mealType;
    }
}