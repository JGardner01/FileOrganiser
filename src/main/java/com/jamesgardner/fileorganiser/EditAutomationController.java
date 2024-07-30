package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditAutomationController {
    @FXML
    private TextField directoryTextField;

    @FXML
    private RadioButton fileTypeRadioButton;
    @FXML
    private RadioButton dateRadioButton;

    @FXML
    private VBox fileTypeVbox;
    @FXML
    private VBox dateVbox;

    @FXML
    private CheckBox docsCheckBox;
    @FXML
    private CheckBox imgCheckBox;
    @FXML
    private CheckBox vidCheckBox;
    @FXML
    private CheckBox musicCheckBox;
    @FXML
    private CheckBox appCheckBox;
    @FXML
    private CheckBox archCheckBox;
    @FXML
    private CheckBox sysCheckBox;

    @FXML
    private RadioButton yearlyRadioButton;
    @FXML
    private RadioButton monthlyRadioButton;

    private Button removeButton;
    private MainController mainController;

    private String mode;
    private List<String> selectedFileTypes;
    private String dateFrequency;

    public void setPath(String path){
        directoryTextField.setText(path);
    }

    public void setOrganisedBy(String mode, List<String> selectedFileTypes){
        this.mode = mode;
        setOrganisedByRadio(mode);

        this.selectedFileTypes = selectedFileTypes;
        for(String fileType : selectedFileTypes){
            switch (fileType){
                case "Documents":
                    docsCheckBox.setSelected(true);
                    break;
                case "Images":
                    imgCheckBox.setSelected(true);
                    break;
                case "Videos":
                    vidCheckBox.setSelected(true);
                    break;
                case "Music":
                    musicCheckBox.setSelected(true);
                    break;
                case "Applications":
                    appCheckBox.setSelected(true);
                    break;
                case "Archives":
                    archCheckBox.setSelected(true);
                    break;
                case "System Files":
                    sysCheckBox.setSelected(true);
                    break;
            }
        }
    }

    public void setOrganisedBy(String mode, String dateFrequency){
        this.mode = mode;
        setOrganisedByRadio("date");

        this.dateFrequency = dateFrequency;
        if (dateFrequency.equals("Yearly")){
            yearlyRadioButton.setSelected(true);
        } else if(dateFrequency.equals("Monthly")){
            monthlyRadioButton.setSelected(true);
        }
    }

    private void setOrganisedByRadio(String mode) {
        if (mode.equals("fileType")) {
            fileTypeRadioButton.setSelected(true);
            fileTypeVbox.setVisible(true);
            fileTypeVbox.setManaged(true);
            dateVbox.setVisible(false);
            dateVbox.setManaged(false);

        } else if (mode.equals("date")) {
            dateRadioButton.setSelected(true);
            fileTypeVbox.setVisible(false);
            fileTypeVbox.setManaged(false);
            dateVbox.setVisible(true);
            dateVbox.setManaged(true);
        }
    }

    @FXML
    public void onOrganiseByRadio(){
        if (fileTypeRadioButton.isSelected()){
            fileTypeVbox.setVisible(true);
            fileTypeVbox.setManaged(true);
            dateVbox.setVisible(false);
            dateVbox.setManaged(false);
        } else {
            fileTypeVbox.setVisible(false);
            fileTypeVbox.setManaged(false);
            dateVbox.setVisible(true);
            dateVbox.setManaged(true);
        }
    }

    public void setRemoveButton(Button removeButton){
        this.removeButton = removeButton;
    }

    public void passMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    protected void onRemoveAutomationButtonClick(){
        if(removeButton != null)
        {
            System.out.println("Removing Automation");
            removeButton.fire();
            removeButton = null;

            Stage stage = (Stage) directoryTextField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    protected void onSaveCloseButtonClick(){
        if (checkChanged(mode)){
            onRemoveAutomationButtonClick();

            String path = directoryTextField.getText();
            try{
                if (fileTypeRadioButton.isSelected()){
                    mainController.addToAutomations(path, getSelectedFileTypes());
                } else if (dateRadioButton.isSelected()) {
                    mainController.addToAutomations(path, getDateFrequency());
                } else{
                    throw new IllegalArgumentException("Organise by not valid");
                }
            } catch (Exception e){
                System.out.println("Exception:" + e.getMessage());
            }
        }
        Stage stage = (Stage) directoryTextField.getScene().getWindow();
        stage.close();
    }

    private boolean checkChanged(String mode){
        String newMode;
        if (fileTypeRadioButton.isSelected()){
            newMode = "fileType";
        } else {
            newMode = "date";
        }

        if (!mode.equals(newMode)) {
            return true;
        } else if (mode.equals("fileType")) {
            return !selectedFileTypes.equals(getSelectedFileTypes());
        } else {
            return !dateFrequency.equals(getDateFrequency());
        }
    }

    private ArrayList<String> getSelectedFileTypes() {
        ArrayList<String> selectedFileTypes = new ArrayList<>();
        if (docsCheckBox.isSelected()) {selectedFileTypes.add("Documents");}
        if (imgCheckBox.isSelected()) {selectedFileTypes.add("Images");}
        if (vidCheckBox.isSelected()) {selectedFileTypes.add("Videos");}
        if (musicCheckBox.isSelected()) {selectedFileTypes.add("Music");}
        if (appCheckBox.isSelected()) {selectedFileTypes.add("Applications");}
        if (archCheckBox.isSelected()) {selectedFileTypes.add("Archives");}
        if (sysCheckBox.isSelected()) {selectedFileTypes.add("System Files");}

        if (selectedFileTypes.isEmpty()){
            throw new IllegalArgumentException("No file types selected");
        }

        return selectedFileTypes;
    }

    private String getDateFrequency(){
        if (yearlyRadioButton.isSelected()){
            return "Yearly";
        } else if (monthlyRadioButton.isSelected()) {
            return "Monthly";
        }
        throw new IllegalArgumentException("Invalid date frequency");
    }

}
