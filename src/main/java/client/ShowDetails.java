package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

// The ShowDetails class represents a window for displaying initial recipe details
class ShowDetails extends BorderPane {
    private Header header;
    private Footer footer;
    private Details details;

    private Button saveButton;

    private RecipeList recipeList;
    private Recipe recipe;

    // Constructor for ShowDetails
    ShowDetails(RecipeList recipeList) {
        this.recipeList = recipeList;

        // Initialize the header Object
        header = new Header();
        // Create a details Object to hold the recipe details
        details = new Details();
        // Initialize the Footer Object
        footer = new Footer();

        ScrollPane scrollPane = new ScrollPane(details);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scrollPane);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
        // Initialize Button Variables through the getters in Footer
        saveButton = footer.getSaveButton();
        // Call Event Listeners for the Buttons
        addListeners();
    }

    // RecipeDetails Header
    class Header extends HBox {
        Header() {
            this.setPrefSize(500, 60); // Size of the header
            this.setStyle("-fx-background-color: #F0F8FF;");

            Text titleText = new Text("Recipe Details"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
        }
    }

    // RecipeDetails Footer
    class Footer extends HBox {
        private Button saveButton;

        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // set a default style for buttons - background color, font size, italics
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            saveButton = new Button("Save Recipe"); // text displayed on add button
            saveButton.setStyle(defaultButtonStyle); // styling the button

            this.getChildren().add(saveButton); // adding buttons to footer
            this.setAlignment(Pos.CENTER); // aligning the buttons to center
        }

        public Button getSaveButton() {
            return saveButton;
        }
    }

    // Add button functionality
    public void addListeners() {
        saveButton.setOnAction(e -> {
            // Create a new recipe with the title and details from the window
            recipe = new Recipe(this.getRecipeTitle(), this.getRecipeDetails(), recipeList);
            RecipeDisplay recipeDisplay = new RecipeDisplay(recipe);
            recipeList.getChildren().add(recipeDisplay);
            recipeList.updateRecipeIndices();
        });
    }

    public void setTitleAndDetails(String recipeString) {
        details.setTitle(details.extractTitle(recipeString));
        details.setDetails(details.extractDetails(recipeString));
    }

    public String getRecipeTitle() {
        return details.getTitle();
    }

    public String getRecipeDetails() {
        return details.getDetails();
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
