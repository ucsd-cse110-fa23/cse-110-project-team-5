package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

// Class representing the detailed view of a recipe
class RecipeDetails extends BorderPane {
    // Instance variables
    private Header header;
    private Footer footer;
    private Details details;
    private Button saveButton;

    private RecipeDisplay recipeDisplay;

    // Constructor for RecipeDetails
    RecipeDetails(RecipeDisplay recipeDisplay) {
        this.recipeDisplay = recipeDisplay;

        // Initialize UI components
        header = new Header();
        details = new Details();
        footer = new Footer();
        ScrollPane scrollPane = new ScrollPane(details);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);
        saveButton = footer.getSaveButton();

        // Update title and details from the current recipe
        updateTitleAndDetails(recipeDisplay.getRecipe());

        // Set up event listeners for buttons
        addListeners();
    }

    // RecipeDetails Header
    class Header extends HBox {
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #F0F8FF;");

            Text titleText = new Text("Recipe Details"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
        }
    }

    // RecipeDetails Footer
    class Footer extends HBox {
        // Footer instance variables
        private Button saveButton;
        // Footer constructor
        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // Set a default style for buttons - background color, font size, italics
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            saveButton = new Button("Save Changes"); // Text displayed on save button
            saveButton.setStyle(defaultButtonStyle); // Styling the save button
           
            this.getChildren().addAll(saveButton); // Adding buttons to the footer
            this.setAlignment(Pos.CENTER); // Aligning the buttons to center
        }

        // Getter for the saveButton
        public Button getSaveButton() {
            return saveButton;
        }

    }

    // Method to add event listeners to buttons
    public void addListeners() {
        // Add button functionality for saveButton
        saveButton.setOnAction(e -> {
            recipeDisplay.getRecipe().setRecipeName(details.getTitle());
            recipeDisplay.getRecipe().setRecipe(details.getDetails());
            updateTitleAndDetails(recipeDisplay.getRecipe());
            recipeDisplay.setRecipeDisplayName(recipeDisplay.getRecipe());
        });
    }

    // Method to update title and details from a given recipe
    public void updateTitleAndDetails(Recipe recipe) {
        details.setTitle(recipe.getRecipeName());
        details.setDetails(recipe.getRecipeDetails());
    }

    public String getTitle() {
        return details.getTitle();
    }

    public String getDetails() {
        return details.getDetails();
    }
}