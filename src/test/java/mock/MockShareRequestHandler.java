package mock;

import org.bson.Document;
import org.json.JSONObject;

import server.MongoDB;

public class MockShareRequestHandler {
    MongoDB db;

    public MockShareRequestHandler() {
        db = new MongoDB();
    }

    public String handle(String method, String route, String query) {
        String response = "Invalid GET request";

        JSONObject res = new JSONObject();

        // Parse the query parameters
        if (query != null) {
            String value = query.substring(query.indexOf("=") + 1);
            
            // Extract tokens and message from the query
            String username = value.substring(0, value.indexOf(","));
            String recipeName = value.substring(value.indexOf(",") + 1).replaceAll("_", " ");

            if (username != null && recipeName != null) {
                try {
                    // retrieve recipe data from database
                    Document recipe;
                    recipe = db.readRecipe(username, recipeName);

                    if (recipe.getString("recipe_name") == null) {
                        return "No recipe found";
                    }

                    res.put("recipe_name", recipe.getString("recipe_name"));
                    res.put("recipe_tag", recipe.getString("recipe_tag"));
                    res.put("recipe_details", recipe.getString("recipe_details"));
                    res.put("creation_time", recipe.getString("creation_time"));
                    res.put("image_link", recipe.get("image_link"));

                    StringBuilder htmlBuilder = new StringBuilder();
                    htmlBuilder
                            .append("<html>")
                            .append("<body>")
                            .append("<h1>")
                            .append(recipe.getString("recipe_name"))
                            .append("</h1>")
                            .append("<h3>")
                            .append(recipe.getString("recipe_tag"))
                            .append("</h3>")
                            .append("<p>")
                            .append(recipe.getString("recipe_details"))
                            .append("</p>")
                            .append("<img src=\"" + recipe.getString("image_link") + "\">")
                            .append("</body>")
                            .append("</html>");

                    // encode HTML content
                    response = htmlBuilder.toString();
                } catch (Exception e) {
                    response = "Error";
                }
            } else {
                response = "No message query found";
            }
        }

        return response;
    }
    
}
