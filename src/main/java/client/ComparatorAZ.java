package client;

import java.util.Comparator;

public class ComparatorAZ implements Comparator<RecipeDisplay> {
    @Override
    public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
        return recipe1.getTitle().compareTo(recipe2.getTitle());
    }
}
