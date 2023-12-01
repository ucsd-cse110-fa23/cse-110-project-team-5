package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class UserRecipes {
    private Model model;
    String response;
    UserRecipes() {
        model = new Model();
    }
    String getResponse() {
        String username = "luffy";
        String password = "saito";
        response = model.sendLoginRequest(username, password);
        return response;
    }
    String getTitle(String response) {
        String title = response.substring(response.indexOf("recipe_name") + 14, response.indexOf("recipe_tag") - 3);
        return title;
    }
    String getTag(String response) {
        String tag = response.substring(response.indexOf("recipe_tag") + 13, response.indexOf("recipe_details") - 3);
        return tag;
    }
    String getDetails(String response) {
        String details = response.substring(response.indexOf("recipe_details") + 17, response.indexOf("creation_time") - 3);
        return details;
    }
}
