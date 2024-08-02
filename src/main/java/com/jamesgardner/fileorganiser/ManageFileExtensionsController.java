package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.FileType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Manage File Extensions Controller
 * This class is the controller for the managing file extensions fxml window, handling logic and UI interaction.
 * It allows users to add, remove and view file extensions for different file types.
 */
public class ManageFileExtensionsController {

    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private ListView<BorderPane> extensionListView;

    @FXML
    private TextField extensionTextField;

    private ObservableList<BorderPane> extensionList;

    /**
     * Initialise
     * Initialises the controller by populating the java fx combo box element with an array list of file types.
     */
    public void initialize(){
        //create array list and add file type enums to list
        ArrayList<String> fileTypesList = new ArrayList<>();
        //get correct order doing manually
        fileTypesList.add(FileType.DOCUMENTS.toFormattedString());
        fileTypesList.add(FileType.IMAGES.toFormattedString());
        fileTypesList.add(FileType.VIDEOS.toFormattedString());
        fileTypesList.add(FileType.MUSIC.toFormattedString());
        fileTypesList.add(FileType.APPLICATIONS.toFormattedString());
        fileTypesList.add(FileType.ARCHIVES.toFormattedString());
        fileTypesList.add(FileType.SYSTEM_FILES.toFormattedString());

        //set items to combo box
        fileTypeComboBox.setItems(FXCollections.observableArrayList(fileTypesList));
    }

    /**
     * On File Type Selected
     * Handles the event of a file type being selected in the combo box.
     * Updates the extension list view with extension related to the selected file types.
     */
    @FXML
    protected void onFileTypeSelected(){
        String selectedFileType = fileTypeComboBox.getValue();
        extensionList = FXCollections.observableArrayList();
        List<String> extensions = Config.fileTypes.get(getSelectedFileType(selectedFileType));
        for (String extension : extensions){
            extensionList.add(createExtensionItem(extension));
        }
        extensionListView.setItems(extensionList);
    }

    /**
     * Create Extension Item
     * Creates an extension item to be displayed in the extension list view.
     * @param extension The extension to be added.
     * @return The created BorderPane item.
     */
    private BorderPane createExtensionItem(String extension){
        //create border pane, label and remove button
        BorderPane borderPane = new BorderPane();
        Label extensionLabel = new Label(extension);
        Button removeButton = new Button("Remove");

        //set action event on remove button
        removeButton.setOnAction(event -> {
            extensionList.remove(borderPane);
            Config.fileTypes.get(getSelectedFileType(fileTypeComboBox.getValue())).remove(extension);   //remove extension from hashmap
        });

        //set label and remove button in border pane
        borderPane.setLeft(extensionLabel);
        borderPane.setRight(removeButton);
        return borderPane;
    }

    /**
     * On Add Button Click
     * Handles the event when the add button is clicked.
     * Checks the extension is valid and adds a new extension to the selected file type.
     */
    @FXML
    protected void onAddButtonClick(){
        String selectedFileType = fileTypeComboBox.getValue();
        String newExtension = extensionTextField.getText();

        //add a '.' to the start of extension if there is not one
        if (!newExtension.isEmpty() && !newExtension.startsWith(".")){
            newExtension = "." + newExtension;
        }

        //check extension is valid
        if (selectedFileType != null && !newExtension.isEmpty()){
            if (!newExtension.matches("^\\.[A-Za-z0-9._-]+$")){
                System.out.println("Extension not valid");
                //alert the user if not valid
                Alerts.errorAlert("Adding Extension", "The inputted extension was not valid.");
                return;
            }

            //check the extension does not exist in the config hashmap
            if (!checkExtensionExists(newExtension)){
                List<String> extensions = Config.fileTypes.get(getSelectedFileType(selectedFileType));
                if (!extensions.contains(newExtension)){
                    //add new extension to config file and extension list view
                    extensions.add(newExtension);
                    extensionList.add(createExtensionItem(newExtension));
                    extensionTextField.clear();                         //clear text field
                }
            } else{
                System.out.println("Extension already exists");
                Alerts.errorAlert("Adding Extension", "The inputted extension already exists.");
            }
        } else{
            System.out.println("No file type selected or no extension entered");
            Alerts.errorAlert("Adding Extension", "No file type was selected or no extension was entered.");
        }
    }

    /**
     * Check Extension Exists
     * This function checks if an extension exists within the config hashmap.
     * @param newExtension The new extension to check, entered by the user.
     * @return true if the extension exists, else false.
     */
    private boolean checkExtensionExists(String newExtension){
        //check each extension in hashmap
        for (Map.Entry<FileType, List<String>> entry : Config.fileTypes.entrySet()){
            for (String extension : entry.getValue()){
                if (extension.equals(newExtension)){
                    System.out.println(newExtension + " exists in config");
                    return true;    //if exists
                }
            }
        }
        return false;   //if it is a new extension return false
    }


    /**
     * On Close Button Click
     * Handles the event when the close button is clicked.
     * Closes the window
     */
    @FXML
    protected void onCloseButtonClick(){
        Stage stage = (Stage) extensionTextField.getScene().getWindow();
        stage.close();
    }

    /**
     * Get Selected File Type
     * Converts a formatted string to the relevant file type enum value.
     * @param selectedFileType The formatted string representing the file type.
     * @return The corresponding file type enum value.
     */
    private FileType getSelectedFileType(String selectedFileType){
        return FileType.values()[fileTypeComboBox.getItems().indexOf(selectedFileType)];
    }
}
