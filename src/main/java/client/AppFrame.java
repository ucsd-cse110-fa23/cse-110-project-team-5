package client;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;

class AppFrame extends BorderPane {
    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private ShowDetails showDetails;
    private RecipeGenerate recipeGen;
    private Button createButton;

    private Scene scene;

    AppFrame() {
        // Initialise the header Object
        header = new Header();
        // Create a tasklist Object to hold the tasks
        recipeList = new RecipeList();
        recipeGen = new RecipeGenerate();
        // Initialise the Footer Object
        footer = new Footer();
        ScrollPane scrollPane = new ScrollPane(recipeList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scrollPane);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
        // Initialise Button Variables through the getters in Footer
        createButton = footer.getCreateButton();
        // Call Event Listeners for the Buttons
        addListeners();

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

    public void addListeners() {
        // Add button functionality
        createButton.setOnAction(e -> {

            Stage recordingStage = new Stage();
            BorderPane recordingPane = new BorderPane();

            Button recordButton = new Button("Record");
            recordButton.setOnAction(e1 -> {
                if (!recipeGen.toggleRecord()) {
                    showDetails = new ShowDetails(recipeList);
                    showDetails.setTitleAndDetails(recipeGen.getResponse());
                    recordingStage.close();
                    scene.setRoot(showDetails);
                    Stage recipeDetailStage = new Stage();
                    recipeDetailStage.setScene(scene);
                    recipeDetailStage.show();
                }
            });

            HBox buttonBox = new HBox(10);

            buttonBox.setAlignment(Pos.BOTTOM_CENTER);
            recordingPane.setCenter(buttonBox);
            buttonBox.getChildren().addAll(recordButton, recipeGen.recordingLabel);
            scene = new Scene(recordingPane, 400, 300);
            recordingStage.setScene(scene);
            recordingStage.setTitle("Recording Window");
            recordingStage.show();

        });
    }
}