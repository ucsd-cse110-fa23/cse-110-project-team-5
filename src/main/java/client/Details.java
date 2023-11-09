package client;

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

class Details extends VBox {
    private TextArea title;
    private TextArea details;

    Details() {
        this.setPrefSize(500, 500);
        this.setStyle("-fx-background-color: #F0F8FF;");

        title = new TextArea();
        title.setPrefSize(475, 50); // set size of text field
        // set background color, size, and style of TextArea
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #DAE5EA; -fx-border-width: 0;");
        title.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        title.setWrapText(true); // Enable text wrapping
        this.getChildren().add(title); // add textlabel to recipe

        details = new TextArea(); 
        details.setPrefSize(475, 400); // set size of text field
        details.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        details.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        details.setWrapText(true); // Enable text wrapping
        details.prefWidthProperty().bind(this.widthProperty()); // TextArea matches window width
        details.prefHeightProperty().bind(this.heightProperty()); // TextArea matches window height
        this.getChildren().add(details); // add textlabel to recipe
    }

    public TextArea getDetails() {
        return this.details;
    }

    public TextArea getTitle() {
        return this.title;
    }

    public void setDetails(String detailString){
        details.setText(detailString);
    }
}