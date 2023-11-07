package miniproject;

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
    private TextField details;

    Details() {
        this.setPrefSize(500, 500);
        this.setStyle("-fx-background-color: #F0F8FF;");

        details = new TextField(); // create recipe name text field
        details.setPrefSize(380, 20); // set size of text field
        details.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        details.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(details); // add textlabel to recipe
    }

    public TextField getDetails() {
        return this.details;
    }
}