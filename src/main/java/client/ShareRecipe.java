package client;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ShareRecipe {

    ShareRecipe() {
        this.showRecordingWindow();
    }

    private void showRecordingWindow() {
        Stage shareStage = new Stage();
        BorderPane sharePane = new BorderPane();
        Scene shareScene = new Scene(sharePane, 400, 200);
        shareStage.setScene(shareScene);
        shareStage.show();
    }
}
