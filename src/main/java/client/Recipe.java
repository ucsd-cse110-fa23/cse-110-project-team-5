package client;

public class Recipe {
    String recipeName;
    String recipeDetails;
    MealType mealType;
    RecipeList recipeList;
    boolean isDone;

    // public Recipe(MealType mealType, String title, String recipe) {
    // this.mealType = mealType;
    // this.title = title;
    // this.recipe = recipe;
    // }

    // public String getMealType() {
    // return this.mealType.toString();
    // }

    public Recipe(String recipeName, String recipeDetails, RecipeList recipeList) {        //MOCK Purposes
        this.recipeList = recipeList;
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.isDone = false;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public String getRecipeDetails() {
        return this.recipeDetails;
    }

    public RecipeList getRecipeList(){
        return this.recipeList;
    }

    public void setRecipeName(String newName){
        this.recipeName = newName;
    }

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