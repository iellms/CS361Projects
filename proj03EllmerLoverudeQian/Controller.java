package proj03EllmerLoverudeQian;

// import java.util.Optional;
// import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
     *
     * Handler method for about menu bar item. When the about item of the
     * menu bar is clicked, an alert window appears displaying basic information
     * about the application.
     *
     * @see Informational window about the application
    */
    @FXML
    private void handleAboutMenuItem() {
 
       Alert aboutDialogBox = new Alert(AlertType.INFORMATION);
 
       aboutDialogBox.setTitle("About");
       aboutDialogBox.setHeaderText("About this Application");

       aboutDialogBox.setContentText(
           "Authors: Ian Ellmer, Jasper Loverude, and Leo Qian"
           + "\nLast Modified: Feb 18, 2022");
 
       aboutDialogBox.show();

    }
 
   @FXML
   private void handleNewMenuItem() {
       
   }
 
   @FXML
   private void handleOpenMenuItem() {
       
   }
 
   @FXML
   private void handleCloseMenuItem() {
 
 
   }
 
   @FXML
   private void handleSaveMenuItem() {
 
 
 
   }
 
   @FXML
   private void handleSaveAsMenuItem() {
 
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

    public static void main(String[] args){

    }
}
