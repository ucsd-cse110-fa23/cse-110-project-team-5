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

// Main AppFrame for Pantry Pal App 
class AppFrame extends BorderPane {
    // Declare instance variables
    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private Button createButton;
    private Recorder recorder;
    private ServerError serverError;

    // Constructor for AppFrame
    AppFrame() {
        // Initialize UI components
        this.header = new Header();
        this.recipeList = new RecipeList();
        this.footer = new Footer();

        this.recorder = new Recorder(recipeList);

        ScrollPane scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Configure layout of the BorderPane
        this.setTop(header);
        this.setCenter(scrollPane);
        this.setBottom(footer);

        // Initialize and configure button
        this.createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons

        // Check for Server Error
        this.serverError = new ServerError(this.createButton);
        this.serverError.checkServerAvailability();
    }

    // App Header
    class Header extends HBox {
        private ComboBox<String> filter;
        // Constructor for Header
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #F0F8FF;");

            // Add "Recipe List" Title
            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            // Add "Filter Recipes" Dropdown
            filter = new ComboBox<>();
            filter.setPromptText("Filter Recipes");
            filter.getItems().addAll("Breakfast", "Lunch", "Dinner", "All");
            HBox.setMargin(filter, new Insets(0,10,0,10));
            // Create containers for elements
            HBox filterBox = new HBox(filter);
            HBox titleBox = new HBox(titleText);
            // Set alignments for elements
            filterBox.setAlignment(Pos.CENTER_RIGHT);
            filter.setStyle("-fx-background-radius: 5;");
            titleBox.setAlignment(Pos.CENTER);
            // Add elements to the header
            this.getChildren().addAll(titleBox, filterBox);
            HBox.setHgrow(titleBox, Priority.ALWAYS);
            Filter filt = new Filter();
            filter.setOnAction(e -> {
                String selectedOption = filter.getSelectionModel().getSelectedItem();
                if (selectedOption == "Breakfast") {
                    filt.filter(recipeList, "Breakfast");
                }
                else if (selectedOption == "Lunch") {
                    filt.filter(recipeList, "Lunch");
                }
                else if (selectedOption == "Dinner") {
                    filt.filter(recipeList, "Dinner");
                }
                else if (selectedOption == "All") {
                    
                }
            });
        }
    }

    // App Footer
    class Footer extends HBox {
        // Declare instance variable
        private Button createButton;

        // Constructor for Footer
        Footer() {
            this.setPrefSize(500, 60);
            this.setStyle("-fx-background-color: #F0F8FF;");
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
            if (this.serverError.checkServerAvailability()) {
                recorder.showRecordingWindow();
            }
        });
    }
}
