package client;

import javafx.scene.layout.*;

// The RecipeList class represents a list of recipes displayed in a VBox
class RecipeList extends VBox {

    // Constructor for RecipeList
    RecipeList() {
        this.setSpacing(5); // Set spacing between recipes
        this.setPrefSize(500, 560); // Set preferred size for the RecipeList
        this.setStyle("-fx-background-color: #F0F8FF;"); // Set background color
    }

    // Update the indices of displayed recipes in the RecipeList
    public void updateRecipeIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeDisplay) {
                // Set the index for each RecipeDisplay in the RecipeList
                ((RecipeDisplay) this.getChildren().get(i)).setRecipeIndex(index);
                index++;
            }
        }
    }

    // Remove recipes marked as done from the RecipeList
    public void removeRecipe() {
        // Remove RecipeDisplays marked as done from the RecipeList
        this.getChildren().removeIf(recipeDisplay -> recipeDisplay instanceof RecipeDisplay
                && ((RecipeDisplay) recipeDisplay).getRecipe().isMarkedDone());
        // Update the indices after removing recipes
        this.updateRecipeIndices();
    }
}
