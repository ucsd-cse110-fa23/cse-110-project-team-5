package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

// Main AppFrame for Pantry Pal App 
class AppFrame extends BorderPane {
    // Declare instance variables
    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private Button createButton;
    private Recorder recorder;
    private ServerError serverError;
    private LoginScreen loginScreen;

    // Constructor for AppFrame
    AppFrame() {
        // Initialize UI components
        header = new Header();
        recipeList = new RecipeList();
        footer = new Footer();

        loginScreen = new LoginScreen(this);
        recorder = new Recorder(recipeList);

        ScrollPane scrollPane = new ScrollPane(loginScreen);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Configure layout of the BorderPane
        //this.setTop(header);
        //this.setCenter(loginScreen);
        //this.setBottom(footer);
        showLoginScreen();
        //showRecipeList();
        
        // Initialize and configure button
        this.createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons

        // Check for Server Error
        this.serverError = new ServerError(this.createButton);
        this.serverError.checkServerAvailability();
    }

    // App Header
    class Header extends HBox {
        // Constructor for Header
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #F0F8FF;");

            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            this.getChildren().add(titleText);
            this.setAlignment(Pos.CENTER); // Align the text to the Center
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

    public void showRecipeList() {
        this.setTop(header);
        this.setCenter(recipeList);
        this.setBottom(footer);
    }

    public void showLoginScreen() {
        this.setCenter(loginScreen);
    }
}