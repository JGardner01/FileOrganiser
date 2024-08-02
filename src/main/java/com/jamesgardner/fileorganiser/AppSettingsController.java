package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * App Settings Controller
 * @author James Gardner
 * This class is responsible for handling user actions in the App Settings window.
 * This includes selecting a default directory, setting application preferences and saving or cancelling the changes.
 */
public class AppSettingsController {

    //fxml java fx elements
    @FXML
    private TextField directoryTextField;

    @FXML
    private CheckBox backgroundCheckBox;

    @FXML
    private CheckBox startUpCheckBox;

    /**
     * Initialise
     * This function is called automatically after the FXML file is loaded.
     * Sets the text in the directory text field element to the current default config path.
     */
    public void initialize() {
        directoryTextField.setText(Config.defaultPath);
    }

    /**
     * On Select Directory Button Click
     * This function opens a window to select a directory.
     * Then updates the directory text field with the selected directory path.
     * This is triggered when the user clicks on the 'Browse' button.
     */
    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        //System.out.println(selectedDirectory);//debug
        directoryTextField.setText(String.valueOf(selectedDirectory));
    }

    /**
     * On Cancel Button Click
     * This function closes the app settings window without saving any changes.
     * This is triggered when the user clicks on the 'Cancel' button.
     */
    @FXML
    protected  void onCancelButtonClick(){
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }

    /**
     * On Save Button Click
     * This function validates the selected directory and updates the app settings based on the new input.
     * If the directory is invalid, an error alert is displayed.
     */
    @FXML
    protected void onSaveButtonClick(){

        String path = directoryTextField.getText();
        File directory = new File(path);

        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Directory invalid");
            Alerts.errorAlert("Changing Default Directory", "The selected directory does not exist or is not a valid directory.");
            return;
        } else {
            //update app settings
            Config.defaultPath = path;
        }

        //update app settings
        AppSettings.setRunInBackground(backgroundCheckBox.isSelected());
        AppSettings.setRunAtStartUp(startUpCheckBox.isSelected());

        //close window
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }
}
