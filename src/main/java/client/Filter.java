package client;

public class Filter {
    public void filter(RecipeList recipeList, String mealtype) {
        for (int i = 0; i < recipeList.getChildren().size(); i++) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                if (((RecipeDisplay) recipeList.getChildren().get(i)).getRecipe().getMealType() != mealtype) {
                    ((RecipeDisplay) recipeList.getChildren().get(i)).setVisible(false);
                }
            }
        }
    }
}