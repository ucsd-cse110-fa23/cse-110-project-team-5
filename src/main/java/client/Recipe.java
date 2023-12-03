package client;

import java.time.LocalDateTime;

// The Recipe class represents a recipe with a name, details, and status (if the recipe should be removed)
public class Recipe {
    // Instance variables
    private String recipeName;
    private String recipeDetails;
    private String mealType;
    private LocalDateTime time;
    private boolean isDone; // Flag indicating whether the recipe is marked as done

    // Constructor to create a Recipe object with its name, details, and the
    // RecipeList that will hold it
    public Recipe(String recipeName, String mealType, String recipeDetails) {
        this.recipeName = recipeName;
        this.mealType = mealType;
        this.recipeDetails = recipeDetails;
        this.isDone = false; // By default, a recipe is not marked as done 
        this.time = LocalDateTime.now();
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public String getMealType() {
        return this.mealType;
    }

    public String getRecipeDetails() {
        return this.recipeDetails;
    }

    public String getTimeString(){
        return this.time.toString();
    }

    public LocalDateTime getTime() {
        return this.time;
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

