package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.DateFrequency;
import com.jamesgardner.fileorganiser.enums.FileType;
import com.jamesgardner.fileorganiser.enums.OrganiseMode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Controller
 * This class is the controller for the Main FXML.
 * It handles the interaction between the UI and the back end logic.
 * Manages user input, directory selection and the organisation of files.
 */
public class MainController {
    public final int maxCharAutomationName = 20;    //max length of automation names

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

    @FXML
    private ListView<BorderPane> automationListView;
    private final ObservableList<BorderPane> automationList = FXCollections.observableArrayList();

    //create automation manager
    private final AutomationManager automationManager = new AutomationManager();

    /**
     * Initialise
     * Initialises the controller.
     */
    public void initialize() {
        //sets java fx elements with relevant variables
        directoryTextField.setText(Config.defaultPath);
        automationListView.setItems(automationList);
    }

    /**
     * On app settings click
     * Loads the AppSettings fxml and opens the settings window.
     * @throws IOException If there is an issue loading the FXML.
     */
    @FXML
    protected void onAppSettingsClick() throws IOException {
        Stage settingsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppSettings.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        settingsStage.setTitle("App Settings");
        settingsStage.setScene(scene);
        settingsStage.setResizable(false);
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.showAndWait();
    }

    /**
     * On additional file extensions click
     * Loads the ManageFileExtensions fxml and opens the manage file extensions window.
     * @throws IOException If there is an issue loading the FXML.
     */
    @FXML
    protected void onAdditionalFileExtensionsClick() throws IOException{
        Stage extensionsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManageFileExtensions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        extensionsStage.setTitle("Manage File Extensions");
        extensionsStage.setScene(scene);
        extensionsStage.initModality(Modality.APPLICATION_MODAL);
        extensionsStage.showAndWait();
    }

    /**
     * On exit click
     * Closes the application
     */
    @FXML
    protected void onExitClick() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * On help click
     * Loads the ManageFileExtensions fxml and opens the manage file extensions window.
     * @throws IOException If there is an issue loading the FXML.
     */
    @FXML
    protected void onHelpClick() throws IOException {
        Stage helpStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        helpStage.setTitle("Help");
        helpStage.setScene(scene);
        helpStage.show();
    }

    /**
     * On about click
     * Loads the ManageFileExtensions fxml and opens the manage file extensions window.
     * @throws IOException If there is an issue loading the FXML.
     */
    @FXML
    protected void onAboutClick() throws IOException{
        Stage aboutStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        aboutStage.setTitle("About");
        aboutStage.setResizable(false);
        aboutStage.setScene(scene);
        aboutStage.show();
    }


    /**
     * On Select Directory Button Click
     * Create directory choose and open the window for the user to select a directory
     */
    @FXML
    protected void onSelectDirectoryButtonClick() {
        //create directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");
        File selectedDirectory = directoryChooser.showDialog(new Stage());  //show directory chooser and assign selected directory
        directoryTextField.setText(String.valueOf(selectedDirectory));      //set directory text field as the path selected by the user
    }

    /**
     * On organise by radio
     * Toggles between file type and date organisation options.
     * Changes vbox visibility of relevant organisation options.
     */
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

    /**
     * On organise button click
     * Creates file organiser and organises files based on user criteria.
     * Alerts the user if the organisation was success or error.
     */
    @FXML
    protected void onOrganiseButtonClick() {
        System.out.println("Organising " + directoryTextField.getText());

        try{
            boolean organised = false;
            //create file organiser and organise files
            if (fileTypeRadioButton.isSelected()){
                FileOrganiser fileOrganiser = new FileOrganiser(getPath(), getSelectedFileTypes());
                organised = fileOrganiser.organiseFiles();
            } else if (dateRadioButton.isSelected()){
                FileOrganiser fileOrganiser = new FileOrganiser(getPath(), getDateFrequency());
                organised = fileOrganiser.organiseFiles();
            }

            //alerts the user if successful or error
            if (organised){
                System.out.print("Files were organised");
                Alerts.infoAlert( "Organising Files", "Files were organised successfully.");
            } else {
                System.out.println("Error organising files");
                Alerts.errorAlert( "Organising Files", "An error occurred organising files.");
            }
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
            Alerts.errorAlert( "Organising Files", "An error occurred organising files.");
        }
    }

    /**
     * On add to automations button click
     * Handles automate directory button click action, calls add to automations function.
     */
    @FXML
    protected void onAddToAutomationsButtonCLick(){
        try{
            if (fileTypeRadioButton.isSelected()){
                addToAutomations(getPath(), getSelectedFileTypes());
            } else if (dateRadioButton.isSelected()) {
                addToAutomations(getPath(), getDateFrequency());
            } else{
                throw new IllegalArgumentException("Organise by not valid");
            }
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
            Alerts.errorAlert("Automating Directory", "The organise by mode selected is not valid.");
        }
    }

    /**
     * Add to automations
     * Creates directory automator in file type mode, creates automation list item and adds it to the automation list, if the
     * automation was successful.
     * @param path The directory path.
     * @param selectedFileTypes The selected file types.
     */
    public void addToAutomations(String path, List<FileType> selectedFileTypes) {
        //create directory automator
        DirectoryAutomator directoryAutomator;
        directoryAutomator = new DirectoryAutomator(path, selectedFileTypes);
        //if automation successful create automation list item
        if (automationManager.automateDirectory(path, directoryAutomator)){
            BorderPane automationListItem = createAutomationListItem(path);
            automationList.add(automationListItem);
        } else{
            System.out.println("Could not add automation");
            Alerts.errorAlert("Automating Directory", "An error occurred automating this directory.");
        }
    }

    /**
     * Add to automations
     * Creates directory automator in date mode, creates automation list item and adds it to the automation list, if the
     * automation was successful.
     * @param path The directory path.
     * @param dateFrequency The selected date frequency.
     */
    public void addToAutomations(String path, DateFrequency dateFrequency) {
        //create directory automator
        DirectoryAutomator directoryAutomator;
        directoryAutomator = new DirectoryAutomator(path, dateFrequency);
        //if automation successful create automation list item
        if (automationManager.automateDirectory(path, directoryAutomator)){
            BorderPane automationListItem = createAutomationListItem(path);
            automationList.add(automationListItem);
        } else{
            System.out.println("Could not add automation");
            Alerts.errorAlert("Automating Directory", "An error occurred automating this directory.");
        }
    }

    /**
     * Create automation list item
     * Creates an automation list item to be displayed in the automation list view.
     * Shortens the directory name if longer than the max character limit, adds remove and edit buttons and action listeners.
     * Remove button, ends the automation and removes the automation item from automation list.
     * Edit button, loads edit automation fxml, passes the variables and objects needed to the edit automation controller then opens
     * window to edit the automation configuration.
     * @param path The directory path.
     * @return The created BorderPane item.
     */
    private BorderPane createAutomationListItem(String path){
        //create border pane
        BorderPane borderPane = new BorderPane();
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(5);

        //shorten directory name if over max char limit
        String directoryName = new File(path).getName();
        if (directoryName.length() > maxCharAutomationName){
            directoryName = directoryName.substring(0, maxCharAutomationName) + "...";
        }
        Label nameLabel = new Label(directoryName);

        //create remove button and create action
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(actionEvent -> {
            try{
                automationManager.endAutomation(path);
                automationList.remove(borderPane);
            } catch (Exception e){
                System.out.println("Removing automation exception: " + e.getMessage());
            }
        });

        //create mode and organising criteria variables
        OrganiseMode organisedBy;
        Object organisedByValue;

        //assign variables based on mode
        if (fileTypeRadioButton.isSelected()){
            organisedBy = OrganiseMode.FILE_TYPE;
            organisedByValue = getSelectedFileTypes();
        } else {
            organisedBy = OrganiseMode.DATE;
            organisedByValue = getDateFrequency();
        }

        //create edit button and create action
        Button editButton = new Button("Edit");
        editButton.setOnAction(actionEvent -> {
            try{
                //load edit automation
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAutomation.fxml"));
                Parent parent = fxmlLoader.load();

                //get edit automation controller
                EditAutomationController editAutomationController = fxmlLoader.getController();
                editAutomationController.setPath(path);

                //set organise by mode and criteria
                if (organisedByValue instanceof List){
                    editAutomationController.setOrganisedBy(organisedBy, (List<FileType>) organisedByValue);
                } else {
                    editAutomationController.setOrganisedBy(organisedBy, (DateFrequency) organisedByValue);
                }

                //pass remove button and main controller to edit automation controller
                editAutomationController.setRemoveButton(removeButton);
                editAutomationController.passMainController(this);

                //display window
                Stage editAutomationsStage = new Stage();
                Scene scene = new Scene(parent);
                editAutomationsStage.setTitle("Edit Automation");
                editAutomationsStage.setScene(scene);
                editAutomationsStage.setResizable(false);
                editAutomationsStage.initModality(Modality.APPLICATION_MODAL);
                editAutomationsStage.showAndWait();
            } catch (IOException e){
                System.out.println("Exception:" + e.getMessage());
            }
        });

        //add buttons and set items in border pane
        buttonHbox.getChildren().addAll(editButton,removeButton);
        borderPane.setLeft(nameLabel);
        borderPane.setRight(buttonHbox);

        return borderPane;
    }

    /**
     * Get Path
     * Checks the path is valid and returns the selected directory path.
     * @return The directory path as a string
     */
    private String getPath(){
        String path = directoryTextField.getText();
        File directory = new File(path);
        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            Alerts.errorAlert( "Invalid Directory", "The selected directory does not exist or is not a valid directory.");
            throw new IllegalArgumentException("Invalid directory");
        }
        return path;
    }

    /**
     * Get Selected File Types
     * Creates a list and adds the selected checkbox file types to the list.
     * Checks if the list is empty and display alert an error to the user.
     * @return A list of the selected file types.
     */
    private ArrayList<FileType> getSelectedFileTypes() {
        //create list and add to list
        ArrayList<FileType> selectedFileTypes = new ArrayList<>();
        if (docsCheckBox.isSelected()) {selectedFileTypes.add(FileType.DOCUMENTS);}
        if (imgCheckBox.isSelected()) {selectedFileTypes.add(FileType.IMAGES);}
        if (vidCheckBox.isSelected()) {selectedFileTypes.add(FileType.VIDEOS);}
        if (musicCheckBox.isSelected()) {selectedFileTypes.add(FileType.MUSIC);}
        if (appCheckBox.isSelected()) {selectedFileTypes.add(FileType.APPLICATIONS);}
        if (archCheckBox.isSelected()) {selectedFileTypes.add(FileType.ARCHIVES);}
        if (sysCheckBox.isSelected()) {selectedFileTypes.add(FileType.SYSTEM_FILES);}

        //check if list is empty
        if (selectedFileTypes.isEmpty()){
            Alerts.errorAlert( "File Type Selection", "There were no file types selected to organise the files by.");
            throw new IllegalArgumentException("No file types selected");
        }

        return selectedFileTypes;
    }

    /**
     * Date Frequency
     * Retrieves the selected date frequency from the java fx radio buttons.
     * @return the selected date frequency.
     */
    private DateFrequency getDateFrequency(){
        if (yearlyRadioButton.isSelected()){
            return DateFrequency.YEARLY;
        } else if (monthlyRadioButton.isSelected()) {
            return DateFrequency.MONTHLY;
        }
        Alerts.errorAlert( "Invalid Date Frequency", "Invalid date frequency selected or there was no date frequency selected.");
        throw new IllegalArgumentException("Invalid date frequency");
    }
}