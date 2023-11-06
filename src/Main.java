import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import java.awt.BorderLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

// Recipes
class Recipe extends HBox {
    private Label index;
    private TextField recipeName;
    private Button detailButton;

    Recipe() {
        this.setPrefSize(500, 20); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the recipe
        this.getChildren().add(index); // add index label to recipe

        recipeName = new TextField(); // create recipe name text field
        recipeName.setPrefSize(380, 20); // set size of text field
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(recipeName); // add textlabel to recipe

        detailButton = new Button("View Details"); // creates a button for viewing recipe details
        detailButton.setPrefSize(100, 20);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        this.getChildren().add(detailButton);
    }

    public void setRecipeIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.recipeName.setPromptText("Recipe " + num);
    }

    public TextField getRecipeName() {
        return this.recipeName;
    }

    public Button getDetailButton() {
        return this.detailButton;
    }
}

// Recipe List to hold Recipes
class RecipeList extends VBox {
    RecipeList() {
        this.setSpacing(5); // sets spacing between recipes
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void updateTaskIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                ((Recipe) this.getChildren().get(i)).setRecipeIndex(index);
                index++;
            }
        }
    }
}

// App Footer
class Footer extends HBox {
    private Button createButton;

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

    public Button getCreateButton() {
        return createButton;
    }
}

// App Header
class Header extends HBox {
    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Recipe List"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

// class AppFrame extends BorderPane {
//     private Header header;
//     private Footer footer;
//     private RecipeList recipeList;

//     private Button createButton;

//     AppFrame() {
//         // Initialise the header Object
//         header = new Header();
//         // Create a tasklist Object to hold the tasks
//         recipeList = new RecipeList();
//         // Initialise the Footer Object
//         footer = new Footer();
//         ScrollPane scrollPane = new ScrollPane(recipeList);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setFitToHeight(true);
//         scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
//         // Add header to the top of the BorderPane
//         this.setTop(header);
//         // Add scroller to the centre of the BorderPane
//         this.setCenter(scrollPane);
//         // Add footer to the bottom of the BorderPane
//         this.setBottom(footer);
//         // Initialise Button Variables through the getters in Footer
//         createButton = footer.getCreateButton();
//         // Call Event Listeners for the Buttons
//         addListeners();
//     }

//     public void addListeners() {
//         // Add button functionality
//         createButton.setOnAction(e -> {
//             // Create a new task
//             Recipe recipe = new Recipe();
//             // Add task to tasklist
//             recipeList.getChildren().add(recipe);
//             // Update task indices
//             recipeList.updateTaskIndices();
//         });
//     }
// }

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
        AppFrame root = new AppFrame();
        // Set the title of the app
        primaryStage.setTitle("Pantry Pal");
        // BorderPane root = new BorderPane();
        // ListView<String> recipeList = new ListView<>();
        // root.getChildren().addAll(recipeList);
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        // Make window resizable
        primaryStage.setResizable(true);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// public class Main extends Application {

//     @Override
//     public void start(Stage primaryStage) throws Exception {

//         // Setting the Layout of the Window- Should contain a Header, Footer and the TaskList
//         AppFrame root = new AppFrame();

//         // Create scene of mentioned size with the border pane
//         primaryStage.setScene(new Scene(root, 500, 600));

//     }
// }