package org.BookRecommender;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Setup JFX versions
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        // Setup FXML file
        var root = new FXMLLoader(App.class.getResource("/org/BookRecommender/View/registrationPage.fxml"));
        var scene = new Scene(root.load(), 640, 480);
        stage.setResizable(false);  // Impedisci resize finestra
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BookRecommender");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}