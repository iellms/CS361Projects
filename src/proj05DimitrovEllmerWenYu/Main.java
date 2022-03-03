/*
 * File: proj05DimitrovEllmerWenYu.Main.java
 * Names: Anton Dimitrov, Ian Ellmer, Muqing Wen, Alex Yu
 * Class: CS361
 * Project 5
 * Date: 3/7/21
 */

package proj05DimitrovEllmerWenYu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The Main Class for loading the fxml file and building the stage
 *
 * @author (Anton Dimitrov, Ian Ellmer, Muqing Wen, Alex Yu)
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Controller controller = fxmlLoader.getController();

        primaryStage.setTitle("Alex, Anton, Ian, and Muqing's Project 5");
        primaryStage.setScene(scene);
        primaryStage.show();
        // exit method triggered when user tries to click on the red dot on top corner
        primaryStage.setOnCloseRequest(controller::handleExitMenuItem);
    }
}
