package edu.farmingdale.fullstackproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FullStackProject.fxml"));

        // Load the scene from the FXML file
        Scene scene = new Scene(fxmlLoader.load(), 802, 571); // Adjusted size

        // Set the title of the stage
        stage.setTitle("CSC325 FullStackProject-Anthony Selvaggio");

        // Optionally set an application icon (if you have one)
        // stage.getIcons().add(new Image("path/to/your/icon.png")); // Uncomment and adjust path if needed

        // Set the scene to the stage
        stage.setScene(scene);

        // Load the CSS stylesheet
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Show the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
