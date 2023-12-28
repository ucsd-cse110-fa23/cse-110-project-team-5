package client;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The Sort class provides methods for sorting RecipeDisplay objects in various
 * ways.
 */
public class Sort {
    // Comparators for different sorting criteria
    public Comparator<RecipeDisplay> compAZ;
    public Comparator<RecipeDisplay> compZA;
    public Comparator<RecipeDisplay> compNewToOld;
    public Comparator<RecipeDisplay> compOldToNew;

    /**
     * Constructor for the Sort class. Initializes the comparators.
     */
    public Sort() {
        // Comparator for sorting alphabetically (A-Z)
        this.compAZ = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
                return recipe1.getTitle().compareTo(recipe2.getTitle());
            }
        };

        // Comparator for sorting alphabetically in reverse order (Z-A)
        this.compZA = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
                return recipe2.getTitle().compareTo(recipe1.getTitle());
            }
        };

        // Comparator for sorting from new to old based on recipe creation time
        this.compNewToOld = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
                return recipe2.getRecipe().getTime().compareTo(recipe1.getRecipe().getTime());
            }
        };

        // Comparator for sorting from old to new based on recipe creation time
        this.compOldToNew = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
                return recipe1.getRecipe().getTime().compareTo(recipe2.getRecipe().getTime());
            }
        };
    }

    /**
     * Sorts the RecipeList using the specified comparator.
     *
     * @param recipeList The RecipeList to be sorted.
     * @param comp       The comparator used for sorting.
     */
    public void sort(RecipeList recipeList, Comparator<RecipeDisplay> comp) {
        // Create a temporary list to hold RecipeDisplay objects for sorting
        ArrayList<RecipeDisplay> sort = new ArrayList<RecipeDisplay>();

        // Add RecipeDisplay objects from the RecipeList to the temporary list
        for (int i = 0; i < recipeList.getChildren().size(); i++) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                RecipeDisplay recipe = (RecipeDisplay) recipeList.getChildren().get(i);
                sort.add(recipe);
            }
        }

        // Sort the temporary list using the specified comparator
        sort.sort(comp);

        // Remove RecipeDisplay objects from the RecipeList
        for (int i = recipeList.getChildren().size() - 1; i >= 0; i--) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                recipeList.getChildren().remove(i);
            }
        }

        // Add sorted RecipeDisplay objects back to the RecipeList
        for (int j = 0; j < sort.size(); j++) {
            RecipeDisplay recipe = sort.get(j);
            recipeList.getChildren().add(recipe);
        }

        // Update recipe indices in the RecipeList
        recipeList.updateRecipeIndices();
    }

    /**
     * Sorts the RecipeList alphabetically from A to Z.
     *
     * @param recipeList The RecipeList to be sorted.
     */
    public void sortAZ(RecipeList recipeList) {
        sort(recipeList, compAZ);
    }

    /**
     * Sorts the RecipeList alphabetically from Z to A.
     *
     * @param recipeList The RecipeList to be sorted.
     */
    public void sortZA(RecipeList recipeList) {
        sort(recipeList, compZA);
    }

    /**
     * Sorts the RecipeList from new to old based on recipe creation time.
     *
     * @param recipeList The RecipeList to be sorted.
     */
    public void sortNewToOld(RecipeList recipeList) {
        sort(recipeList, compNewToOld);
    }

    /**
     * Sorts the RecipeList from old to new based on recipe creation time.
     *
     * @param recipeList The RecipeList to be sorted.
     */
    public void sortOldToNew(RecipeList recipeList) {
        sort(recipeList, compOldToNew);
    }
}
