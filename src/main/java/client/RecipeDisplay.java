package client;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

// Represents the graphical display of a single recipe in the RecipeList
class RecipeDisplay extends HBox {
    // Instance variables
    private Label index;
    private TextField mealTypeTag;
    private TextArea recipeName;
    private Button detailButton;
    private RecipeDetails recipeDetails;

    // Constructor for RecipeDisplay
    RecipeDisplay(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #CCE3DE; -fx-font-weight: bold; -fx-background-radius: 7;"); // Set background
                                                                                                     // color and style
        
        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";                                                                                

        // Create and configure the index label
        index = new Label();
        index.setText(""); // Initialize index label text
        index.setPrefSize(20, 20); // Set size of the index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(0, 0, 0, 10)); // Add padding to the recipe

        // Create meal type tag
        mealTypeTag = new TextField();
        mealTypeTag.setPrefSize(65,20); // Set size of the index label
        mealTypeTag.setAlignment(Pos.CENTER); // Set alignment of index label
        mealTypeTag.setEditable(false); // Make the text field non-editable
        mealTypeTag.setText(this.recipeDetails.getRecipe().getMealType()); // Set tag text
        // Set colors for each type of mealtype tag
        if (this.recipeDetails.getRecipe().getMealType().equals("Breakfast")) {
            mealTypeTag.setStyle("-fx-background-color: #23AEFB; -fx-padding: 5px; -fx-background-radius: 10;");
        }
        else if (this.recipeDetails.getRecipe().getMealType().equals("Lunch")) {
            mealTypeTag.setStyle("-fx-background-color: #F2E147; -fx-padding: 5px; -fx-background-radius: 10;");
        } 
        else if (this.recipeDetails.getRecipe().getMealType().equals("Dinner")) {
            mealTypeTag.setStyle("-fx-background-color: #FF6961; -fx-padding: 5px; -fx-background-radius: 10;");
        }

        // Create and configure the recipe name text area
        recipeName = new TextArea();
        recipeName.setPrefSize(275, 40); // Set size of the text area
        recipeName.setEditable(false); // Make the text area non-editable
        recipeName.setWrapText(true); // Enable text wrapping
        recipeName.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-padding: 5 0 5 0; -fx-text-alignment: center; -fx-font-size: 14px;");
        HBox.setHgrow(recipeName, Priority.ALWAYS); // Allow horizontal growth
        // Create and configure the "View Details" button
        detailButton = new Button("View Details");
        detailButton.setStyle(defaultButtonStyle); // Set style of the button
        detailButton.setOnAction(e -> { // Add button functionality to open RecipeDetails window
            RecipeDetails root = this.recipeDetails;
            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(root, 500, 600);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();
            viewDetailStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    viewDetailScene.setRoot(new BorderPane());
                    viewDetailStage.close();
                }
            });
        });
        HBox indexBox = new HBox(index);
        indexBox.setPadding(new Insets(0, 5, 0, 0)); // Add padding 
        HBox tagBox = new HBox(mealTypeTag);
        tagBox.setPadding(new Insets(0, 5, 0, 5)); // Add padding 
        HBox nameBox = new HBox(recipeName);
        nameBox.setPadding(new Insets(0, 5, 0, 5)); // Add padding
        HBox detBox = new HBox(detailButton);
        detBox.setPadding(new Insets(0, 10, 0, 5)); // Add padding 
        HBox.setHgrow(nameBox, Priority.ALWAYS);
        // Set alignments for elements
        indexBox.setAlignment(Pos.CENTER);
        tagBox.setAlignment(Pos.CENTER_LEFT);
        tagBox.setStyle("-fx-background-radius: 10;");
        nameBox.setAlignment(Pos.CENTER);
        detBox.setAlignment(Pos.CENTER_RIGHT);

        // Add elements to the header
        this.getChildren().addAll(indexBox, tagBox, nameBox, detBox);
    }

    // Set Recipe Index and prompt text for the text field
    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // Convert num to String and set index label text
        this.recipeName.setPromptText("Recipe " + num); // Set prompt text for the recipe name text field
    }

    public Button getDetailButton() {
        return this.detailButton;
    }

    // Set the displayed name of the recipe in the text field
    public void setRecipeDisplayName(Recipe recipe) {
        this.recipeName.setText(recipe.getRecipeName());
    }

    public Recipe getRecipe() {
        return this.recipeDetails.getRecipe();
    }

    public String getTitle() {
        return this.recipeName.getText();
    }
}