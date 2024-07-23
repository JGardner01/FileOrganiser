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

    private final AutomationManager automationManager = new AutomationManager();


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

        try{
            FileOrganiser fileOrganiser = new FileOrganiser(getPath(), getSelectedFileTypes());

            boolean organised = fileOrganiser.organiseFiles();

            if (organised){
                System.out.print("Files were organised");
            } else {
                System.out.println("Error organising files");
            }
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
    }

    @FXML
    protected void onAddToAutomationsButtonCLick(){
        try{
            automationManager.automateDirectory(getPath(), getSelectedFileTypes());
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
    }

    private String getPath(){
        String path = directoryTextField.getText();
        File directory = new File(path);
        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            throw new IllegalArgumentException("Invalid directory");
        }

        return path;
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
}