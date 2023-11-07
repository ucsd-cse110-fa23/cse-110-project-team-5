package anything;

import org.junit.jupiter.api.Test;
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
    public void testGetTitle() {
        assertEquals("Recipe Title", recipe.getTitle());
    }

    @Test
    public void testGetRecipe() {
        assertEquals("Recipe Content", recipe.getRecipe());
    }

    @Test
    public void testSetRecipe() {
        recipe.setRecipe("New Recipe");
        assertEquals("New Recipe", recipe.getRecipe());
    }
}