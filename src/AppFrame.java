import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

class AppFrame extends BorderPane {
    private Header header;
    private Footer footer;
    private RecipeList recipeList;

    private Button createButton;

    AppFrame() {
        // Initialise the header Object
        header = new Header();
        // Create a tasklist Object to hold the tasks
        recipeList = new RecipeList();
        // Initialise the Footer Object
        footer = new Footer();
        ScrollPane scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scrollPane);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
        // Initialise Button Variables through the getters in Footer
        createButton = footer.getCreateButton();
        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners() {
        // Add button functionality
        createButton.setOnAction(e -> {
            // Create a new task
            Recipe recipe = new Recipe();
            // Add task to tasklist
            recipeList.getChildren().add(recipe);
            // Update task indices
            recipeList.updateTaskIndices();
        });
    }
}