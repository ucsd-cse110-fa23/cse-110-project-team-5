package anything;

public class Recipe {
    String title;
    String recipe;
    MealType mealType;

    public Recipe(MealType mealType, String title, String recipe) {
        this.mealType = mealType;
        this.title = title;
        this.recipe = recipe;
    }

    public String getMealType() {
        return this.mealType.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getRecipe() {
        return this.recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}