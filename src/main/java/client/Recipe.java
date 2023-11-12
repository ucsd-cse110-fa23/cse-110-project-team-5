package client;

// Recipe Object
public class Recipe {
    String recipeName;
    String recipeDetails;
    RecipeList recipeList;
    boolean isDone;

    // Create Recipe Object with its name, details, and RecipeList Object that will hold it
    public Recipe(String recipeName, String recipeDetails, RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.isDone = false;
    }

    // Return Recipe Name
    public String getRecipeName() {
        return this.recipeName;
    }

    // Return Recipe Details
    public String getRecipeDetails() {
        return this.recipeDetails;
    }

    // Return Recipe List
    public RecipeList getRecipeList(){
        return this.recipeList;
    }

    // Change Recipe Name
    public void setRecipeName(String newName){
        this.recipeName = newName;
    }

    // Change Recipe Details
    public void setRecipe(String newDetails) {
        this.recipeDetails = newDetails;
    }

    public void isDone(){
        this.isDone = true;
    }

    public boolean MarkedDone(){
        return this.isDone;
    }
}