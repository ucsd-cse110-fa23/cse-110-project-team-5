package client;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort {
    public Comparator<RecipeDisplay> compAZ;
    public Comparator<RecipeDisplay> compZA;
    public Comparator<RecipeDisplay> compNewToOld;
    public Comparator<RecipeDisplay> compOldToNew;

    public Sort() {
        this.compAZ = new ComparatorAZ();
        this.compZA = new ComparatorZA();
        this.compNewToOld = new ComparatorNewToOld();
        this.compOldToNew = new ComparatorOldToNew();
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

    public Comparator<RecipeDisplay> getCompAZ() {
        return this.compAZ;
    }

    public Comparator<RecipeDisplay> getCompZA() {
        return this.compZA;
    }

    public Comparator<RecipeDisplay> getCompNewToOld() {
        return this.compNewToOld;
    }

    public Comparator<RecipeDisplay> getCompOldToNew() {
        return this.compOldToNew;
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