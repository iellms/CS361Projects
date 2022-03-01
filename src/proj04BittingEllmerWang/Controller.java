/*
 * File: proj04BittingEllmerWang.Controller.java
 * Names: Caleb Bitting, Ian Ellmer, Baron Wang
 * Class: CS361
 * Project 4
 * Date: 2/28/2022
 */

package proj04BittingEllmerWang;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;


/**
 * The Controller Class for handling menu items click events of the stage
 *
 * @author (Caleb Bitting, Ian Ellmer, Baron Wang)
 */
public class Controller {

    @FXML private TabPane tabPane;

    @FXML private MenuItem Close;
    @FXML private MenuItem Save;
    @FXML private MenuItem SaveAs;

    @FXML private Menu Edit;

    @FXML private CodeArea codeArea;

    // a number that stores the next untitled number for "untitled-x"
    private int untitledNumber;

    // a hasmmap that stores what file locations for tabnames
    private HashMap<String,String> fileLocation;

    public Controller() {
        this.untitledNumber = 1;
        this.fileLocation = new HashMap<String,String>();
        fileLocation.put("Untitled", null);
    }


    /**
     * Makes sure that the very first tab is properly highlighted. Local variable never
     * stored because instantiating a KeywordHighlighter creates a dedicated thread to
     * syntax highlighting.
     */
    @FXML
    public void initialize() {
        new KeywordHighlighter(codeArea);
    }


    /**
     * Helper method to disable/re-enable selected menu items Close, Save, SaveAs,
     * and the entire Edit menu
     *
     * @param clickable boolean: true is disable, false is re-enable
     */
    private void clickableMenuItems(boolean clickable) {
        Close.setDisable(!clickable);
        Save.setDisable(!clickable);
        SaveAs.setDisable(!clickable);
        Edit.setDisable(!clickable);
    }

    /**
     * Handler method for about menu bar item. When the about item of the
     * menu bar is clicked, an alert window appears displaying basic information
     * about the application.
     */
    @FXML
    private void handleAboutMenuItem(Event event) {
        Alert aboutDialogBox = new Alert(AlertType.INFORMATION);

        aboutDialogBox.setTitle("About");
        aboutDialogBox.setHeaderText("About this Application");

        aboutDialogBox.setContentText(
                "Authors: Caleb Bitting, Ian Ellmer, and Baron Wang\n" +
                "Last Modified: Feb 28, 2022");

        aboutDialogBox.show();
    }

    /**
     * Handler method for about new bar item. When the new item of the
     * menu bar is clicked, a new tab is opened with a code area.
     * Calls helper function "getNextDefaultTitle", which returns a String like
     * "Untitled-1", or "Untitled-2", based on what is available.
     *
     * @see new tab and codearea
     */
    @FXML
    private void handleNewMenuItem(Event event) {
        // create the area to write
        CodeArea codeArea = new CodeArea();
        codeArea.setWrapText(true); // enabling all new tabs to wrap text
        new KeywordHighlighter(codeArea); // colorizing codeArea

        // visualize the area
        Tab newTab = new Tab();
        newTab.setContent(new VirtualizedScrollPane<>(codeArea));
        newTab.setText("Untitled-" + untitledNumber);
        fileLocation.put("Untitled-" + untitledNumber++, null);
        newTab.setOnCloseRequest(this::handleCloseMenuItem);

        // add new tab and move selection to front
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);

        // re-enable the buttons if disabled
        clickableMenuItems(true);
    }

    /**
     * Handler for "open" menu item.
     * When the "open" button is clicked, a fileChooserDialog appears,
     * and the user has to select a valid text file to proceed
     * <p>
     * If a valid file is selected, the program reads the file's content as String
     * and that String is put as content of the codearea of the new tab created
     * <p>
     * The new tab will also be initiated with the path of the file opened
     */
    @FXML
    private void handleOpenMenuItem(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open your text file");

        // restrict the file type to only text files
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Java Files", "*.java")
        );
        File selectedFile = fileChooser.showOpenDialog(tabPane.getScene().getWindow());

        // if a valid file is selected
        if (selectedFile != null) {
            try {
                // get the path of the file selected
                String filePath = selectedFile.getPath();
                // check if the file has already been opened in tabPane.
                for (Tab tab : tabPane.getTabs()) {
                    String tabFileLocation = fileLocation.get(tab.getText());
                    if (tabFileLocation != null){
                        if (tabFileLocation.equals(filePath)) {
                            // if so, switch to existing tab.
                            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(tab); // select by tab
                            return;
                        }
                    }
                }

                // read the content of the file to a string
                String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
                // generate a new tab and put the file content into the code area
                handleNewMenuItem(event);
                // get the current codeBox
                Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
                CodeArea codeBox = getCurrentCodeArea();
                // set the content of the codeBox
                codeBox.appendText(fileContent);
                // adding keyword highlighting
                new KeywordHighlighter(codeBox);
                // set the title of the tab
                currentTab.setText(selectedFile.getName());
                fileLocation.put(selectedFile.getName(),selectedFile.getPath());
            } catch (IOException e){
                Alert failedToSaveAlert = new Alert(AlertType.ERROR);

                // set alert properties
                failedToSaveAlert.setTitle("Failed to open file");
                failedToSaveAlert.setHeaderText("IO Exception");
                failedToSaveAlert.setContentText("Error opening file.");

                failedToSaveAlert.show();
            }
        }

        // re-enable the buttons when there are tabs
        clickableMenuItems(true);
    }

    /**
     * Handler for "Close" menu item
     * When the "Close" button is clicked, or when the tab is closed, the program would
     * check if any changes has been made since the last save event, a dialog appears
     * asking if the user wants to save again
     * <p>
     * After the user makes selection the tab is closed
     * <p>
     * If no changes has been made, the tab also closes
     */
    @FXML
    private void handleCloseMenuItem(Event event) {
        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        // get content of code area
        CodeArea codeBox = getCurrentCodeArea();
        String currentContent = codeBox.getText();
        // get the file associated with the current tab
        String filePath = fileLocation.get(currentTab.getText());
        // check if changes has been made
        boolean changed = false;
        // check if the codeArea has been modified

        // check if the content of the file matches the content of the codeArea
        if (filePath != null) {
            File file = new File(filePath);
            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(file.getPath())));
                // if not it has been modified
                if (!currentContent.equals(fileContent)) {
                    changed = true;
                }
            } catch (IOException ex) {
                changed = true;
            }
        } else if (!currentContent.equals("")) {
            changed = true;
        }

        // if it has been modified
        if (changed) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Changes has been made to " + currentTab.getText() + 
                    "\ndo you want to save it?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    handleSaveMenuItem(event);
                    if(!event.isConsumed()){
                        tabPane.getTabs().remove(currentTab);
                    } 
                } else if (type == cancelButton) {
                    event.consume();
                } else {  // type == noButton
                    tabPane.getTabs().remove(currentTab);
                }
            });
        }
        // if no changes have been made, the tab also closes
        else {
            tabPane.getTabs().remove(currentTab);
        }

        // checks if there's any tab left; if not, disable menu items
        if (tabPane.getTabs().isEmpty()) {
            clickableMenuItems(false);
        }
    }

    /**
     * Handler for "save" menu item
     * When the "save" button is clicked, if file of the name of the tab exist in the
     * current directory, it will overwrite the file with the content in the code box of
     * the current tab.
     * <p>
     * If that file didn't exist, it will call the save as menu item for the user to put
     * in a new name
     */
    @FXML
    private void handleSaveMenuItem(Event event) {
        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

        // get the name of the tab (file path)
        String fileName = fileLocation.get(currentTab.getText());

        

        if (fileName != null) {
            File file = new File(fileName);
            // get content of codearea
            CodeArea codeBox = getCurrentCodeArea();
            String content = codeBox.getText();

            // save the content of the current tab
            saveFile(content, file);
        } else {
            handleSaveAsMenuItem(event);
        }
    }

    /**
     * Handler for "save as" menu item
     * When the "save as" button is clicked, a save as window appears asking the user to
     * enter a file name for the text file and if the file exist, the prompt will ask user
     * whether to overwrite.
     * <p>
     * After file is created successfully, the user will see a prompt, and if not, the
     * user will also see an error message; At the same time, the tab name will be changed
     * to the file path saved.
     * <p>
     * Modeled after the Keystore demonstrated at http://java-buddy.blogspot.com/
     */
    @FXML
    private void handleSaveAsMenuItem(Event event) {
        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        // get the current codeBox
        CodeArea codeBox = getCurrentCodeArea();

        // initiate a new file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");

        //Show save file dialog
        File file = fileChooser.showSaveDialog(tabPane.getScene().getWindow());

        if (file != null) {
            if (saveFile(codeBox.getText(), file)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Successfully created " + file.getPath());
                alert.show();
                // change the name of the tab to the file path
                currentTab.setText(file.getName());
                fileLocation.put(file.getName(), file.getPath());
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed creating " + file.getPath());
                alert.show();
            }
        }
        else{
            event.consume(); //Stops anything from happening
        }
    }

    /**
     * Helper method for creating a new file
     *
     * @param (content) (the string content of the new file being created)
     * @param (file)    (the file variable passed by handleSaveAsMenuItem function
     *                  indicating the file the user want to save to is valid)
     * @return returns true if file created successfully and false if error occurs
     */
    private boolean saveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            Alert failedToSaveAlert = new Alert(AlertType.ERROR);

            failedToSaveAlert.setTitle("Failed to save file");
            failedToSaveAlert.setHeaderText("IO Exception");
            failedToSaveAlert.setContentText("Error saving file.");

            failedToSaveAlert.show();
            return false;
        }

    }

    /**
     * Handler method for exit menu bar item. When exit item of the menu
     * bar is clicked, the window disappears and the application quits after going
     * through each tab and ask user about the unsaved change.
     * <p>
     * If the user clicked cancel at any point, the operation is stopped
     *
     * @param event An ActionEvent object that gives information about the event
     *              and its source.
     */
    @FXML
    void handleExitMenuItem(Event event) {
        while (!tabPane.getTabs().isEmpty()) {
            Tab previousTab = tabPane.getSelectionModel().getSelectedItem();
            handleCloseMenuItem(event);
            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            // if the tab is not closed, stop the operation of this function
            if (previousTab.equals(currentTab)) {
                return;
            }
        }
        System.exit(0);
    }

    /**
     * Retrieves the current CodeArea. Created this method to reduce long lines.
     * @return the current CodeArea
     */
    private CodeArea getCurrentCodeArea() {
        Node currentNode = tabPane.getSelectionModel().getSelectedItem().getContent();
        VirtualizedScrollPane<?> currentPane = (VirtualizedScrollPane<?>) (currentNode);

        return (CodeArea) currentPane.getContent();
    }

    /**
     * Handler method for "Undo" in the Edit menu
     * Undo the previous codeArea edition
     */
    @FXML
    private void handleUndo(Event event) {
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.undo();
    }

    /**
     * Handler method for "Redo" in the Edit menu
     * Redo the previous codeArea edition
     */
    @FXML
    private void handleRedo(Event event) {
        // get the current tab selected
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.redo();
    }

    /**
     * Handler method for "Cut" in the Edit menu
     * Cut all the selected text in the codeArea of the current Tab
     */
    @FXML
    private void handleCut(Event event) {
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.cut();
    }

    /**
     * Handler method for "Copy" in the Edit menu
     * Copy the selected text from the codeArea of the current Tab to the clipboard
     */
    @FXML
    private void handleCopy(Event event) {
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.copy();
    }

    /**
     * Handler method for "Paste" in the Edit menu
     * Paste text from the clipboard to the codeArea of the current Tab
     */
    @FXML
    private void handlePaste(Event event) {
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.paste();
    }

    /**
     * Handler method for "Select all" in the Edit menu
     * Select all the text in the codeArea of the current Tab
     */
    @FXML
    private void handleSelectAll(Event event) {
        CodeArea codeBox = getCurrentCodeArea();
        codeBox.selectAll();
    }
}
