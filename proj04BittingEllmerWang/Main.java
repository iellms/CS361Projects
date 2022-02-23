/*
 * File: proj04BittingEllmerWant.Main.java
 * Names: Caleb Bitting, Ian Ellmer, Baron Wang
 * Class: CS361
 * Project 3
 * Date: 2/15/2022
 */

package proj04BittingEllmerWang;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main Class for loading the fxml file and building the stage
 *
 * @author (Caleb Bitting, Ian Ellmer, Baron Wang)
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load fxml file
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Project 3");
        Scene scene = new Scene(root);

        // Load css file
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);

        // Set the minimum height and width of th main stage
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(300);

        // Show the stage
        primaryStage.show();

    }
}
