package client;

import javafx.scene.layout.*;

class RecipeList extends VBox {
    RecipeList() {
        this.setSpacing(5); // sets spacing between recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateRecipeIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof RecipeDisplay) {
                ((RecipeDisplay) this.getChildren().get(i)).setRecipeIndex(index);
                index++;
            }
        }
    }
}
