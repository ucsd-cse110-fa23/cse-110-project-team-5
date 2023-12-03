package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

// Window that shows newly created Recipes
class RecipeDetails extends BorderPane {
    private Header header;
    private Footer footer;
    private Details details;

    private Button saveButton;
    private Button saveChangesButton;
    private Button deleteButton;
    private Button shareButton;

    private RecipeList recipeList;
    private RecipeDisplay recipeDisplay;
    private Recipe recipe;

    private Share share;

    RecipeDetails(RecipeList recipeList) {
        // Recipe List to add newly created Recipe Object to
        this.recipeList = recipeList;
        // Initialise the header Object
        header = new Header();
        // Create a details Object to hold the recipe details
        details = new Details();
        // Initialise the Footer Object
        footer = new Footer();
        // Initialise ScrollPane Object
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
        // Initialise Save Button through the getters in Footer
        this.saveButton = footer.getSaveButton();
        this.saveChangesButton = footer.getSaveChangesButton();
        this.deleteButton = footer.getDeleteButton();
        this.shareButton = footer.getShareButton();
        // Call Event Listeners for the Buttons
        addListeners();
    }

    // ShowDetails Header
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

    // ShowDetails Footer
    class Footer extends HBox {
        private Button saveButton;
        private Button saveChangesButton;
        private Button deleteButton;
        private Button shareButton;

        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
            this.setSpacing(15);

            // set a default style for buttons - background color, font size, italics
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            saveButton = new Button("Save Recipe"); // text displayed on add button
            saveButton.setStyle(defaultButtonStyle); // styling the button
            saveButton.setDisable(false);

            saveChangesButton = new Button("Save Changes"); // Text displayed on save button
            saveChangesButton.setStyle(defaultButtonStyle); // Styling the save button
            saveChangesButton.setDisable(true);

            deleteButton = new Button("Delete Recipe"); // Text displayed on delete button
            deleteButton.setStyle(defaultButtonStyle); // Styling the delete button
            deleteButton.setDisable(true);

            shareButton = new Button("Share Recipe"); // Text displayed on delete button
            shareButton.setStyle(defaultButtonStyle); // Styling the delete button
            shareButton.setDisable(true);

            this.getChildren().addAll(saveButton, saveChangesButton, deleteButton, shareButton); // adding buttons to
                                                                                                 // footer
            this.setAlignment(Pos.CENTER); // aligning the buttons to center
        }

        public Button getSaveButton() {
            return this.saveButton;
        }

        public Button getSaveChangesButton() {
            return this.saveChangesButton;
        }

        public Button getDeleteButton() {
            return this.deleteButton;
        }

        public Button getShareButton() {
            return this.shareButton;
        }
    }

    public void addListeners() {
        // Add button functionality
        saveButton.setOnAction(e -> {
            // Create new Recipe Object based on newly created recipe details
            this.recipe = new Recipe(this.getRecipeTitle(), this.getRecipeDetails(), this.getMealType());
            // Create RecipeDisplay Object to show Recipe in the Recipe List
            this.recipeDisplay = new RecipeDisplay(this);
            this.recipeDisplay.setRecipeDisplayName(this.recipe);
            this.recipeList.getChildren().add(0, recipeDisplay);
            this.recipeList.updateRecipeIndices();
            this.enableDeleteAndEditAndShare();
            this.disableSave();
            this.details.makeTextEditable();
        });

        // Add button functionality for saveButton
        saveChangesButton.setOnAction(e -> {
            this.recipe.setRecipeName(details.getTitle());
            this.recipe.setRecipe(details.getDetails());
            updateTitleAndDetails(this.recipe);
            recipeDisplay.setRecipeDisplayName(this.recipe);
        });

        // Add button functionality for deleteButton
        deleteButton.setOnAction(e -> {
            this.recipe.markDone();
            this.recipeList.removeRecipe();
            this.saveButton.setDisable(true);
            this.saveChangesButton.setDisable(true);
            this.shareButton.setDisable(true);
            this.deleteButton.setDisable(true);
        });

        shareButton.setOnAction(e -> {
            // this.share = new Share(User.getUsername(), recipe.getRecipeName());
            this.share = new Share("temp username", "temp recipe name");
            Share root = this.share;
            Stage shareStage = new Stage();

            Scene viewShareScene = new Scene(root, 500, 600);
            shareStage.setScene(viewShareScene);

            shareStage.show();

            // RecipeList root = this.recipeList;
            // Stage shareStage = new Stage();
            // Scene viewShareScene = new Scene(root, 500, 600);
            // shareStage.setScene(viewShareScene);
            // shareStage.show();
        });
    }

    // Set Title and Details of Details VBox
    public void setTitleAndDetails(String recipeString) {
        details.setTitle(details.extractTitle(recipeString));
        details.setDetails(details.extractDetails(recipeString));
    }

    public void setMealtype(String mealType) {
        details.setMealType(mealType);
    }

    // Method to update title and details from a given recipe
    public void updateTitleAndDetails(Recipe recipe) {
        details.setTitle(recipe.getRecipeName());
        details.setDetails(recipe.getRecipeDetails());
    }

    public void enableDeleteAndEditAndShare() {
        this.deleteButton.setDisable(false);
        this.saveChangesButton.setDisable(false);
        this.shareButton.setDisable(false);
    }

    public void disableSave() {
        this.saveButton.setDisable(true);
    }

    public String getRecipeTitle() {
        return details.getTitle();
    }

    public String getRecipeDetails() {
        return details.getDetails();
    }

    public String getMealType() {
        return details.getMealType();
    }

    public Recipe getRecipe() {
        return recipe;
    }
}