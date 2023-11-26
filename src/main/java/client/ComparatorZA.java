package client;

import java.util.Comparator;

public class ComparatorZA implements Comparator<RecipeDisplay> {
    @Override
    public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
        return recipe2.getTitle().compareTo(recipe1.getTitle());       
    }
}
