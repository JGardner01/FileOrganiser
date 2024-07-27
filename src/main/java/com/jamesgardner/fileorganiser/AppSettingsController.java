package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class AppSettingsController {

    @FXML
    private CheckBox backgroundCheckBox;

    @FXML
    private CheckBox startUpCheckBox;


    @FXML
    protected  void onCancelButtonClick(){
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSaveButtonClick(){
        AppSettings.setRunInBackground(backgroundCheckBox.isSelected());
        AppSettings.setRunAtStartUp(startUpCheckBox.isSelected());
        Stage stage = (Stage) backgroundCheckBox.getScene().getWindow();
        stage.close();
    }



}
