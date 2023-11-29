package client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class ServerError {
    private Model model;
    private Text message;
    private Button createButton;

    ServerError(Button createButton) {
        this.createButton = createButton;
        this.model = new Model();
        this.message = new Text("The Server Is Temporary Unavailable");
        this.message.setStyle("-fx-font-style: italic; -fx-fill: #DC143C; -fx-font-weight: bold; -fx-font-size: 15; -fx-font-family: comic sans ms;");
    }

    private void showErrorWindow() {
        Stage serverErrorStage = new Stage();
        BorderPane serverErrorPane = new BorderPane();
        Scene serverErrorScene = new Scene(serverErrorPane, 500, 200);
        serverErrorPane.setCenter(message);
        serverErrorStage.setTitle("SERVER ERROR");
        serverErrorStage.setScene(serverErrorScene);
        serverErrorStage.setAlwaysOnTop(true);
        serverErrorStage.show();
    }

    public boolean isAvailable() {
        if ((this.model.performRequest("PUT", "gpt", "hello").equals("Error"))) {
            this.showErrorWindow();
            this.createButton.setDisable(true);
            return false;
        }
        else{
            return true;
        }
    }
}