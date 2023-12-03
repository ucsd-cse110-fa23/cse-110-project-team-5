package client;
import javafx.scene.layout.*;

// VBox within AppFrame that holds the list of Recipe objects
class RecipeList extends VBox {

    // Recipe List Constructor
    RecipeList() {
        this.setSpacing(5); // sets spacing between recipes
        this.setPrefSize(500, 600);
        this.setStyle("-fx-background-color: #EAF4F4;"); // sets background color
    }

    // Updates Recipe Indices within the Recipe List
    public void updateRecipeIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeDisplay) {
                ((RecipeDisplay) this.getChildren().get(i)).setRecipeIndex(index);
                index++;
            }
        }
    }

    // Removes Recipes from the Recipe List
    public void removeRecipe() {
        this.getChildren().removeIf(recipeDisplay -> recipeDisplay instanceof RecipeDisplay
                && ((RecipeDisplay) recipeDisplay).getRecipe().isMarkedDone());
        this.updateRecipeIndices(); // Update recipe indices after removal
    }
}
