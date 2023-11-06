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
        detailButton.setOnAction(e -> {
            Pane pane = new Pane();
            pane.setPrefSize(300, 300);

            Stage viewDetailStage = new Stage();
            Scene viewDetailScene = new Scene(pane);
            viewDetailStage.setScene(viewDetailScene);
            viewDetailStage.show();

        });

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