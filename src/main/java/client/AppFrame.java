package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
    private Button createButton;
    private Button logoutButton;
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
        boolean isRemembered = false;
        String savedUser = "";
        try(BufferedReader reader = new BufferedReader(new FileReader("user_login.txt"))) {
            String line = reader.readLine();
            if(line != null) {
                isRemembered = true;
                savedUser = line;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        loginScreen = new LoginScreen(this);

        ScrollPane scrollPane = new ScrollPane(loginScreen);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Configure layout of the BorderPane
        //this.setTop(header);
        //this.setCenter(loginScreen);
        //this.setBottom(footer);
        //showLoginScreen();
        //showRecipeList();
        
        // Initialize and configure button
        this.createButton = footer.getCreateButton();
        addListeners(); // Set up event listeners for buttons

        // Check for Server Error
        this.serverError = new ServerError(this.createButton);
        if(!isRemembered) {
            if (this.serverError.checkServerAvailability()) {
                showLoginScreen();
            }
        }
        else {
            User.username = savedUser;
            showRecipeList();
        }
    }

    // App Header
    class Header extends HBox {
        private ComboBox<String> sort;
        private Sort sorter;

        // Constructor for Header
        Header() {
            this.setPrefSize(500, 60); // Set size of the header
            this.setStyle("-fx-background-color: #A4C3B2;");
            // Add "Sort By" Dropdown
            sort = new ComboBox<>();
            sort.setPromptText("Sort By");
            sort.getItems().addAll("Newest to Oldest", "Oldest to Newest", "A - Z", "Z - A");
            HBox.setMargin(sort, new Insets(0,10,0,10));
            // Add "Recipe List" Title
            Text titleText = new Text("Recipe List"); // Text of the Header
            titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
            // Create containers for elements
            HBox sortBox = new HBox(sort);
            HBox titleBox = new HBox(titleText);

            logoutButton = new Button("Logout");
            logoutButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;"); // Styling the logout button
            HBox.setMargin(logoutButton, new Insets(0,10,0,10));
            this.getChildren().add(logoutButton); // Add logout button to the header
            // Set alignments for elements
            sortBox.setAlignment(Pos.CENTER_LEFT);
            sort.setStyle("-fx-background-radius: 5;");
            titleBox.setAlignment(Pos.CENTER);
            // Add elements to the header
            this.getChildren().addAll(sortBox, titleBox);
            HBox.setHgrow(titleBox, Priority.ALWAYS);
            this.sorter = new Sort();
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
            this.setStyle("-fx-background-color: #A4C3B2;"); //#F0F8FF
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
                recipePresenter = new RecipePresenter(recipeList);
            }
        });

        header.getLogoutButton().setOnAction(e -> {
            this.recipeList.removeAll();
            this.setTop(null); // Remove the header
            this.setCenter(loginScreen); // Set the center to the login screen
            this.setBottom(null); // Remove the footer
            try (FileWriter writer = new FileWriter("user_login.txt")) {
                writer.write(""); 
            } catch (IOException ex) {
                ex.printStackTrace();
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