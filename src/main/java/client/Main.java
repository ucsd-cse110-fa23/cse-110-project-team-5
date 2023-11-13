package client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AppFrame root = new AppFrame();
        // Set the title of the app
        primaryStage.setTitle("Pantry Pal");
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        // Make window resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}