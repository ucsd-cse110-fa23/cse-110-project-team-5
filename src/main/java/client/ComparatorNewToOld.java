package client;

import java.util.Comparator;

public class ComparatorNewToOld implements Comparator<RecipeDisplay> {
    @Override
    public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
        return recipe1.getRecipe().getTime().compareTo(recipe2.getRecipe().getTime());
    }
}


