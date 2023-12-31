package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

class Share extends HBox {
    private TextField link;

    Share(String username, String recipeName) {
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-font-weight: bold; -fx-background-radius: 5;"); // Set background
                                                                                                     // color and style
        link = new TextField();
        link.setPrefSize(500,100);
        link.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        link.setPadding(new Insets(10, 0, 10, 0));
        link.setEditable(false);
        String mod = recipeName.replace(" ", "_");
        link.setText("http://localhost:8100/share?v=" + username + "," + mod);
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(link);
    }
}
