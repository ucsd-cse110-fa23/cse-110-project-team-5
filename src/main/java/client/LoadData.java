package client;

import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * The LoadData class is responsible for loading and populating recipes for a
 * given user.
 */
public class LoadData {
    private Model model;
    private ArrayList<?> recipes;
    private RecipeList recipeList;
    private String username;
    private Gson gson;
    private Sort sort;

    /**
     * Constructor for the LoadData class.
     *
     * @param username   The username of the user for whom recipes are loaded.
     * @param recipeList The RecipeList to which the loaded recipes will be added.
     */
    LoadData(String username, RecipeList recipeList) {
        this.model = new Model();
        this.recipeList = recipeList;
        this.gson = new Gson();
        this.sort = new Sort();
        this.username = username;
    }

    /**
     * Retrieves recipes from the server for the specified user.
     */
    public void retrieveRecipes() {
        // Send a request to the model to retrieve recipes for the given username
        String response = model.sendRecipeRetrieveRequest(username);
        // Parse the JSON response into an ArrayList of generic objects
        recipes = (ArrayList<?>) gson.fromJson(response, ArrayList.class);
    }

    /**
     * Populates the RecipeList with RecipeDisplay objects based on the retrieved
     * recipes.
     */
    public void populateRecipes() {
        // Iterate through each retrieved recipe
        for (int i = 0; i < recipes.size(); i++) {
            // Extract relevant information from the recipe string
            String recipeString = recipes.get(i).toString();
            String name = recipeString.substring(recipeString.indexOf("recipe_name=") + 12,
                    recipeString.indexOf("recipe_tag") - 2);
            String tag = recipeString.substring(recipeString.indexOf("recipe_tag=") + 11,
                    recipeString.indexOf("recipe_details") - 2);
            String details = recipeString.substring(recipeString.indexOf("recipe_details=") + 15,
                    recipeString.indexOf("creation_time") - 2);
            String imageLink = recipeString.substring(recipeString.indexOf("image_link=") + 11,
                    recipeString.length() - 1);

            // Create a new Recipe and associated RecipeDetails and RecipeDisplay objects
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

            // Add the RecipeDisplay to the RecipeList and update indices
            recipeList.getChildren().add(recipeDisplay);
            recipeList.updateRecipeIndices();

            // Sort the RecipeList based on the creation time in descending order (new to
            // old)
            sort.sortNewToOld(recipeList);
        }
    }

    /**
     * Loads recipes for the specified user and updates the provided RecipeList.
     *
     * @param username   The username of the user for whom recipes are loaded.
     * @param recipeList The RecipeList to which the loaded recipes will be added.
     */
    public void loadRecipesForUser(String username, RecipeList recipeList) {
        this.username = username;
        this.recipeList = recipeList;
        // Retrieve and populate recipes for the specified user
        this.retrieveRecipes();
        this.populateRecipes();
    }
}
