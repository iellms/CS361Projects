package proj03EllmerLoverudeQian;



import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
import javafx.stage.FileChooser;
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


    /**
     * Handler for "open" menu item
     * When the "open" button is clicked, a fileChooserDialog appears,
     * and the user has to select a valid text file to proceed
     *
     * If a valid file is selected, the program reads the file's content as String
     * and that String is put as content of the textarea of the new tab created
     *
     * The new tab will also be initiated with the path of the file opened
     */
   @FXML
   private void handleOpenMenuItem() throws IOException {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Open your text file");

       // restrict the file type to only text files
       fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("Text Files", "*.txt")
       );
       File selectedFile = fileChooser.showOpenDialog(tabPane.getScene().getWindow());

       // if a valid file is selected
       if (selectedFile != null) {
           // get the path of the file selected
           String filePath = selectedFile.getPath();
           // read the content of the file to a string
           String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
           // generate a new tab and put the file content into the text area
           Tab newTab = new Tab();
           newTab.setText(selectedFile.getPath());
           tabPane.getTabs().add(newTab);
           TextArea textArea = new TextArea(fileContent);
           newTab.setContent(textArea);
           // select the new tab created
           SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
           selectionModel.select(newTab);
       }

   }
 
   @FXML
   private void handleCloseMenuItem() {
 
 
   }
    /**
     * Handler for "save" menu item
     * When the "save" button is clicked, if file of the name of the tab exist in the current directory, it will
     * overwrite the file with the content in the textbox of the current tab
     *
     * If that file didn't exist, it will call the save as menu item for the user to put in a new name
     *
     */
   @FXML
   private void handleSaveMenuItem() {
       // get the current tab
       Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

       // get the name of the tab (file path)
       String fileName = currentTab.getText();

       File file = new File(fileName);

       if (file.exists()) {
           // get content of textarea
           TextArea textBox = (TextArea) currentTab.getContent();
           String content = textBox.getText();

           // save the content of the current tab
           SaveFile(content,file);
       }
       else {
           handleSaveAsMenuItem();
       }
 
 
   }
    /**
     * Handler for "save as" menu item
     * When the "save as" button is clicked, a save as window appears asking the user to enter
     * a file name for the text file and if the file exist, the prompt will ask user whether to overwrite
     *
     * After file is created successfully, the user will see a prompt, and if not, the user will also see an error
     * message; At the same time, the tab name will be changed to the file path saved
     *
     * @Give credit to http://java-buddy.blogspot.com/
     */
   @FXML
   private void handleSaveAsMenuItem() {
       // get the current tab
       Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
       // get the current textBox
       TextArea textBox = (TextArea) currentTab.getContent();

       // initiate a new file chooser
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save as");

       //Set extension filter
       FileChooser.ExtensionFilter extFilter =
               new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
       fileChooser.getExtensionFilters().add(extFilter);

       //Show save file dialog
       File file = fileChooser.showSaveDialog(tabPane.getScene().getWindow());

       if(file != null) {
           Alert alert;
           if (SaveFile(textBox.getText(), file)) {
               alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Success");
               alert.setHeaderText(null);
               alert.setContentText("Successfully created " + file.getPath());
               alert.show();
               // change the name of the tab to the file path
               currentTab.setText(file.getPath());
           } else {
               alert = new Alert(AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText(null);
               alert.setContentText("Filed creating " + file.getPath());
               alert.show();
           }
       }
 
   }

    /**
     * Helper method for creating a new file
     * @param (content) (the string content of the new file being created)
     * @param (file) (the file variable passed by handleSaveAsMenuItem function indicating the
     *               file the user want to save to is valid)
     *
     * @return returns true if file created successfully and false if error occurs
     */
    private boolean SaveFile(String content, File file){
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            return false;
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

    /**
     * Handler method for "Undo" in the Edit menu
     * Undo the previous textArea edition
     */
    @FXML
    private void handleUndo(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the undo method
        textBox.undo();
    }

    /**
     * Handler method for "Redo" in the Edit menu
     * Redo the previous textArea edition
     */
    @FXML
    private void handleRedo(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the redo method
        textBox.redo();
    }

    /**
     * Handler method for "Cut" in the Edit menu
     * Cut all the selected text in the textArea of the current Tab
     */
    @FXML
    private void handleCut(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the cut method
        textBox.cut();
    }

    /**
     * Handler method for "Copy" in the Edit menu
     * Copy the selected text from the textArea of the current Tab to the clipboard
     */
    @FXML
    private void handleCopy(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the copy method
        textBox.copy();
    }

    /**
     * Handler method for "Paste" in the Edit menu
     * Paste text from the clipboard to the textArea of the current Tab
     */
    @FXML
    private void handlePaste(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the paste method
        textBox.paste();
    }

    /**
     * Handler method for "Select all" in the Edit menu
     * Select all the text in the textArea of the current Tab
     */
    @FXML
    private void handleSelectAll(){
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the select all method
        textBox.selectAll();
    }


    public static void main(String[] args){

    }
}
