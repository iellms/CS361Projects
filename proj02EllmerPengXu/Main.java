/*
* File: Main.java
* Names: Ian Ellmer, Ricky Peng, and Andy Xu
* Class: CS 361
* Project 2
* Date: February 14th
*/


package proj02EllmerPengXu;

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



public class Main extends Application
{

    @FXML
    private Button helloButton;
    @FXML
    private Button goodbyeButton;
    @FXML
    private TextArea textBox;

    @FXML
    private void handleHelloButton(ActionEvent event) {
        // the handler method for the hello button
        // this is a test. creates a new stage only
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Give me a number");
        inputDialog.setHeaderText("Give me an integer from 0 to 255:");
        inputDialog.showAndWait(); 
        helloButton.setText(inputDialog.getEditor().getText());
    }

    @FXML
    private void handleExitButton(ActionEvent event){
        Platform.exit();
    }

    @FXML
    private void handleGoodbyeButton(ActionEvent event){
        textBox.setText(textBox.getText() + " Goodbye");
    }

    @FXML
    private void handleResetButton(ActionEvent event){
        helloButton.setText("Hello"); 
        textBox.setText("Sample Text");
    }
    

    @Override
    public void start(Stage primaryStage) throws java.io.IOException {

        // load fxml file
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Project 2");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    

    public static void main(String[] args) {

        launch(args);

    }
}
