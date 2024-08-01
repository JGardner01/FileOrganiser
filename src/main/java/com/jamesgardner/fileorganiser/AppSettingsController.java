package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class AppSettingsController {

    @FXML
    private TextField directoryTextField;

    @FXML
    private CheckBox backgroundCheckBox;

    @FXML
    private CheckBox startUpCheckBox;

    //private final String = ""

    public void initialize() {
        directoryTextField.setText(Config.defaultPath);
    }

    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        //System.out.println(selectedDirectory);//debug
        directoryTextField.setText(String.valueOf(selectedDirectory));
    }

    @FXML
    protected  void onCancelButtonClick(){
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }

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
            Config.defaultPath = path;
        }

        AppSettings.setRunInBackground(backgroundCheckBox.isSelected());
        AppSettings.setRunAtStartUp(startUpCheckBox.isSelected());
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }



}
