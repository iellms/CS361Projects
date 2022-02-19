package proj03EllmerLoverudeQian;


import java.util.Arrays;
import java.util.Optional;
import java.io.File;
import java.io.IOException;



// import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
    /**
     * Handler for save as menu item
     * When the save as button is clicked, a textInputDialog appears
     * and asks the file name for the text file
     *
     * If ok is clicked, it calls the createNewFile helper method and create a
     * new text file if none exist in the current director
     */
   @FXML
   private void handleSaveAsMenuItem() {
       TextInputDialog saveAsDialog = new TextInputDialog();
       saveAsDialog.setTitle("Save As");
       saveAsDialog.setHeaderText("Name your new text file");
       Optional<String> result = saveAsDialog.showAndWait();
       if (result.isPresent()){
            String fileName = saveAsDialog.getEditor().getText();
            createNewFile(fileName);
       }
 
   }

    /**
     * Helper method for creating a new file
     * It creates a newFile if none existed and throws error message if an error is encountered
     * @param (fileName) (the name of the text file that is going to be created)
     * <p>Bugs: Need to ask the user if the file name already exist; doesn't show exception dialog if
     *                   file name is invalid
     */
   private void createNewFile(String fileName){
       File file = new File(fileName+".txt");

       try {
           // create a new file
           boolean result = file.createNewFile();

           // test if successfully created a new file
           Alert alert;
           if(result){
               alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Success");
               alert.setHeaderText(null);
               alert.setContentText("Successfully created "+fileName+".txt");

           }
           else{
               alert = new Alert(AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText(null);
               alert.setContentText("Filed creating "+fileName+".txt");

           }
           alert.showAndWait();
       } catch (IOException e) {
           Alert alert = new Alert(AlertType.ERROR);
           alert.setTitle("Exception");
           alert.setHeaderText(null);
           alert.setContentText("Exception creating "+fileName+".txt"
           + "\n"+e.getMessage());
       }
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
