package client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SortTest {

    RecipeList recipeListTime;  // to test time related sorts
    RecipeList recipeListChar;  // to test character related sorts

    @BeforeEach
    public void setUp() {
        this.recipeListTime = new RecipeList();
        this.recipeListChar = new RecipeList();
        RecipeDisplay firstRecipe = new RecipeDisplay(new RecipeDetails(recipeListTime));
        // RecipeDisplay secondRecipe = new RecipeDisplay(null);
        // RecipeDisplay lastRecipe = new RecipeDisplay(null);
        // RecipeDisplay aRecipe = new RecipeDisplay(null);
        // RecipeDisplay qRecipe = new RecipeDisplay(null);
        // RecipeDisplay zRecipe = new RecipeDisplay(null);
        // aRecipe.setRecipeDisplayName(new Recipe("A Recipe", null, null));
        // qRecipe.setRecipeDisplayName(new Recipe("Q Recipe", null, null));
        // zRecipe.setRecipeDisplayName(new Recipe("Z Recipe", null, null));
        // recipeListTime.getChildren().addAll(secondRecipe, lastRecipe, firstRecipe);
        // recipeListChar.getChildren().addAll(qRecipe, zRecipe, aRecipe);
        // for (int i = 0; i < recipeListChar.getChildren().size(); i++) {
        //     if (recipeListChar.getChildren().get(i) instanceof RecipeDisplay) {
        //         System.out.println(recipeListChar.getChildren().get(i));
        //     }
        // }
        
    }

    @Test
    public void testGetCompAZ() {

    }

    @Test
    public void testGetCompZA() {

    }

    @Test
    public void testGetCompNewToOld() {

    }

    @Test
    public void testGetCompOldToNew() {

    }
}
