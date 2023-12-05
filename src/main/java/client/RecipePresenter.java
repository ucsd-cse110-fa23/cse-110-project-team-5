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

    Scene scene;
    Stage recipeDetailStage;

    private Model model;

    RecipePresenter(RecipeList recipeList) {
        this.recipeList = recipeList;
        this.recorderPresenter = new RecorderPresenter(this);
        this.recipeGenerate = new RecipeGenerate();
        this.model = new Model();
    }

    public void notifyRecorder(String newRecipe) {
        this.mealType = recorderPresenter.getMealType();
        this.ingredients = recorderPresenter.getIngredients();
        displayNewRecipe(newRecipe);
    }

    private void displayNewRecipe(String newRecipe) {
        RecipeDetails recipeDetails = new RecipeDetails(recipeList);
        recipeDetails.register(this);
        recipeDetails.setTitleAndDetails(newRecipe);
        System.out.println(recipeDetails.getRecipeTitle());
        String l = model.sendImageRetrieveRequest(recipeDetails.getRecipeTitle());
        recipeDetails.setImageLink(l);
        recipeDetails.getDetails().uploadImage(recipeDetails.getImageLink());
        recipeDetails.setMealtype(this.mealType);
        scene = new Scene(recipeDetails);
        scene.setRoot(recipeDetails);
        
        recipeDetailStage = new Stage();
        recipeDetailStage.setScene(scene);
        recipeDetailStage.show();
        recipeDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                scene.setRoot(new BorderPane());
                recipeDetailStage.close();
            }
        });
    }

    // @require recipeDetailStage != null
    public void notifyRegenerate() {
        recipeDetailStage.close();
        String newRecipe = recipeGenerate.fetchGeneratedRecipe(this.ingredients, this.mealType);
        displayNewRecipe(newRecipe);
    }
    
    // @require recipeDetailStage != null
    public void notifySave() {
        scene.setRoot(new BorderPane());
        recipeDetailStage.close();
    }
}
