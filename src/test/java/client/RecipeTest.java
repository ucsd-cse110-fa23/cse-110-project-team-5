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
        recipe = new Recipe("Recipe Name", "Recipe Details", recipeList);
    }

    @Test
    public void testGetRecipeName() {
        assertEquals("Recipe Name", recipe.getRecipeName());
    }

    @Test 
    public void testGetRecipeDetails() {
        assertEquals("Recipe Details", recipe.getRecipeDetails());
    }

    @Test
    public void testGetRecipeList() {
        assertEquals(recipeList, recipe.getRecipeList());
    }

    @Test
    public void testSetRecipeName() {
        String newRecipeName = "New Recipe Name";
        recipe.setRecipeName(newRecipeName);
        assertEquals(newRecipeName, recipe.getRecipeName());
    }

    @Test
    public void testSetRecipe() {
        String newRecipe = "New Recipe";
        recipe.setRecipe(newRecipe);
        assertEquals(newRecipe, recipe.getRecipeDetails());
    }

    @Test
    public void testIsDone() {
        assertEquals(false, recipe.MarkedDone());
        recipe.isDone();
        assertEquals(true, recipe.MarkedDone());
    }

    @Test
    public void testEditRecipe() {
        String name = "Beef Curry";
        String details = "Make beef curry with carrots";
        Recipe recipeToEdit = new Recipe(name, details, recipeList);
        assertEquals(name, recipeToEdit.getRecipeName());
        assertEquals(details, recipeToEdit.getRecipeDetails());

        String newName = "Not Beef Curry";
        String newDetails = "don't make beef curry";
        recipeToEdit.setRecipe(newDetails);
        recipeToEdit.setRecipeName(newName);
        assertEquals(newName, recipeToEdit.getRecipeName());
        assertEquals(newDetails, recipeToEdit.getRecipeDetails());
    }
}