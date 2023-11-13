package client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import client.Recipe;
import client.RecipeList;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class RecipeGenTest {
    private RecipeGenerate recipeGen;

    @BeforeEach
    public void setUp(){
        recipeGen = new RecipeGenerate();
    }
    @Test
    public void testToggleRecord() {
        assertFalse(recipeGen.isRecording);
        recipeGen.toggleRecord();
        assertTrue(recipeGen.isRecording);
        recipeGen.toggleRecord();
        assertFalse(recipeGen.isRecording);
    }
    
    // @Test
    // public void testStartAndStopAudioRecording() {
    //     recipeGen.startAudioRecording();
    //     assertTrue(recipeGen.recordingLabel.isVisible());

    //     recipeGen.stopAudioRecording();
    //     assertFalse(recipeGen.recordingLabel.isVisible());
    // }


}
