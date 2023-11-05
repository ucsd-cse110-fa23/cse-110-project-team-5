import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFrame extends BorderPane{
    public AppFrame() {
        ListView<String> recipeList = new ListView<>();
        this.setCenter(recipeList);
        HBox footer = new HBox(10);
        Button saveButton = new Button("Save Recipe");
        Button createButton = new Button("Create Recipe");
        footer.getChildren().addAll(saveButton, createButton);
        this.setBottom(footer);
        
    }
}
