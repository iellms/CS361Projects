/*
 * File: proj04BittingEllmerWang.Main.java
 * Names: Caleb Bitting, Ian Ellmer, Baron Wang
 * Class: CS361
 * Project 3
 * Date: 2/15/2022
 */

package proj04BittingEllmerWang;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


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



        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Controller controller = fxmlLoader.getController();

        primaryStage.setTitle("Baron, Caleb, and Ian's Project 4");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // exit method triggered when user tries to click on the red dot on top corner
                controller.handleExitMenuItem(event);
            }
        });
    }
}
