package client;

import java.util.ArrayList;
import com.google.gson.Gson;

public class LoadData {
    private Model model;
    private ArrayList<?> recipes;
    private RecipeList recipeList;
    private String username;
    private Gson gson;
    private Sort sort;

    LoadData(String username, RecipeList recipeList) {
        this.model = new Model();
        this.recipeList = recipeList;
        this.gson = new Gson();
        this.sort = new Sort();
        this.username = username;
    }

    public void retrieveRecipes() {
        String response = model.sendRecipeRetrieveRequest(username);
        recipes = (ArrayList<?>) gson.fromJson(response, ArrayList.class);
    }

    public void populateRecipes() {
        for (int i = 0; i < recipes.size(); i++) {
            String recipeString = recipes.get(i).toString();
            String name = recipeString.substring(recipeString.indexOf("recipe_name=") + 12,
                    recipeString.indexOf("recipe_tag") - 2);
            String tag = recipeString.substring(recipeString.indexOf("recipe_tag=") + 11,
                    recipeString.indexOf("recipe_details") - 2);
            String details = recipeString.substring(recipeString.indexOf("recipe_details=") + 15,
                    recipeString.indexOf("creation_time") - 2);
            String imageLink = recipeString.substring(recipeString.indexOf("image_link=") + 11,
                    recipeString.length() - 1);

            Recipe recipe = new Recipe(name, tag, details);
            RecipeDetails recipeDetails = new RecipeDetails(recipeList);
            recipeDetails.setRecipe(recipe);
            recipeDetails.setImageLink(imageLink);
            recipeDetails.getDetails().uploadImage(imageLink);
            RecipeDisplay recipeDisplay = new RecipeDisplay(recipeDetails);
            recipeDetails.setRecipeDisplay(recipeDisplay);
            recipeDetails.updateTitleAndDetails(recipe);
            recipeDetails.setMealtype(tag);
            recipeDetails.enableDeleteAndEditAndShare();
            recipeDetails.disableSave();
            recipeDetails.disableRegenerate();
            recipeDetails.getDetails().makeTextEditable();
            recipeDisplay.setRecipeDisplayName(recipe);
            recipeList.getChildren().add(recipeDisplay);
            recipeList.updateRecipeIndices();
            sort.sortNewToOld(recipeList);
        }
    }

    public void loadRecipesForUser(String username, RecipeList recipeList) {
        this.username = username;
        this.recipeList = recipeList;
        this.retrieveRecipes();
        this.populateRecipes();
    }
}
