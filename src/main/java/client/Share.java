package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

class Share extends HBox {
    private TextField link;

    Share(String username, String recipeName) {
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-font-weight: bold; -fx-background-radius: 5;"); // Set background
                                                                                                     // color and style
        link = new TextField();
        link.setPrefSize(250,100);
        link.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        link.setPadding(new Insets(10, 0, 10, 0));
        link.setEditable(false);
        link.setText("http://localhost:8100/recipe?v=" + username + "," + recipeName);
        
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(link);

        // Stage shareStage = new Stage();
        // BorderPane sharePane = new BorderPane();
        // String url = "http://localhost:8100/";
        // // String link = url + "recipe?v=" + User.getUsername() + "," + recipe.getRecipeName();
        // String link = url + "recipe?v=" + "luffy" + "," + recipe.getRecipeName();
        // Text t = new Text("link: " + link);
        // t.setLayoutX(100);
        // t.setLayoutY(100);
        // sharePane.getChildren().add(t);

        // shareStage.show();
    }
}
