package client;

// Import necessary classes for the test
import org.junit.jupiter.api.Test;
import client.Recipe;
import client.RecipeList;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Recipe functionality
public class RecipeTest {

    // Instances of Recipe and RecipeList for testing
    Recipe recipe;
    RecipeList recipeList;

    // Set up method to initialize objects before each test
    @BeforeEach
    public void setUp() {
        // Initialize recipeList as null and create a new Recipe instance
        recipeList = null;
        recipe = new Recipe("Recipe Name", "Recipe Details", "Meal Type");
    }

    // Test case for getting the recipe name
    @Test
    public void testGetRecipeName() {
        assertEquals("Recipe Name", recipe.getRecipeName()); // Assert that the retrieved name matches the expected
                                                             // value
    }

    // Test case for getting the recipe details
    @Test
    public void testGetRecipeDetails() {
        assertEquals("Recipe Details", recipe.getRecipeDetails()); // Assert that the retrieved details match the
                                                                   // expected value
    }

    // Test case for getting the recipe list
    @Test
    public void testGetMealType() {
        assertEquals("Meal Type", recipe.getMealType()); // Assert that the retrieved recipe list matches the expected
                                                          // value
    }

    // Test case for setting the recipe name
    @Test
    public void testSetRecipeName() {
        String newRecipeName = "New Recipe Name";
        recipe.setRecipeName(newRecipeName);
        assertEquals(newRecipeName, recipe.getRecipeName()); // Assert that the set name matches the expected value
    }

    // Test case for setting the recipe details
    @Test
    public void testSetRecipe() {
        String newRecipe = "New Recipe";
        recipe.setRecipe(newRecipe);
        assertEquals(newRecipe, recipe.getRecipeDetails()); // Assert that the set details match the expected value
    }

    // Test case for marking the recipe as done
    @Test
    public void testIsDone() {
        assertEquals(false, recipe.isMarkedDone()); // Assert that the recipe is initially not marked as done
        recipe.markDone();
        assertEquals(true, recipe.isMarkedDone()); // Assert that marking the recipe as done is successful
    }

    // Test case for editing a recipe
    @Test
    public void testEditRecipe() {
        // Create a new recipe to edit
        String name = "Beef Curry";
        String details = "Make beef curry with carrots";
        String mealtype = "lunch";
        Recipe recipeToEdit = new Recipe(name, details, mealtype);
        assertEquals(name, recipeToEdit.getRecipeName()); // Assert that the retrieved name matches the expected value
        assertEquals(details, recipeToEdit.getRecipeDetails()); // Assert that the retrieved details match the expected
                                                                // value

        // Edit the recipe
        String newName = "Not Beef Curry";
        String newDetails = "don't make beef curry";
        recipeToEdit.setRecipe(newDetails);
        recipeToEdit.setRecipeName(newName);

        // Assert that the edited name and details match the expected values
        assertEquals(newName, recipeToEdit.getRecipeName());
        assertEquals(newDetails, recipeToEdit.getRecipeDetails());
    }
}
