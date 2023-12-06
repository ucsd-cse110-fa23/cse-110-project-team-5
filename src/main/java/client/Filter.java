package client;

/**
 * The Filter class is responsible for filtering recipes based on a specified
 * meal type.
 */
public class Filter {
    // Filters the provided RecipeList based on the specified meal type.
    public void filter(RecipeList recipeList, String mealtype) {
        // Iterate through each child in the RecipeList
        for (int i = 0; i < recipeList.getChildren().size(); i++) {
            // Check if the child is an instance of RecipeDisplay
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                // Cast the child to RecipeDisplay for accessing recipe-related information
                RecipeDisplay currentRecipe = (RecipeDisplay) recipeList.getChildren().get(i);

                // Check if the current recipe's meal type matches the specified meal type or if
                // "All" is selected
                if (mealtype.equals("All") || currentRecipe.getRecipe().getMealType().equals(mealtype)) {
                    // Show and manage the visibility of the current recipe if it matches the
                    // criteria
                    currentRecipe.setVisible(true);
                    currentRecipe.setManaged(true);
                } else {
                    // Hide and unmanage the visibility of the current recipe if it does not match
                    // the criteria
                    currentRecipe.setVisible(false);
                    currentRecipe.setManaged(false);
                }
            }
        }
    }
}
