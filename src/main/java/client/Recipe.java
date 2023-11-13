package client;

// Recipe Object
public class Recipe {
    // Instance variables
    String recipeName;
    String recipeDetails;
    RecipeList recipeList; // Reference to the RecipeList that holds this recipe
    boolean isDone; // Flag indicating whether the recipe is marked as done

    // Constructor to create a Recipe object with its name, details, and the
    // RecipeList that will hold it
    public Recipe(String recipeName, String recipeDetails, RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.isDone = false; // By default, a recipe is not marked as done
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

