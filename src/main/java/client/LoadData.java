package client;

import java.util.ArrayList;

import com.google.gson.Gson;

public class LoadData {
    private Model model;
    private ArrayList<Recipe> recipes;
    private String username;
    private Gson gson;


    LoadData(String username) {
        this.model = new Model();
        this.gson = new Gson();
        this.username = username;
    }

    public ArrayList<Recipe> retrieveRecipes() {
        String response = model.sendRecipeRetrieveRequest(username);
        recipes = (ArrayList<Recipe>) gson.fromJson(response, ArrayList.class);
        return recipes;
    }
}
