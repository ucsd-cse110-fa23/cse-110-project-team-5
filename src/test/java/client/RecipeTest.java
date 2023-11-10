package client;

import org.junit.jupiter.api.Test;

import client.MealType;
import client.Recipe;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeTest {

    Recipe recipe;

    @BeforeEach
    public void setUp(){
        recipe = new Recipe(MealType.DINNER, "Recipe Title", "Recipe Content");
    }
    
    @Test
    public void testGetMealType() {
        assertTrue(MealType.DINNER == recipe.getMealType());
    }

    @Test void testGetMealTypeToString() {
        assertEquals(MealType.DINNER.toString(), recipe.getMealType().toString());
    }
    
    @Test
    public void testGetTitle() {
        assertEquals("Recipe Title", recipe.getTitle());
    }

    @Test
    public void testGetRecipe() {
        assertEquals("Recipe Content", recipe.getRecipe());
    }

    @Test
    public void testSetRecipe() {
        String newRecipe = "New Recipe";
        recipe.setRecipe(newRecipe);
        assertEquals(newRecipe, recipe.getRecipe());
    }

    @Test
    public void testEditRecipe() {
        Recipe recipeToEdit = new Recipe(MealType.LUNCH, "Chicken Curry", "Chicken Curry Recipe");
        assertTrue(MealType.LUNCH == recipeToEdit.getMealType());
        assertEquals("Chicken Curry", recipeToEdit.getTitle());
        assertEquals("Chicken Curry Recipe", recipeToEdit.getRecipe());
        recipeToEdit.setRecipe("Chicken Curry modified");
        assertEquals("Chicken Curry modified", recipeToEdit.getRecipe());
    }
}