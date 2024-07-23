package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class MainController {
    @FXML
    private TextField directoryTextField;

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

    private AutomationManager automationManager = new AutomationManager();


    public void initialize() {
        directoryTextField.setText(Config.defaultPath);
    }


    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        System.out.println(selectedDirectory);//debug
        directoryTextField.setText(String.valueOf(selectedDirectory));
    }

    @FXML
    protected void onOrganiseButtonClick() {
        System.out.println("Organising " + directoryTextField.getText());

        String path = directoryTextField.getText();
        File directory = new File(path);

        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            return;
        }

        ArrayList<String> selectedFileTypes = getSelectedFileTypes();

        if (selectedFileTypes.isEmpty()){
            System.out.println("No file types selected");
            return;
        }

        FileOrganiser fileOrganiser = new FileOrganiser(path, selectedFileTypes);
        boolean organised = fileOrganiser.organiseFiles();

        if (organised){
            System.out.print("Files were organised");
        } else {
            System.out.println("Error organising files");
        }
    }

    @FXML
    protected void onAddToAutomationsButtonCLick(){
        String path = directoryTextField.getText();
        File directory = new File(path);
        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            return;
        }

        ArrayList<String> selectedFileTypes = getSelectedFileTypes();
        if (selectedFileTypes.isEmpty()){
            System.out.println("No file types selected");
            return;
        }


        automationManager.automateDirectory(path, selectedFileTypes);
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
        return selectedFileTypes;
    }
}