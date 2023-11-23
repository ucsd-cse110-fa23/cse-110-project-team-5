package client;

// Represents the generation and handling of recipes through voice commands
public class RecipeGenerate {
    private String recipeIntro = "Add_a_one_line_title_at_the_start_,_add_a_new_line_,_and_give_me_a_recipe_for_"; // Introductory text for recipe request
    private String recipeIntro2 = "and_only_use_the_following_ingredients_and_nothing_else_other_than_simple_ingredients:"; // Additional text for recipe request
    private String recipeFormat = "_and_format_the_output_like_a_recipe";

    RecipeGenerate(){}
    
    // Generate a recipe based on the voice input from the user
    public String fetchGeneratedRecipe(String ingredients, String mealType) {
        Model model = new Model();
        String gptResponse = "";
        try {
            // Retrieve ingredients from the voice command response
            if(ingredients.length() == 0) {
                return "NO INPUT";
            }
            // Construct and perform a GET request to the 'gpt' endpoint with the necessary
            // parameters
            gptResponse = mealType + ": " + model.performRequest("GET", "gpt",
                    "500," + recipeIntro + mealType + recipeIntro2 + ingredients + recipeFormat);
        } catch (Exception e) {
            System.out.println("No input detected");
        }
        return gptResponse;
    }
}
