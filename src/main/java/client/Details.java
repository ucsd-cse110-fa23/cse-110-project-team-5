package client;

import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

// The Details class represents a VBox containing TextArea components for title and details of a recipe
class Details extends VBox {
    // Declare instance variables
    private TextArea title;
    private TextField mealType;
    private TextArea details;
    private ImageView imageView;

    // Constructor for Details class
    Details() {
        // Set up the VBox properties
        this.setPrefSize(500, 500);
        this.setStyle("-fx-background-color: #EAF4F4;");

        // Set up the TextArea for the title
        title = new TextArea();
        title.setPrefSize(475, 50); // Set Size
        title.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #CCE3DE; -fx-border-width: 0;"); // Set Style 
        title.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the title text field
        title.setWrapText(true); // Enable text wrapping
        title.setEditable(false); // Make text non=editable

        mealType = new TextField();
        mealType.setPrefSize(65, 20); // Set size of the title text field
        mealType.setAlignment(Pos.CENTER); // Set alignment of index label
        mealType.setStyle("-fx-background-color: #23AEFB; -fx-padding: 5px; -fx-background-radius: 10; -fx-font-weight: bold;");
        mealType.setPadding(new Insets(10, 0, 10, 10)); // Add padding to the title text field
        mealType.setEditable(false); // Make the text field non-editable
        mealType.setAlignment(Pos.CENTER);

        HBox mealBox = new HBox(mealType);
        mealBox.setStyle("-fx-background-color: #A4C3B2; -fx-margin-top: 0;");
        this.getChildren().add(mealBox);
        this.getChildren().add(title); 

        // Set up the TextArea for the details
        details = new TextArea();
        details.setPrefSize(475, 400); // Set size of the details text field
        details.setStyle("-fx-background-color: #CCE3DE; -fx-border-width: 0;"); // Set background color of details text
                                                                                 // field
        details.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the details text field
        details.setWrapText(true); // Enable text wrapping
        details.setEditable(false);
        details.prefWidthProperty().bind(this.widthProperty()); // Make details TextArea match the width of the VBox
        details.prefHeightProperty().bind(this.heightProperty()); // Make details TextArea match the height of the VBox
        this.getChildren().add(details); // Add details TextArea to the VBox

        // Add Image of the dish
        imageView = new ImageView();
        imageView.setFitWidth(200); // Set image Width
        imageView.setFitHeight(200); // Set image Height
        HBox imageBox = new HBox(imageView); 
        imageBox.setAlignment(Pos.CENTER);

        this.getChildren().add(imageBox);
    }

    // Method to extract the title from a given recipe string
    public String extractTitle(String recipeString) {
        System.out.println(recipeString);
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(0, firstNewlineInd);
    }

    // Method to extract the details from a given recipe string
    public String extractDetails(String recipeString) {
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(firstNewlineInd + 1, recipeString.length());
    }

    public void makeTextEditable() {
        title.setEditable(true);
        details.setEditable(true);
    }

    public void setTitle(String titleString) {
        title.setText(titleString);
    }

    public void setDetails(String detailsString) {
        details.setText(detailsString);
    }

    public void setMealType(String mealTypeString) {
        mealType.setText(mealTypeString);
    }

    public String getTitle() {
        return this.title.getText();
    }

    public String getDetails() {
        return this.details.getText();
    }

    public String getMealType() {
        return this.mealType.getText();
    }

    public void uploadImage(String link) {
        Image image = new Image(link);
        imageView.setImage(image);
    }
}
