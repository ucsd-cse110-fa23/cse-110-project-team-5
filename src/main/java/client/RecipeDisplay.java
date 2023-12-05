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
import javafx.geometry.Pos;

// Represents the graphical display of a single recipe in the RecipeList
class RecipeDisplay extends HBox {
    // Instance variables
    private Label index;
    // private TextField recipeName;
    private TextField mealTypeTag;
    private TextArea recipeName;
    private Button detailButton;
    private RecipeDetails recipeDetails;

    // Constructor for RecipeDisplay
    RecipeDisplay(RecipeDetails recipeDetails) {
        this.recipeDetails = recipeDetails;
        this.setPrefSize(500, 20); // Set size of the RecipeDisplay
        this.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-font-weight: bold; -fx-background-radius: 5;"); // Set background
                                                                                                     // color and style
        
        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";                                                                                

        // Create and configure the index label
        index = new Label();
        index.setText(""); // Initialize index label text
        index.setPrefSize(40, 20); // Set size of the index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the recipe

        // Create meal type tag
        mealTypeTag = new TextField();
        mealTypeTag.setPrefSize(100,20);
        mealTypeTag.setStyle("-fx-background-color: #FFFFFF; -fx-border-width:0; -fx-padding: 5px;");
        mealTypeTag.setPadding(new Insets(10, 0, 10, 0));
        mealTypeTag.setEditable(false);
        mealTypeTag.setText(this.recipeDetails.getRecipe().getMealType());

        // Create and configure the recipe name text area
        recipeName = new TextArea();
        recipeName.setPrefSize(275, 40); // Set size of the text area
        recipeName.setEditable(false); // Make the text area non-editable
        recipeName.setWrapText(true); // Enable text wrapping
        recipeName.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0; -fx-padding: 5 0 5 0; -fx-background-radius: 10; -fx-text-alignment: center; -fx-font-size: 14px;");
        recipeName.setEditable(false);

        // Create and configure the "View Details" button
        detailButton = new Button("View Details");
        // detailButton.setPrefSize(100, 20); // Set size of the button
        // detailButton.setPrefHeight(Double.MAX_VALUE);
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
        HBox tagBox = new HBox(mealTypeTag);
        HBox nameBox = new HBox(recipeName);
        HBox detBox = new HBox(detailButton);
        // Set alignments for elements
        indexBox.setAlignment(Pos.CENTER);
        tagBox.setAlignment(Pos.CENTER_LEFT);
        tagBox.setStyle("-fx-background-radius: 5;");
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