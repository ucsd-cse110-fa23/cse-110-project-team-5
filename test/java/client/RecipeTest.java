package client;

import org.junit.jupiter.api.Test;

import client.Recipe;
import client.RecipeList;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeTest {
    Recipe recipe;
    RecipeList recipeList;

    @BeforeEach
    public void setUp(){
        recipeList = null;
        recipe = new Recipe("Fried Rice", "Mix old rice, eggs, and spam", recipeList);
    }

    @Test
    public void testGetRecipeName() {
        assertEquals("Fried Rice", recipe.getRecipeName());
    }

    @Test 
    public void testGetRecipeDetails() {
        assertEquals("Mix old rice, eggs, and spam", recipe.getRecipeDetails());
    }

    @Test
    public void testGetRecipeList() {
        assertEquals(recipeList, recipe.getRecipeList());
    }

    @Test
    public void testSetRecipeName() {
        String newRecipeName = "Fried Rice and carrots";
        recipe.setRecipeName(newRecipeName);
        assertEquals("Fried Rice and carrots", recipe.getRecipeName());
    }

    @Test
    public void testSetRecipe() {
        String newRecipe = "New Recipe";
        recipe.setRecipe(newRecipe);
        assertEquals(newRecipe, recipe.getRecipeDetails());
    }

    @Test
    public void testIsDone() {
        assertEquals(false, recipe.isMarkedDone());
        recipe.markDone();
        assertEquals(true, recipe.isMarkedDone());
    }

    @Test
    public void testEditRecipe() {
        String name = "Beef Curry";
        String details = "Make beef curry with carrots";
        Recipe recipeToEdit = new Recipe(name, details, recipeList);
        assertEquals("Beef Curry", recipeToEdit.getRecipeName());
        assertEquals("Make beef curry with carrots", recipeToEdit.getRecipeDetails());

        String newName = "Beef Curry with Salad";
        String newDetails = "Make beef curry with carrots along with a side of salad";
        recipeToEdit.setRecipeName(newName);
        recipeToEdit.setRecipe(newDetails);
        assertEquals("Beef Curry with Salad", recipeToEdit.getRecipeName());
        assertEquals("Make beef curry with carrots along with a side of salad", recipeToEdit.getRecipeDetails());
    }
}