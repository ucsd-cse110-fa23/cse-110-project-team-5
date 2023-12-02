package client;

import java.util.ArrayList;

public class LoadData {
    private Model model;
    private ArrayList<Recipe> recipes;
    private String username;

    LoadData(String username) {
        this.model = new Model();
        this.username = username;
    }

    ArrayList<Recipe> retrieveRecipes() {
        return null;
    }
}
