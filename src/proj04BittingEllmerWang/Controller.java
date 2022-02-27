/*
 * File: proj04BittingEllmerWang.Controller.java
 * Names: Caleb Bitting, Ian Ellmer, Baron Wang
 * Class: CS361
 * Project 4
 * Date: 2/15/2022
 */

package proj04BittingEllmerWang;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyledDocument;

/**
 * The Controller Class for handling menu items click events of the stage
 *
 * @author (Caleb Bitting, Ian Ellmer, Baron Wang)
 */
public class Controller {

    @FXML
    private TabPane tabPane;

    @FXML
    private MenuItem Close;

    @FXML
    private MenuItem Save;

    @FXML
    private MenuItem SaveAs;

    @FXML
    private Menu Edit;

    // a number that stores the next untitled number for "untitled-x"
    private int untitledNumber;


    public Controller() {
        this.untitledNumber = 1;
    }

    public static void main(String[] args) {

    }

    /**
     * Helper method to disable/re-enable selected menu items Close, Save, SaveAs,
     * and the entire Edit menu
     * @param disable boolean: true is disable, false is re-enable
     */
    private void disableMenuItems(boolean disable){
        Close.setDisable(disable);
        Save.setDisable(disable);
        SaveAs.setDisable(disable);
        Edit.setDisable(disable);
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
                "Authors: Caleb Bitting, Ian Ellmer, and Baron Wang"
                        + "\nLast Modified: Feb 28, 2022");

        aboutDialogBox.show();
    }

    /**
     * Handler method for about new bar item. When the new item of the
     * menu bar is clicked, a new tab is opened with text area.
     * Calls helper function "getNextDefaultTitle", which returns a String like
     * "Untitled-1", or "Untitled-2", based on what is available.
     *
     * @see new tab and textarea
     *
     * <bug>for default tab, the close request handler may not work</bug>

     */
    @FXML
    private void handleNewMenuItem(Event event) {



        Tab newTab = new Tab();


        // trigger close menu item handler when tab is closed
        newTab.setOnCloseRequest(this::handleCloseMenuItem);

        newTab.setText("Untitled-" + untitledNumber);
        newTab.setId("Untitled-" + untitledNumber++);
        newTab.setContent(new VirtualizedScrollPane(new CodeArea()));

        // add new tab and move selection to front
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);

        // re-enable the buttons when there are tabs
        disableMenuItems(false);

    }

    /**
     * Handler for "open" menu item.
     * When the "open" button is clicked, a fileChooserDialog appears,
     * and the user has to select a valid text file to proceed
     * <p>
     * If a valid file is selected, the program reads the file's content as String
     * and that String is put as content of the textarea of the new tab created
     * <p>
     * The new tab will also be initiated with the path of the file opened
     *
     * @throws IOException thrown when encountering issues with reading the files
     */
    @FXML
    private void handleOpenMenuItem(Event event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open your text file");

        // restrict the file type to only text files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt") // TODO: extend to other text files (.java, etc.)
        );
        File selectedFile = fileChooser.showOpenDialog(tabPane.getScene().getWindow());


        // if a valid file is selected
        if (selectedFile != null) {
            // get the path of the file selected
            String filePath = selectedFile.getPath();

            // check if the file has already been opened in tabPane.
            for (Tab tab : tabPane.getTabs()) {
                if (tab.getId().equals(filePath)) {
                    // if so, switch to existing tab.
                    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                    selectionModel.select(tab); // select by tab
                    return;
                }
            }

            // read the content of the file to a string
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            // generate a new tab and put the file content into the text area
            handleNewMenuItem(event); // TODO: probably rename the method as this isn't handling this event
            // get the current textBox
            Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
            CodeArea codeBox = (CodeArea) ((VirtualizedScrollPane) currentTab.getContent()).getContent();
            // set the content of the codeBox
            codeBox.appendText(fileContent);
            // set the title of the tab
            String[] fileAncestors = filePath.split("/");
            currentTab.setText(fileAncestors[fileAncestors.length - 1]);
            currentTab.setId(filePath);
        }

        // re-enable the buttons when there are tabs
        disableMenuItems(false);
    }

    /**
     * Handler for "Close" menu item
     * When the "Close" button is clicked, or when the tab is closed, the program would check
     * if any changes has been made since the last save event, a dialog appears asking if the user
     * wants to save again
     * <p>
     * After the user makes selection the tab is closed
     * <p>
     * If no changes has been made, the tab also closes
     */
    @FXML
    private void handleCloseMenuItem(Event event) {

        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        // get content of textarea
        CodeArea codeBox = (CodeArea)((VirtualizedScrollPane) currentTab.getContent()).getContent();
        String currentContent = codeBox.getText();
        // get the file associated with the current tab
        File file = new File(currentTab.getId());
        // check if changes has been made
        boolean changed = false;
        // check if the textArea has been modified

        // check if the content of the file matches the content of the textarea
        if (file.exists()) {
            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(file.getPath())));
                // if not it has been modified
                if (!currentContent.equals(fileContent)) {
                    changed = true;
                }
            } catch (IOException ex) {
                changed = true;
            }
        }
        else if (!currentContent.equals("")){
            changed = true;
        }

        // if it has been modified
        if (changed) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Changes has been made to " + file.getPath() +
                    "\ndo you want to save it?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    handleSaveMenuItem(event);
                    tabPane.getTabs().remove(currentTab);
                } else if (type == cancelButton) {
                    event.consume();
                }
                else {  // type == noButton
                    tabPane.getTabs().remove(currentTab);
                }
            });
        }
        // if no changes have been made, the tab also closes
        else {
            tabPane.getTabs().remove(currentTab);
        }

        // checks if there's any tab to close; if not, disable menu items
        if (tabPane.getTabs().isEmpty()){
            disableMenuItems(true);
        }
    }

    /**
     * Handler for "save" menu item
     * When the "save" button is clicked, if file of the name of the tab exist in the current directory, it will
     * overwrite the file with the content in the text box of the current tab
     * <p>
     * If that file didn't exist, it will call the save as menu item for the user to put in a new name
     */
    @FXML
    private void handleSaveMenuItem(Event event) {
        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();

        // get the name of the tab (file path)
        String fileName = currentTab.getId();

        File file = new File(fileName);

        if (file.exists()) {
            // get content of textarea
            CodeArea codeBox = (CodeArea)((VirtualizedScrollPane) currentTab.getContent()).getContent();
            String content = codeBox.getText();

            // save the content of the current tab
            SaveFile(content, file);
        } else {
            handleSaveAsMenuItem(event);
        }
    }

    /**
     * Handler for "save as" menu item
     * When the "save as" button is clicked, a save as window appears asking the user to enter
     * a file name for the text file and if the file exist, the prompt will ask user whether to overwrite
     * <p>
     * After file is created successfully, the user will see a prompt, and if not, the user will also see an error
     * message; At the same time, the tab name will be changed to the file path saved
     * <p>
     * Modeled after the Keystore demonstrated at http://java-buddy.blogspot.com/
     */
    @FXML
    private void handleSaveAsMenuItem(Event event) {
        // get the current tab
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        // get the current textBox
        CodeArea codeBox = (CodeArea)((VirtualizedScrollPane) currentTab.getContent()).getContent();

        // initiate a new file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(tabPane.getScene().getWindow());

        if (file != null) {
            if (SaveFile(codeBox.getText(), file)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Successfully created " + file.getPath());
                alert.show();
                // change the name of the tab to the file path
                String[] fileAncestors = file.getPath().split("/");
                currentTab.setText(fileAncestors[fileAncestors.length - 1]);
                currentTab.setId(file.getPath());
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed creating " + file.getPath());
                alert.show();
            }
        }
    }

    /**
     * Helper method for creating a new file
     *
     * @param (content) (the string content of the new file being created)
     * @param (file)    (the file variable passed by handleSaveAsMenuItem function indicating the
     *                  file the user want to save to is valid)
     * @return returns true if file created successfully and false if error occurs
     */
    private boolean SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            Alert failedToSaveAlert = new Alert(AlertType.ERROR);
            failedToSaveAlert.setTitle("Failed to save file");
            failedToSaveAlert.setHeaderText("IO Exception");

            failedToSaveAlert.setContentText(
                    "Error saving file.");

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
     * Handler method for "Undo" in the Edit menu
     * Undo the previous textArea edition
     */
    @FXML
    private void handleUndo(Event event) {
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
    private void handleRedo(Event event) {
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
    private void handleCut(Event event) {
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
    private void handleCopy(Event event) {
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
    private void handlePaste(Event event) {
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
    private void handleSelectAll(Event event) {
        // get the current tab selected
        TextArea textBox = (TextArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        // call the select all method
        textBox.selectAll();
    }
}
