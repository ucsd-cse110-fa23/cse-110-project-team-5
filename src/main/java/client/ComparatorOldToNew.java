package client;

import java.util.Comparator;

public class ComparatorOldToNew implements Comparator<RecipeDisplay> {
    @Override
    public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
        return recipe2.getRecipe().getTime().compareTo(recipe1.getRecipe().getTime());
    }
}
