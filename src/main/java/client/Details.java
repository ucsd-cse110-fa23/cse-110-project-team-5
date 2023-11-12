package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

// The Details class represents a VBox containing TextArea components for title and details of a recipe
class Details extends VBox {
    private TextArea title;
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
        title.setPadding(new Insets(10, 0, 10, 0));
        title.setWrapText(true);
        this.getChildren().add(title);

        // Set up the TextArea for the details
        details = new TextArea();
        details.setPrefSize(475, 400);
        details.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;");
        details.setPadding(new Insets(10, 0, 10, 0));
        details.setWrapText(true);
        details.prefWidthProperty().bind(this.widthProperty());
        details.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().add(details);
    }

    // Method to extract the title from a given recipe string
    public String extractTitle(String recipeString) {
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(0, firstNewlineInd);
    }

    // Method to extract the details from a given recipe string
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
