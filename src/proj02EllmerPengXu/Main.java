/*
 * File: Main.java
 * Names: Ian Ellmer, Ricky Peng, and Andy Xu
 * Class: CS 361
 * Project 2
 * Date: February 14th
 */

package proj02EllmerPengXu;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

/**
 * Main class serves as the controller for the window.
 *
 * @author Ian Ellmer
 * @author Ricky Peng
 * @author Andy Xu
 */
public class Main extends Application {

    @FXML
    private Button helloButton;
    @FXML
    private TextArea textBox;

    /**
     * Handler method for hello button. When hello button is clicked,
     * a text input dialogue is created, and it will wait for user input.
     * The text of hello button will be changed to the user input if
     * user clicks ok button of the dialogue.
     *
     * @param event An ActionEvent object that gives information about the event
     *              and its source.
     */
    @FXML
    private void handleHelloButton(ActionEvent event) {

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Give me a number");
        inputDialog.setHeaderText("Give me an integer from 0 to 255:");
        // showAndWeight() returns an optional that contains the result of the dialogue
        Optional<String> result = inputDialog.showAndWait();

        // If the ok button is clicked
        if (result.isPresent()) {
            helloButton.setText(inputDialog.getEditor().getText());
        }
    }

    /**
     * Handler method for goodbye button. When goodbye button is clicked,
     * the word "Goodbye" will append to the text in the text box.
     *
     * @param event An ActionEvent object that gives information about the event
     *              and its source.
     */
    @FXML
    private void handleGoodbyeButton(ActionEvent event) {
        textBox.appendText(" Goodbye");
    }

    /**
     * Handler method for exit menu bar item. When exit item of the menu
     * bar is clicked, the window disappears and the application quits.
     *
     * @param event An ActionEvent object that gives information about the event
     *              and its source.
     */
    @FXML
    private void handleExitMenuItem(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Handler method for reset menu bar item. When reset item of the menu
     * bar is clicked, the window will be restored to its initial contents.
     *
     * @param event An ActionEvent object that gives information about the event
     *              and its source.
     */
    @FXML
    private void handleResetMenuItem(ActionEvent event) {
        helloButton.setText("Hello");
        textBox.setText("Sample text");
    }

    /**
     * Implements the start method of the Application class. This method will
     * be called after {@code launch} method, and it is responsible for initializing
     * the contents of the window.
     *
     * @param primaryStage A Stage object that is created by the {@code launch}
     *                     method
     *                     inherited from the Application class.
     */
    @Override
    public void start(Stage primaryStage) throws java.io.IOException {

        // Load fxml file
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Project 2");
        Scene scene = new Scene(root);

        // Load css file
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);

        // Set the minimum height and width of thmaine stage
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(300);

        // Show the stage
        primaryStage.show();

    }

    /**
     * Main method of the program that calls {@code launch} inherited from the
     * Application class
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
