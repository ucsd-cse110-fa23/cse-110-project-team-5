package client;
import javafx.scene.layout.*;

// VBox within AppFrame that holds the list of Recipe objects
class RecipeList extends VBox {
    private String username; // Store the associated username
    private LoadData loader; // Assume a loader class exists to load recipes


    // Recipe List Constructor
    RecipeList() {
        this.setSpacing(5); // sets spacing between recipes
        this.setPrefSize(500, 600);
        this.setStyle("-fx-background-color: #EAF4F4;"); // sets background color
    }

    public void setLoader(LoadData loader) {
        this.loader = loader;
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

    public void removeAll() {
        this.getChildren().removeIf(recipeDisplay -> recipeDisplay instanceof RecipeDisplay);
        this.updateRecipeIndices();
    }

    public void setUsername(String savedUsername) {
        this.username = savedUsername; // Set the username
        loadRecipesForUser(); // Load the recipes for this user
    }

    // Loads recipes for the current username
    private void loadRecipesForUser() {
        if (loader != null && username != null && !username.isEmpty()) {
            // Clear current recipes
            this.removeAll();

            // Use the loader to load recipes for this username
            // This is a placeholder - your actual load method may vary
            loader = new LoadData(username, this);
            loader.loadRecipesForUser(username, this);
        }
    }
}
