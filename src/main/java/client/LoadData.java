package client;

import java.util.ArrayList;

import com.google.gson.Gson;

public class LoadData {
    private Model model;
    private ArrayList<Recipe> recipes;
    private String username;
    private String password;
    private Gson gson;


    LoadData(String username, String password) {
        this.model = new Model();
        this.gson = new Gson();
        this.username = username;
        this.password = password;
    }

    public ArrayList<Recipe> retrieveRecipes() {
        String response = model.sendLoginRequest(username, password);
        recipes = (ArrayList<Recipe>) gson.fromJson(response, ArrayList.class);
        return recipes;
    }
}
