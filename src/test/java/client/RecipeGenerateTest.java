package client;

import org.junit.jupiter.api.Test;

import client.Recipe;
import client.RecipeList;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
