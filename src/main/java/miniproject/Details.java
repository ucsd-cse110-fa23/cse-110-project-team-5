package miniproject;

import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

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
}