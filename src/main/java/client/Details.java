package client;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

class Details extends VBox {
    private TextArea title;
    private TextArea details;

    Details() {
        this.setPrefSize(500, 500);
        this.setStyle("-fx-background-color: #F0F8FF;");

        title = new TextArea();
        title.setPrefSize(475, 50); // set size of text field
        // set background color, size, and style of TextArea
        title.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #DAE5EA; -fx-border-width: 0;");
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

    public String extractTitle(String recipeString) {
        System.out.println(recipeString);
        int firstNewlineInd = recipeString.indexOf("\n");
        return recipeString.substring(0, firstNewlineInd);
    }

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