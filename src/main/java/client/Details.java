package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// The Details class represents a VBox containing TextArea components for title and details of a recipe
class Details extends VBox {
    // Declare instance variables
    private TextArea title;
    private TextArea mealType;
    private TextArea details;

    // Constructor for Details class
    Details() {
        // Set up the VBox properties
        this.setPrefSize(500, 500);
        this.setStyle("-fx-background-color: #F0F8FF;");

        // Set up the TextArea for the title
        title = new TextArea();
        title.setPrefSize(475, 50);
        title.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #DAE5EA; -fx-border-width: 0;");
        title.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the title text field
        title.setWrapText(true); // Enable text wrapping
        title.setEditable(false);
        this.getChildren().add(title); // Add title TextArea to the VBox

        // Create and configure title TextArea
        mealType = new TextArea();
        mealType.setPrefSize(475, 20); // Set size of the title text field
        mealType.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #DAE5EA; -fx-border-width: 0;");
        mealType.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the title text field
        mealType.setWrapText(true); // Enable text wrapping
        mealType.setEditable(false);
        this.getChildren().add(mealType); // Add title TextArea to the VBox

        // Set up the TextArea for the details
        details = new TextArea();
        details.setPrefSize(475, 400); // Set size of the details text field
        details.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // Set background color of details text
                                                                                 // field
        details.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the details text field
        details.setWrapText(true); // Enable text wrapping
        details.setEditable(false);
        details.prefWidthProperty().bind(this.widthProperty()); // Make details TextArea match the width of the VBox
        details.prefHeightProperty().bind(this.heightProperty()); // Make details TextArea match the height of the VBox
        this.getChildren().add(details); // Add details TextArea to the VBox
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
}
