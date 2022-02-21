package proj03EllmerLoverudeQian;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws java.io.IOException {

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

    public static void main(String[] args){
        launch(args);
    }
}
