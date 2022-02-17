package proj03EllmerLoverudeQian;

// import java.util.Optional;
// import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.stage.Stage;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextInputDialog;
// import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

public class Controller {
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

    public static void main(String[] args){

    }
}
