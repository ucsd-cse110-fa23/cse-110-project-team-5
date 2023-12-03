package client;

import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RecipePresenter {
    private RecipeList recipeList;
    RecipeGenerate recipeGenerate;
    private RecorderPresenter recorderPresenter;

    private String mealType;
    private String ingredients;

    Stage recipeDetailStage;
    Scene scene;

    RecipePresenter(RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recipeGenerate = new RecipeGenerate();
        this.recorderPresenter = new RecorderPresenter(this);

        recipeDetailStage = new Stage();
    }

    public void notifyRecorder(String newRecipe) {
        this.mealType = recorderPresenter.getMealType();
        this.ingredients = recorderPresenter.getIngredients();
        displayNewRecipe(newRecipe);
    }

    private void displayNewRecipe(String newRecipe) {
        RecipeDetails recipeDetails = new RecipeDetails(recipeList, new Recipe(newRecipe, this.mealType));
        recipeDetails.register(this);

        scene = new Scene(recipeDetails);
        
        recipeDetailStage.setScene(scene);
        recipeDetailStage.show();
        recipeDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                scene.setRoot(new BorderPane());
                recipeDetailStage.close();
            }
        });
    }

    public void notifySave(RecipeDisplay recipeDisplay) {
        scene = new Scene(new BorderPane());
        recipeDetailStage.close();
        recipeList.addRecipe(recipeDisplay);
    }

    public void notifyRegenerate() {
        String newRecipe = recipeGenerate.fetchGeneratedRecipe(this.ingredients, this.mealType);
        displayNewRecipe(newRecipe);
    }
}
