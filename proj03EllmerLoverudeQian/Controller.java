package proj03EllmerLoverudeQian;

import java.util.Arrays;


// import java.util.Optional;
// import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SingleSelectionModel;
//import javafx.fxml.FXMLLoader;
// import javafx.stage.Stage;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextInputDialog;
// import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

public class Controller {

   // private ArrayList<Tab> tabList;

    

    @FXML
    private TabPane tabPane;

    // an array that represents whether "untitled-x" current is an open tab
    private boolean[] untitledFileNameArray;


    public Controller()
    {

        this.untitledFileNameArray = new boolean[16];
        Arrays.fill(this.untitledFileNameArray, false);
        this.untitledFileNameArray[0] = true;


    }

    //this.selectionModel.select(0);


        //this.tabList = new ArrayList<>();

        //this.selectionModel = tabPane.getSelectionModel();

    
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
    
    /** 
     *
     * Handler method for about new bar item. When the new item of the
     * menu bar is clicked, an new tab is opened with text area.
     * Calls helper function "getNextDefaultTitle", which returns a String like 
     * "Untitled-1", or "Untitled-2", based on what is available.
     *
     * @see new tab and textarea
    */
   @FXML
   private void handleNewMenuItem() {
       
        Tab newTab = new Tab();

        newTab.setText(getNextDefaultTitle());

        tabPane.getTabs().add(newTab);

        TextArea textArea = new TextArea();

        newTab.setContent(textArea);

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

        selectionModel.select(newTab);

   }

   /** 
     *
     * Helper function that returns the next available Untitled-x, based
     * on the field untitledFileNameArray, which is an array of booleans.
     * 
     * 
     * @returns String that is the next unused "Untitled-..." 
    */
   private String getNextDefaultTitle()
   {

        if(untitledFileNameArray[0] == false) return "Untitled";

        for(int i = 1; i < untitledFileNameArray.length; i++)
        {

            if(untitledFileNameArray[i] == false)
            {
                untitledFileNameArray[i] = true;

                return "Untitled-" + Integer.toString(i);

            }
        }

        return null;
        
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
