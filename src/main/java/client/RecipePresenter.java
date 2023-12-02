package client;

import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RecipePresenter {
    private RecipeList recipeList;

    private RecorderPresenter recorderPresenter;
    private String mealType;
    private String ingredients;

    RecipeGenerate recipeGenerate;

    RecipePresenter(RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recorderPresenter = new RecorderPresenter(this);
        this.recipeGenerate = new RecipeGenerate();
    }

    public void notify(String newRecipe) {
        this.mealType = recorderPresenter.getMealType();
        this.ingredients = recorderPresenter.getIngredients();
        generateRecipe(newRecipe);
    }

    private void generateRecipe(String newRecipe) {
        RecipeDetails recipeDetails = new RecipeDetails(recipeList);
        recipeDetails.setTitleAndDetails(newRecipe);
        recipeDetails.setMealtype(this.mealType);
        Scene scene = new Scene(recipeDetails);
        scene.setRoot(recipeDetails);
        // Stage recipeDetailStage = new Stage();
        Stage recipeDetailStage = recorderPresenter.getStage();
        recipeDetailStage.setScene(scene);
        recipeDetailStage.show();
        recipeDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                scene.setRoot(new BorderPane());
                recipeDetailStage.close();
            }
        });
    }
}
