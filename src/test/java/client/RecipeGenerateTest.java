package client;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RecipeGenerateTest {
    RecipeGenerate recipeGen;

    @BeforeEach
    public void setUp(){
        recipeGen = new RecipeGenerate();
    }
    @Test
    public void toggleRecord_ShouldStartRecording_WhenNotAlreadyRecording() {
        recipeGen.toggleRecord();
        assertTrue(recipeGen.isRecording); 
    }

    @Test
    public void toggleRecord_ShouldStopRecording_WhenAlreadyRecording() {
        recipeGen.toggleRecord(); 
        recipeGen.toggleRecord(); 
        assertFalse(recipeGen.isRecording); 
    }

    /*
     * Testing a full BDD scenario where the user wants breakfast with sausage and eggs
     */
    @Test
    public void testBreakfastRecipe() {
        String actual = recipeGen.retrieveVoiceCommandResponse("breakfasttest.wav");
        String expected = "breakfast";
        if(actual.length() == 9) {
            assertEquals(expected, actual.toLowerCase());
        }
        else{
            assertEquals(expected + ".", actual.toLowerCase());
        }
        assertEquals(expected, recipeGen.mealType);
        String gptOutput = recipeGen.fetchGeneratedRecipe("ingredientstest.wav");
        assertTrue(gptOutput.contains("sausage") && gptOutput.contains("eggs"));
    }
    /*
     * Testing a full BDD scenario where the user doesn't specify a meal type and mealtype is null
     */
    @Test
    public void testNoMealType() {
        String input = recipeGen.retrieveVoiceCommandResponse("ingredientstest.wav");
        assertNull(recipeGen.mealType);
    }
}
