package client;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort {
    public Comparator<RecipeDisplay> compAZ;
    public Comparator<RecipeDisplay> compZA;
    public Comparator<RecipeDisplay> compNewToOld;
    public Comparator<RecipeDisplay> compOldToNew;

    public Sort() {
        this.compAZ = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
            return recipe1.getTitle().compareTo(recipe2.getTitle());
        }};
        this.compZA = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
            return recipe2.getTitle().compareTo(recipe1.getTitle());   
        }};
        this.compNewToOld = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
            return recipe2.getRecipe().getTime().compareTo(recipe1.getRecipe().getTime());
        }};
        this.compOldToNew = new Comparator<RecipeDisplay>() {
            @Override
            public int compare(RecipeDisplay recipe1, RecipeDisplay recipe2) {
            return recipe1.getRecipe().getTime().compareTo(recipe2.getRecipe().getTime());
        }};
    }

    public void sort(RecipeList recipeList, Comparator<RecipeDisplay> comp) {
        ArrayList<RecipeDisplay> sort = new ArrayList<RecipeDisplay>();
        for (int i = 0; i < recipeList.getChildren().size(); i++) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                RecipeDisplay recipe = (RecipeDisplay) recipeList.getChildren().get(i);
                sort.add(recipe);
            }
        }
        sort.sort(comp);
        for (int i = recipeList.getChildren().size() - 1; i >= 0; i--) {
            if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
                recipeList.getChildren().remove(i);
            }
        }
        for (int j = 0; j < sort.size(); j++) {
            RecipeDisplay recipe = sort.get(j);
            recipeList.getChildren().add(recipe);
        }
        recipeList.updateRecipeIndices();
    }

    public void sortAZ(RecipeList recipeList) {
        sort(recipeList, compAZ);
    }

    public void sortZA(RecipeList recipeList) {
        sort(recipeList, compZA);
    }

    public void sortNewToOld(RecipeList recipeList) {
        sort(recipeList, compNewToOld);
    }

    public void sortOldToNew(RecipeList recipeList) {
        sort(recipeList, compOldToNew);
    }
}

// int j = 0;
//         for (int i = 0; i < recipeList.getChildren().size(); i++) {
//             if (recipeList.getChildren().get(i) instanceof RecipeDisplay) {
//                 RecipeDisplay recipe = (RecipeDisplay) recipeList.getChildren().get(i);
//                 if (recipe != sort.get(j)) {
//                     recipeList.getChildren().remove(sort.get(j));
//                     recipeList.getChildren().add(i, sort.get(j));
//                     j++;
//                 }
//             }
//         }
        // for (int j = 0; j < sort.size(); j++) {
        //     RecipeDisplay recipe = sort.get(j);
        //     recipeList.getChildren().add(recipe);
        // }