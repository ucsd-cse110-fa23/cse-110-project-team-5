package client;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

// Main AppFrame for Pantry Pal App 
class AppFrame extends BorderPane {
    // Declare instance variables
    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private Sort sort;
    private Filter filter;
    private ComboBox<String> sortComboBox;
    private ComboBox<String> filterComboBox;
    private Button createButton;
    private RecipePresenter recipePresenter;
    private ServerError serverError;
    private LoginScreen loginScreen;
    private LoadData loadData;

    // Constructor for AppFrame
    AppFrame() {
        // Initialize UI components
        header = new Header();
        recipeList = new RecipeList();
        footer = new Footer();

        loginScreen = new LoginScreen(this);

        ScrollPane scrollPane = new ScrollPane(loginScreen);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Configure layout of the BorderPane
        // this.setTop(header);
        // this.setCenter(loginScreen);
        // this.setBottom(footer);
        showLoginScreen();
        // showRecipeList();

        this.sort = header.getSort();
        this.filter = header.getFilter();
        this.sortComboBox = header.getSortComboBox();
        this.filterComboBox = header.getFilterComboBox();

        // Initialize and configure button
        this.createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons

        // Check for Server Error
        this.serverError = new ServerError(this.createButton);
        this.serverError.checkServerAvailability();
    }

    // App Header
    class Header extends HBox {
        private Sort sorter;
        private Filter filt;
        ComboBox<String> sort;
        ComboBox<String> filter;

        // Constructor for Header
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #A4C3B2;");
            // Add Dropdowns
            sort = new ComboBox<>();
            filter = new ComboBox<>();
            sort.setPromptText("Sort By");
            filter.setPromptText("Filter Recipes");
            sort.getItems().addAll("Newest to Oldest", "Oldest to Newest", "A - Z", "Z - A");
            filter.getItems().addAll("Breakfast", "Lunch", "Dinner", "All");
            HBox.setMargin(sort, new Insets(0, 10, 0, 10));
            HBox.setMargin(filter, new Insets(0, 10, 0, 10));
            // Add "Recipe List" Title
            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            // Create containers for elements
            HBox titleBox = new HBox(titleText);
            HBox filterBox = new HBox(filter);
            HBox sortBox = new HBox(sort);
            // Set alignments for elements
            titleBox.setAlignment(Pos.CENTER);
            sortBox.setAlignment(Pos.CENTER_LEFT);
            sort.setStyle("-fx-background-radius: 5;");
            filterBox.setAlignment(Pos.CENTER_RIGHT);
            filter.setStyle("-fx-background-radius: 5;");
            // Add elements to the header
            this.getChildren().addAll(sortBox, titleBox, filterBox);
            HBox.setHgrow(titleBox, Priority.ALWAYS);
            this.sorter = new Sort();
            this.filt = new Filter();

            // Add sort option functionality
            sort.setOnAction(e -> {
                String selectedOption = sort.getSelectionModel().getSelectedItem();
                // Perform actions based on the selected option
                // Sort Recipe List in Chronological Order
                if (selectedOption == "Newest to Oldest") {
                    this.sorter.sortNewToOld(recipeList);
                }
                // Sort Recipe List in Reverse Chronological Order
                else if (selectedOption == "Oldest to Newest") {
                    this.sorter.sortOldToNew(recipeList);
                }
                // Sort Recipe List in Lexographical Order
                else if (selectedOption == "A - Z") {
                    this.sorter.sortAZ(recipeList);
                }
                // Sort Recipe List in Reverese Lexographical Order
                else if (selectedOption == "Z - A") {
                    sorter.sortZA(recipeList);
                }
            });
            filter.setOnAction(e -> {
                String selectedOption = filter.getSelectionModel().getSelectedItem();
                if (selectedOption == "Breakfast") {
                    filt.filter(recipeList, "Breakfast");
                } else if (selectedOption == "Lunch") {
                    filt.filter(recipeList, "Lunch");
                } else if (selectedOption == "Dinner") {
                    filt.filter(recipeList, "Dinner");
                } else if (selectedOption == "All") {
                    filt.filter(recipeList, "All");
                }
            });
        }

        private Sort getSort() {
            return this.sorter;
        }

        private Filter getFilter() {
            return this.filt;
        }

        private ComboBox<String> getSortComboBox() {
            return this.sort;
        }

        private ComboBox<String> getFilterComboBox() {
            return this.filter;
        }
    }

    // App Footer
    class Footer extends HBox {
        // Declare instance variable
        private Button createButton;

        // Constructor for Footer
        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #A4C3B2;"); // #F0F8FF
            this.setSpacing(15);

            // set a default style for buttons - background color, font size, italics
            String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

            createButton = new Button("New Recipe"); // text displayed on add button
            createButton.setStyle(defaultButtonStyle); // styling the button

            this.getChildren().addAll(createButton); // adding buttons to footer
            this.setAlignment(Pos.CENTER); // aligning the buttons to center
        }

        // Getter for createButton
        public Button getCreateButton() {
            return createButton;
        }
    }

    // Method to add event listeners to buttons
    public void addListeners() {
        // Add button functionality
        createButton.setOnAction(e -> {
            // ComboBox<String> sortComboBox = getSortComboBox();
            // ComboBox<String> filterComboBox;
            sortComboBox.setValue("Newest to Oldest");
            filterComboBox.setValue("All");
            sort.sortNewToOld(recipeList);
            filter.filter(recipeList, "All");
            if (this.serverError.checkServerAvailability()) {
                recipePresenter = new RecipePresenter(recipeList);
            }
        });
    }

    public void showRecipeList() {
        loadData = new LoadData(User.getUsername(), recipeList);
        loadData.retrieveRecipes();
        loadData.populateRecipes();
        this.setTop(header);
        this.setCenter(recipeList);
        this.setBottom(footer);
    }

    public void showLoginScreen() {
        this.setCenter(loginScreen);
    }
}
