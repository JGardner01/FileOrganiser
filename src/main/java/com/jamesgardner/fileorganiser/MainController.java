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


public class MainController {
    public final int maxCharAutomationName = 20;

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

    private final AutomationManager automationManager = new AutomationManager();


    public void initialize() {
        directoryTextField.setText(Config.defaultPath);
        automationListView.setItems(automationList);
    }

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

    @FXML
    protected void onExitClick() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void onHelpClick() throws IOException {
        Stage helpStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        helpStage.setTitle("Help");
        helpStage.setScene(scene);
        helpStage.show();
    }
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

    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        //System.out.println(selectedDirectory);//debug
        directoryTextField.setText(String.valueOf(selectedDirectory));
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

    @FXML
    protected void onOrganiseButtonClick() {
        System.out.println("Organising " + directoryTextField.getText());

        try{
            boolean organised = false;
            if (fileTypeRadioButton.isSelected()){
                FileOrganiser fileOrganiser = new FileOrganiser(getPath(), getSelectedFileTypes());
                organised = fileOrganiser.organiseFiles();
            } else if (dateRadioButton.isSelected()){
                FileOrganiser fileOrganiser = new FileOrganiser(getPath(), getDateFrequency());
                organised = fileOrganiser.organiseFiles();
            }

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

    public void addToAutomations(String path, List<FileType> selectedFileTypes) {
        DirectoryAutomator directoryAutomator;
        directoryAutomator = new DirectoryAutomator(path, selectedFileTypes);
        if (automationManager.automateDirectory(path, directoryAutomator)){
            BorderPane automationListItem = createAutomationListItem(path);
            automationList.add(automationListItem);
        } else{
            System.out.println("Could not add automation");
        }
    }

    public void addToAutomations(String path, DateFrequency dateFrequency) {
        DirectoryAutomator directoryAutomator;
        directoryAutomator = new DirectoryAutomator(path, dateFrequency);
        if (automationManager.automateDirectory(path, directoryAutomator)){
            BorderPane automationListItem = createAutomationListItem(path);
            automationList.add(automationListItem);
        } else{
            System.out.println("Could not add automation");
        }
    }



    private BorderPane createAutomationListItem(String path){
        BorderPane borderPane = new BorderPane();
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(5);

        String directoryName = new File(path).getName();
        if (directoryName.length() > maxCharAutomationName){
            directoryName = directoryName.substring(0, maxCharAutomationName) + "...";
        }
        Label nameLabel = new Label(directoryName);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(actionEvent -> {
            try{
                automationManager.endAutomation(path);
                automationList.remove(borderPane);
            } catch (Exception e){
                System.out.println("Removing automation exception: " + e.getMessage());
            }
        });


        OrganiseMode organisedBy;
        Object organisedByValue;

        if (fileTypeRadioButton.isSelected()){
            organisedBy = OrganiseMode.FILE_TYPE;
            organisedByValue = getSelectedFileTypes();
        } else {
            organisedBy = OrganiseMode.DATE;
            organisedByValue = getDateFrequency();
        }

        Button editButton = new Button("Edit");
        editButton.setOnAction(actionEvent -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAutomation.fxml"));
                Parent parent = fxmlLoader.load();

                EditAutomationController editAutomationController = fxmlLoader.getController();
                editAutomationController.setPath(path);

                if (organisedByValue instanceof List){
                    editAutomationController.setOrganisedBy(organisedBy, (List<FileType>) organisedByValue);
                } else {
                    editAutomationController.setOrganisedBy(organisedBy, (DateFrequency) organisedByValue);
                }

                editAutomationController.setRemoveButton(removeButton);
                editAutomationController.passMainController(this);

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

        buttonHbox.getChildren().addAll(editButton,removeButton);
        borderPane.setLeft(nameLabel);
        borderPane.setRight(buttonHbox);


        return borderPane;
    }

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
            Alerts.errorAlert( "File Type Selection", "There were no file types selected to organise the files by.");
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
        Alerts.errorAlert( "Invalid Date Frequency", "Invalid date frequency selected or there was no date frequency selected.");
        throw new IllegalArgumentException("Invalid date frequency");
    }
}