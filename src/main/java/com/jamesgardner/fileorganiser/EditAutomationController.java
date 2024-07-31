package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.DateFrequency;
import com.jamesgardner.fileorganiser.enums.FileType;
import com.jamesgardner.fileorganiser.enums.OrganiseMode;
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

    private OrganiseMode mode;
    private List<FileType> selectedFileTypes;
    private DateFrequency dateFrequency;

    public void setPath(String path){
        directoryTextField.setText(path);
    }

    public void setOrganisedBy(OrganiseMode mode, List<FileType> selectedFileTypes){
        this.mode = mode;
        setOrganisedByRadio(mode);

        this.selectedFileTypes = selectedFileTypes;
        for(FileType fileType : selectedFileTypes){
            switch (fileType){
                case FileType.DOCUMENTS:
                    docsCheckBox.setSelected(true);
                    break;
                case FileType.IMAGES:
                    imgCheckBox.setSelected(true);
                    break;
                case FileType.VIDEOS:
                    vidCheckBox.setSelected(true);
                    break;
                case FileType.MUSIC:
                    musicCheckBox.setSelected(true);
                    break;
                case FileType.APPLICATIONS:
                    appCheckBox.setSelected(true);
                    break;
                case FileType.ARCHIVES:
                    archCheckBox.setSelected(true);
                    break;
                case FileType.SYSTEM_FILES:
                    sysCheckBox.setSelected(true);
                    break;
            }
        }
    }

    public void setOrganisedBy(OrganiseMode mode, DateFrequency dateFrequency){
        this.mode = mode;
        setOrganisedByRadio(OrganiseMode.DATE);

        this.dateFrequency = dateFrequency;
        if (dateFrequency.equals(DateFrequency.YEARLY)){
            yearlyRadioButton.setSelected(true);
        } else if(dateFrequency.equals(DateFrequency.MONTHLY)){
            monthlyRadioButton.setSelected(true);
        }
    }

    private void setOrganisedByRadio(OrganiseMode mode) {
        if (mode.equals(OrganiseMode.FILE_TYPE)) {
            fileTypeRadioButton.setSelected(true);
            fileTypeVbox.setVisible(true);
            fileTypeVbox.setManaged(true);
            dateVbox.setVisible(false);
            dateVbox.setManaged(false);

        } else if (mode.equals(OrganiseMode.DATE)) {
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

    private boolean checkChanged(OrganiseMode mode){
        OrganiseMode newMode;
        if (fileTypeRadioButton.isSelected()){
            newMode = OrganiseMode.FILE_TYPE;
        } else {
            newMode = OrganiseMode.DATE;
        }

        if (!mode.equals(newMode)) {
            return true;
        } else if (mode.equals(OrganiseMode.FILE_TYPE)) {
            return !selectedFileTypes.equals(getSelectedFileTypes());
        } else {
            return !dateFrequency.equals(getDateFrequency());
        }
    }

    private ArrayList<FileType> getSelectedFileTypes() {
        ArrayList<FileType> selectedFileTypes = new ArrayList<>();
        if (docsCheckBox.isSelected()) {selectedFileTypes.add(FileType.DOCUMENTS);}
        if (imgCheckBox.isSelected()) {selectedFileTypes.add(FileType.IMAGES);}
        if (vidCheckBox.isSelected()) {selectedFileTypes.add(FileType.VIDEOS);}
        if (musicCheckBox.isSelected()) {selectedFileTypes.add(FileType.MUSIC);}
        if (appCheckBox.isSelected()) {selectedFileTypes.add(FileType.APPLICATIONS);}
        if (archCheckBox.isSelected()) {selectedFileTypes.add(FileType.ARCHIVES);}
        if (sysCheckBox.isSelected()) {selectedFileTypes.add(FileType.SYSTEM_FILES);}

        if (selectedFileTypes.isEmpty()){
            throw new IllegalArgumentException("No file types selected");
        }

        return selectedFileTypes;
    }

    private DateFrequency getDateFrequency(){
        if (yearlyRadioButton.isSelected()){
            return DateFrequency.YEARLY;
        } else if (monthlyRadioButton.isSelected()) {
            return DateFrequency.MONTHLY;
        }
        throw new IllegalArgumentException("Invalid date frequency");
    }

}
