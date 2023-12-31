package client;

import java.util.ArrayList;
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
    private ArrayList<RecipePresenter> obs;

    private Header header;
    private Footer footer;
    private Details details;

    private Button saveButton;
    private Button saveChangesButton;
    private Button deleteButton;
    private Button shareButton;
    private Button regenerateButton;

    private RecipeList recipeList;
    private RecipeDisplay recipeDisplay;
    private Recipe recipe;

    private Share share;

    private Model model;

    private String imageLink;

    RecipeDetails(RecipeList recipeList) {
        this.obs = new ArrayList<>();
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
        this.regenerateButton = footer.getRegenerateButton();
        // Call Event Listeners for the Buttons
        addListeners();

        this.model = new Model();
    }

    // ShowDetails Header
    class Header extends HBox {
        Header() {
            this.setPrefSize(500, 60); // Size of the header
            this.setStyle("-fx-background-color: #A4C3B2;");

            Text titleText = new Text("Recipe Details"); // Text of the Header
            titleText.setFont(Font.font("Tahoma", FontWeight.BOLD, 22)); // Set Font and Size

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
        private Button regenerateButton;

        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #A4C3B2;");
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

            regenerateButton = new Button("Regenerate Recipe");
            regenerateButton.setStyle(defaultButtonStyle);
            regenerateButton.setDisable(false);

            this.getChildren().addAll(saveButton, saveChangesButton, deleteButton, shareButton, regenerateButton);
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

        public Button getRegenerateButton() {
            return this.regenerateButton;
        }
    }

    public void addListeners() {
        // Add button functionality
        saveButton.setOnAction(e -> {
            // Create new Recipe Object based on newly created recipe details
            this.recipe = new Recipe(this.getRecipeTitle(), this.getMealType(), this.getRecipeDetails());
            this.recipe.setImageLink(this.getImageLink());
            // Create RecipeDisplay Object to show Recipe in the Recipe List
            this.recipeDisplay = new RecipeDisplay(this);
            this.recipeDisplay.setRecipeDisplayName(this.recipe);
            this.recipeList.getChildren().add(0, recipeDisplay);
            this.recipeList.updateRecipeIndices();
            this.enableDeleteAndEditAndShare();
            this.disableSave();
            this.details.makeTextEditable();

            this.disableRegenerate();
            this.notifySave();
            
            if (User.getSavedUsername().length() > 0) {
                this.model.sendPostRecipeRequest(User.getSavedUsername(), this.recipe);
                //System.out.println("showRecipeList: " + User.getSavedUsername());
            } else {
                this.model.sendPostRecipeRequest(User.getUsername(), this.recipe);
                //System.out.println("showRecipeList: " + User.getUsername());
            }
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
            
            if (User.getSavedUsername().length() > 0) {
                this.model.sendRecipeDeleteRequest(User.getSavedUsername(), this.recipe);
                //System.out.println("showRecipeList: " + User.getSavedUsername());
            } else {
                this.model.sendRecipeDeleteRequest(User.getUsername(), this.recipe);
                //System.out.println("showRecipeList: " + User.getUsername());
            }
            Stage stage = (Stage) this.getScene().getWindow(); // Get the stage
            stage.close(); // Close the stage (window)
        });

        shareButton.setOnAction(e -> {
            this.share = new Share(User.getUsername(), recipe.getRecipeName());
            Share root = this.share;
            Stage shareStage = new Stage();

            Scene viewShareScene = new Scene(root, 500, 600);
            shareStage.setScene(viewShareScene);

            shareStage.show();
        });

        regenerateButton.setOnAction(e -> {
            notifyRegenerate();
        });
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    public void setRecipeDisplay(RecipeDisplay recipeDisplay){
        this.recipeDisplay = recipeDisplay;
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
        this.footer.getChildren().remove(this.saveButton);
    }

    public void disableRegenerate() {
        this.footer.getChildren().remove(this.regenerateButton);
    }

    public void register(RecipePresenter recipePresenter) {
        obs.add(recipePresenter);
    }

    public void notifyRegenerate() {
        for (RecipePresenter ob : obs)
            ob.notifyRegenerate();
    }

    public void notifySave() {
        for (RecipePresenter ob : obs)
            ob.notifySave();
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

    public Details getDetails() {
        return details;
    }

    public String getImageLink() {
        return this.imageLink;
    }

    public void setImageLink(String link) {
        this.imageLink = link;
    }
}