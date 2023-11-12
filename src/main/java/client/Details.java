package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// VBox within RecipeDetails that stores the details of a recipe
class Details extends VBox {
    // Declare instance variables
    private TextArea title;
    private TextArea details;

    // Constructor for Details class
    Details() {
        this.setPrefSize(500, 500); // Set preferred size of the VBox
        this.setStyle("-fx-background-color: #F0F8FF;"); // Set background color of the VBox

        // Create and configure title TextArea
        title = new TextArea();
        title.setPrefSize(475, 50); // Set size of the title text field
        title.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #DAE5EA; -fx-border-width: 0;");
        title.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the title text field
        title.setWrapText(true); // Enable text wrapping
        this.getChildren().add(title); // Add title TextArea to the VBox

        // Create and configure details TextArea
        details = new TextArea();
        details.setPrefSize(475, 400); // Set size of the details text field
        details.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // Set background color of details text
                                                                                 // field
        details.setPadding(new Insets(10, 0, 10, 0)); // Add padding to the details text field
        details.setWrapText(true); // Enable text wrapping
        details.prefWidthProperty().bind(this.widthProperty()); // Make details TextArea match the width of the VBox
        details.prefHeightProperty().bind(this.heightProperty()); // Make details TextArea match the height of the VBox
        this.getChildren().add(details); // Add details TextArea to the VBox
    }

    // Method to extract title from a recipe string
    public String extractTitle(String recipeString) {
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(0, firstNewlineInd);
    }

    // Method to extract details from a recipe string
    public String extractDetails(String recipeString) {
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(firstNewlineInd + 1, recipeString.length());
    }

    public void setTitle(String newTitle) {
        title.setText(newTitle);
    }

    public void setDetails(String newDetails) {
        details.setText(newDetails);
    }

    public String getDetails() {
        return this.details.getText();
    }

    public String getTitle() {
        return this.title.getText();
    }
}
