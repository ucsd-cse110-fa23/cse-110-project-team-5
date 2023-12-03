package client;

public class Filter {
    public void filter(RecipeList recipeList, String mealtype) {
        for (int i = 0; i < recipeList.getChildren().size(); i++) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                RecipeDisplay currentRecipe = (RecipeDisplay) recipeList.getChildren().get(i);
                if (mealtype.equals("All") || currentRecipe.getRecipe().getMealType().equals(mealtype)) {
                    currentRecipe.setVisible(true);
                    currentRecipe.setManaged(true);
                } else {
                    currentRecipe.setVisible(false);
                    currentRecipe.setManaged(false);
                }
            }
        }
    }
}
