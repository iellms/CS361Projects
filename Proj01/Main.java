/*
 * File: Main.java
 * Names: Ian Ellmer, Hanna Gao, Nick English, Nico Hillison
 * Class: CS361
 * Project 1
 * Date: 2/9/2022
 */

//package proj1EllmerEnglishGaoHilison;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

/**
 * Main is the primary class used to handle all parts of this project.
 *
 */
public class Main extends Application
{
    /**
     * Constructs the Application window and handles its functionality.
     * Overrides Application start method.
     *
     * @author Ian Ellmer, Nick English, Hanna Gao, Nico Hillison
     * @param primaryStage the stage on which objects will be placed
     */
    @Override
    public void start(Stage primaryStage) {
        // Build the main menu bar
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem reset = new MenuItem("Reset");
        MenuItem exit = new MenuItem("Exit");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        menubar.getMenus().addAll(fileMenu);
        fileMenu.getItems().addAll(reset, separator, exit);

        // Build the tool bar
        ToolBar toolbar = new ToolBar();
        VBox toolbarVB = new VBox(toolbar);
        Button hello = new Button("Hello");
        Button goodbye = new Button("Goodbye");
        hello.setStyle("-fx-background-color: LightGreen; -fx-border-color: Black");
        goodbye.setStyle("-fx-background-color: LightPink; -fx-border-color: Black");

        toolbar.getItems().addAll(hello, goodbye);

        // Build the text area
        TextArea textBox = new TextArea("Sample Text");
        VBox textVB = new VBox(textBox);

        // Create the popup input dialogue
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Give me a number");
        inputDialog.setHeaderText("Give me an integer from 0 to 255:");

        // Handle button responses to actions
        hello.setOnAction(e -> {inputDialog.showAndWait(); hello.setText(inputDialog.getEditor().getText());});
        reset.setOnAction(e -> {hello.setText("Hello"); textBox.setText("Sample Text");});
        goodbye.setOnAction(e -> textBox.setText(textBox.getText() + " Goodbye"));
        exit.setOnAction(e -> Platform.exit());

        // Construct the main pane and add elements
        AnchorPane root = new AnchorPane();
        root.getChildren().add(menubar);
        root.getChildren().add(toolbarVB);
        root.getChildren().add(textVB);

        // Position elements in the pane
        AnchorPane.setTopAnchor(toolbarVB, 25.0);
        AnchorPane.setRightAnchor(toolbarVB, 0.0);
        AnchorPane.setLeftAnchor(toolbarVB, 0.0);

        AnchorPane.setRightAnchor(menubar, 0.0);
        AnchorPane.setLeftAnchor(menubar, 0.0);

        AnchorPane.setTopAnchor(textVB, 65.0);
        AnchorPane.setBottomAnchor(textVB, 0.0);

        // Construct scene and add elements to the stage
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Project 1 Ellmer English Gao Hillison");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args args to the main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}