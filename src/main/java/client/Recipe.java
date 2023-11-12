package client;

// The Recipe class represents a recipe with a name, details, and status (if the recipe should be removed)
public class Recipe {
    String recipeName;
    String recipeDetails;
    RecipeList recipeList;
    boolean isDone;

    // Constructor for the Recipe class
    public Recipe(String recipeName, String recipeDetails, RecipeList recipeList) {
        // Initialize the Recipe object with the provided values
        this.recipeList = recipeList;
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.isDone = false; // Default status is not done
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public String getRecipeDetails() {
        return this.recipeDetails;
    }

    public RecipeList getRecipeList() {
        return this.recipeList;
    }

    public void setRecipeName(String newName) {
        this.recipeName = newName;
    }

    public void setRecipe(String newDetails) {
        this.recipeDetails = newDetails;
    }

    public void markDone() {
        this.isDone = true;
    }

    public boolean isMarkedDone() {
        return this.isDone;
    }
}