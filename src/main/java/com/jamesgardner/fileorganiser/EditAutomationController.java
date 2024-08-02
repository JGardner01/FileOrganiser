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

/**
 * Edit Automation Controller
 * @author James Gardner
 * This class handles the user interactions for editing automation configurations.
 */
public class EditAutomationController {
    //java fx ui elements
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

    //file type check boxes
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

    //declare variables
    private Button removeButton;
    private MainController mainController;

    private OrganiseMode mode;
    private List<FileType> selectedFileTypes;
    private DateFrequency dateFrequency;

    /**
     * Set Path
     * sets the path of the directory to be displayed in the text field
     * @param path The path of the directory to be displayed.
     */
    public void setPath(String path){
        directoryTextField.setText(path);
    }

    /**
     * Set Organised By
     * Sets the organisation mode and file types for the directory.
     * Updates the UI to be in line with selection.
     * @param mode The mode of organisation (file type).
     * @param selectedFileTypes The list of user selected files.
     */
    public void setOrganisedBy(OrganiseMode mode, List<FileType> selectedFileTypes){
        this.mode = mode;
        setOrganisedByRadio(mode);  //check file type radio button

        //check file types check boxes
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

    /**
     * Set Organised By
     * Sets the organisation mode and date frequency for the directory.
     * Updates the UI to be in line with selection.
     * @param mode The mode of organisation (date).
     * @param dateFrequency The date frequency for organisation.
     */
    public void setOrganisedBy(OrganiseMode mode, DateFrequency dateFrequency){
        this.mode = mode;
        setOrganisedByRadio(OrganiseMode.DATE);     //check date radio button

        //check selected date frequency radio button.
        this.dateFrequency = dateFrequency;
        if (dateFrequency.equals(DateFrequency.YEARLY)){
            yearlyRadioButton.setSelected(true);
        } else if(dateFrequency.equals(DateFrequency.MONTHLY)){
            monthlyRadioButton.setSelected(true);
        }
    }

    /**
     * Set Organised By Radio
     * Sets the organisation mode by updating the relevant java fx radio buttons and vbox visibilities.
     * @param mode The mode of organisation (date or file type).
     */
    private void setOrganisedByRadio(OrganiseMode mode) {
        //set file type radio button to true and remove date frequency selection visibility.
        if (mode.equals(OrganiseMode.FILE_TYPE)) {
            fileTypeRadioButton.setSelected(true);
            fileTypeVbox.setVisible(true);
            fileTypeVbox.setManaged(true);
            dateVbox.setVisible(false);
            dateVbox.setManaged(false);

        //set date radio button to true and remove file type selection visibility.
        } else if (mode.equals(OrganiseMode.DATE)) {
            dateRadioButton.setSelected(true);
            fileTypeVbox.setVisible(false);
            fileTypeVbox.setManaged(false);
            dateVbox.setVisible(true);
            dateVbox.setManaged(true);
        }
    }

    /**
     * On Organise By Radio
     * Handles switching between the file type and date organisation modes, updates java fx UI when a organise by mode
     * radio button is selected.
     */
    @FXML
    public void onOrganiseByRadio(){
        //set file type selection visibility to true and remove date frequency selection visibility.
        if (fileTypeRadioButton.isSelected()){
            fileTypeVbox.setVisible(true);
            fileTypeVbox.setManaged(true);
            dateVbox.setVisible(false);
            dateVbox.setManaged(false);

        //set date selection visibility to true and remove file type selection visibility.
        } else {
            fileTypeVbox.setVisible(false);
            fileTypeVbox.setManaged(false);
            dateVbox.setVisible(true);
            dateVbox.setManaged(true);
        }
    }

    /**
     * Set Remove Button
     * Sets the remove button to be used for removing the automation thread.
     * Passed from main controller.
     * @param removeButton The automation's remove button.
     */
    public void setRemoveButton(Button removeButton){
        this.removeButton = removeButton;
    }

    /**
     * Pass Main Controller
     * Passes the main controller to this controller to access some functions.
     * @param mainController The main controller.
     */
    public void passMainController(MainController mainController){
        this.mainController = mainController;
    }

    /**
     * On Remove Automation Button Click
     * Handles removing the automation thread for this directory.
     * Uses the remove button passed from the main controller to remove the automation thread and all of its UI components.
     */
    @FXML
    protected void onRemoveAutomationButtonClick(){
        //check the remove button was passed
        if(removeButton != null)
        {
            System.out.println("Removing Automation");
            removeButton.fire();    //uses remove button's function
            removeButton = null;    //clears the remove button variable

            //close the window
            Stage stage = (Stage) directoryTextField.getScene().getWindow();
            stage.close();
        } else {
            //alert if remove button was not passed correctly
            System.err.println("Edit automation controller remove button is null");
            Alerts.errorAlert( "Removing Automation", "An error occurred while attempting to remove automation.");
        }
    }

    /**
     * On Save Close Button Click
     * This function handle saving the changes to the automation configuration and closing the window.
     * Saving changes only triggered if there were changes made.
     */
    @FXML
    protected void onSaveCloseButtonClick(){
        if (checkChanged(mode)){    //check if changes were made
            onRemoveAutomationButtonClick();    //remove current configuration

            String path = directoryTextField.getText();

            //recreate automation using new updated configuration
            try{
                if (fileTypeRadioButton.isSelected()){
                    mainController.addToAutomations(path, getSelectedFileTypes());
                } else if (dateRadioButton.isSelected()) {
                    mainController.addToAutomations(path, getDateFrequency());
                } else{
                    Alerts.errorAlert("Saving Edited Automation Changes", "The organise by mode selected is not valid.");
                    throw new IllegalArgumentException("Organise by not valid");
                }
            } catch (Exception e){
                System.err.println("Exception:" + e.getMessage());
            }
        }
        //close window
        Stage stage = (Stage) directoryTextField.getScene().getWindow();
        stage.close();
    }

    /**
     * Check Changed
     * Check if there were any changes in the automation configuration by comparing variables.
     * @param mode The original organisation mode used for comparison.
     * @return ture if there were changes to the configuration, else false.
     */
    private boolean checkChanged(OrganiseMode mode){
        //find and assign new organisation mode
        OrganiseMode newMode;
        if (fileTypeRadioButton.isSelected()){
            newMode = OrganiseMode.FILE_TYPE;
        } else {
            newMode = OrganiseMode.DATE;
        }

        //check if the modes and organisation criteria is different
        if (!mode.equals(newMode)) {
            return true;
        } else if (mode.equals(OrganiseMode.FILE_TYPE)) {
            return !selectedFileTypes.equals(getSelectedFileTypes());
        } else {
            return !dateFrequency.equals(getDateFrequency());
        }
    }

    /**
     * Get Selected File Types
     * Creates a list of all the file types selected in the checkboxes.
     * @return List of selected file types.
     */
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
            Alerts.errorAlert("Saving Edited Automation Changes", "No file types were selected.");
            throw new IllegalArgumentException("No file types selected");
        }

        return selectedFileTypes;
    }

    /**
     * Get Date Frequency
     * Gets the selected date frequency from the radio buttons.
     * @return The selected date frequency.
     */
    private DateFrequency getDateFrequency(){
        if (yearlyRadioButton.isSelected()){
            return DateFrequency.YEARLY;
        } else if (monthlyRadioButton.isSelected()) {
            return DateFrequency.MONTHLY;
        }
        Alerts.errorAlert("Saving Edited Automation Changes", "Invalid date frequency selected.");
        throw new IllegalArgumentException("Invalid date frequency");
    }

}
