package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    private Button logoutButton;
    private RecipePresenter recipePresenter;
    private ServerError serverError;
    private LoginScreen loginScreen;
    private LoadData loadData;
    private ScrollPane listPane;

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
        this.sort = header.getSort();
        this.filter = header.getFilter();
        this.sortComboBox = header.getSortComboBox();
        this.filterComboBox = header.getFilterComboBox();

        // Initialize and configure button
        this.createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons

        // Check for Server Error
        this.serverError = new ServerError(this.createButton);

        if (this.serverError.checkServerAvailability()) {
            if (User.isRemembered()) {
                // Auto-login
                recipeList.setUsername(User.getSavedUsername());
                showRecipeList();
            } else {
                showLoginScreen();
            }
        }
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
            // Create Logout button
            logoutButton = new Button("Logout");
            logoutButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white; -fx-background-radius: 5;"); // Style
                                                                                                                     // logout
                                                                                                                     // button
            // Create Sort Dropdown
            sort = new ComboBox<>();
            sort.setPromptText("Sort By");
            sort.getItems().addAll("Newest to Oldest", "Oldest to Newest", "A - Z", "Z - A"); // Fill dropdown options
            sort.setStyle("-fx-background-radius: 5;"); // Set Sort style
            sort.setPrefWidth(134); // Set a preferred width for the sort dropdown
            // Create Filter Dropdown
            filter = new ComboBox<>();
            filter.setPromptText("Filter Recipes");
            filter.getItems().addAll("Breakfast", "Lunch", "Dinner", "All"); // Fill dropdown options
            filter.setStyle("-fx-background-radius: 5;"); // Set Filter style
            filter.setPrefWidth(134); // Set a preferred width for the sort dropdown
            // Create "Recipe List" Title
            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setFont(Font.font("Tahoma", FontWeight.BOLD, 23)); // Set Font and Size
            // Create containers for elements
            VBox logoutAndSortBox = new VBox(logoutButton, sort);
            HBox titleBox = new HBox(titleText);
            HBox filterBox = new HBox(filter);
            // Configure spacing and padding
            logoutAndSortBox.setPadding(new Insets(8, 10, 8, 10)); // Add padding to the VBox
            logoutAndSortBox.setSpacing(5);
            filterBox.setPadding(new Insets(38, 10, 8, 10)); // Add padding to the VBox
            // Set alignments for elements
            logoutAndSortBox.setAlignment(Pos.CENTER_LEFT);
            titleBox.setAlignment(Pos.CENTER);
            filterBox.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(titleBox, Priority.ALWAYS);
            // Add elements to the header
            this.getChildren().addAll(logoutAndSortBox, titleBox, filterBox);
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

        public Button getLogoutButton() {
            return logoutButton;
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
            sortComboBox.setValue("Newest to Oldest");
            filterComboBox.setValue("All");
            sort.sortNewToOld(recipeList);
            filter.filter(recipeList, "All");
            if (this.serverError.checkServerAvailability()) {
                recipePresenter = new RecipePresenter(recipeList);
            }
        });

        header.getLogoutButton().setOnAction(e -> {
            this.recipeList.removeAll();
            this.setTop(null); // Remove the header
            this.setCenter(loginScreen); // Set the center to the login screen
            this.setBottom(null); // Remove the footer
            User.saveLoginState(false);

        });
    }

    public void showRecipeList() {
        if (User.getSavedUsername().length() > 0) {
            loadData = new LoadData(User.getSavedUsername(), recipeList);
            System.out.println("showRecipeList: " + User.getSavedUsername());
        } else {
            loadData = new LoadData(User.getUsername(), recipeList);
            System.out.println("showRecipeList: " + User.getUsername());
        }
        loadData.retrieveRecipes();
        loadData.populateRecipes();
        ScrollPane listPane = new ScrollPane(recipeList);
        listPane.setFitToWidth(true);
        listPane.setFitToHeight(true);
        listPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.setTop(header);
        this.setCenter(listPane);
        this.setBottom(footer);
    }

    public void showLoginScreen() {
        this.setCenter(loginScreen);
    }
}
