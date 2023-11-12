package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

// The RecipeDetails class represents the details view for a recipe
class RecipeDetails extends BorderPane {
    private Header header;
    private Footer footer;
    private Details details;
    private Button saveButton;
    private Button deleteButton;

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

        // Add UI components to the BorderPane
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        // Get button references from the footer
        saveButton = footer.getSaveButton();
        deleteButton = footer.getDeleteButton();

        // Update title and details with the recipe from RecipeDisplay
        updateTitleAndDetails(recipeDisplay.getRecipe());

        // Add event listeners to the buttons
        addListeners();
    }

    // RecipeDetails Header
    class Header extends HBox {
        Header() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");

            // Text for the header
            Text titleText = new Text("Recipe Details");
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER);
        }
    }

    // RecipeDetails Footer
    class Footer extends HBox {
        private Button saveButton;
        private Button deleteButton;

        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // Default style for buttons
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            // Create "Save Changes" button
            saveButton = new Button("Save Changes");
            saveButton.setStyle(defaultButtonStyle);

            // Create "Delete Recipe" button
            deleteButton = new Button("Delete Recipe");
            deleteButton.setStyle(defaultButtonStyle);

            // Add buttons to the footer
            this.getChildren().addAll(saveButton, deleteButton);
            this.setAlignment(Pos.CENTER);
        }

        // Getter method for the "Save Changes" button
        public Button getSaveButton() {
            return saveButton;
        }

        // Getter method for the "Delete Recipe" button
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    // Add event listeners for buttons
    public void addListeners() {
        // Event listener for the "Save Changes" button
        saveButton.setOnAction(e -> {
            // Update the recipe details and name with the values from the Details view
            recipeDisplay.getRecipe().setRecipeName(details.getTitle());
            recipeDisplay.getRecipe().setRecipe(details.getDetails());
            // Update the displayed details in RecipeDetails
            updateTitleAndDetails(recipeDisplay.getRecipe());
            // Update the recipe name in RecipeDisplay
            recipeDisplay.setRecipeName(recipeDisplay.getRecipe());
        });

        // Event listener for the "Delete Recipe" button
        deleteButton.setOnAction(e -> {
            // Mark the recipe as done and remove it from the associated RecipeList
            recipeDisplay.getRecipe().markDone();
            recipeDisplay.getRecipe().getRecipeList().removeRecipe();
        });
    }

    // Update the title and details displayed in the Details view
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
