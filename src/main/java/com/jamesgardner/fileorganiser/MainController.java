package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class MainController {
    @FXML
    private TextField directoryTextField;


    public void initialize() {
        directoryTextField.setText(Config.defaultPath);
    }


    @FXML
    protected void onSelectDirectoryButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Organise");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        System.out.println(selectedDirectory);//debug
    }

    @FXML
    protected void onOrganiseButtonClick() {
        System.out.println("Organising " + directoryTextField.getText());

        String path = directoryTextField.getText();
        File directory = new File(path);

        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            return;
        }

        FileOrganiser fileOrganiser = new FileOrganiser();

        fileOrganiser.createFolders(path);

        boolean organised = fileOrganiser.organiseFiles(path);

        if (organised){
            System.out.print("Files were organised");
        } else {
            System.out.println("Error organising files");
        }

    }


}