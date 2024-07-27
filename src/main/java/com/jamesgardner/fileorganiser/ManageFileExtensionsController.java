package com.jamesgardner.fileorganiser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManageFileExtensionsController {

    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private ListView<HBox> extensionListView;

    @FXML
    private TextField extensionTextField;

    private ObservableList<HBox> extensionList;


    public void initialize(){
        fileTypeComboBox.setItems(FXCollections.observableArrayList(Config.fileTypes.keySet()));
    }

    @FXML
    protected void onFileTypeSelected(){
        String selectedFileType = fileTypeComboBox.getValue();
        extensionList = FXCollections.observableArrayList();
        List<String> extensions = Config.fileTypes.get(selectedFileType);
        for (String extension : extensions){
            extensionList.add(createExtensionItem(extension));
        }
        extensionListView.setItems(extensionList);
    }

    private HBox createExtensionItem(String extension){
        HBox hbox = new HBox();
        Label extensionLabel = new Label(extension);
        Button removeButton = new Button("Delete");

        removeButton.setOnAction(event -> {
            extensionList.remove(hbox);
            Config.fileTypes.get(fileTypeComboBox.getValue()).remove(extension);
        });

        hbox.getChildren().addAll(extensionLabel, removeButton);
        return hbox;
    }

    @FXML
    protected void onAddButtonClick(){
        String selectedFileType = fileTypeComboBox.getValue();
        String newExtension = extensionTextField.getText();

        if (!newExtension.isEmpty() && !newExtension.startsWith(".")){
            newExtension = "." + newExtension;
        }

        if (selectedFileType != null && !newExtension.isEmpty()){
            if (!newExtension.matches("^\\.[A-Za-z0-9._-]+$")){
                System.out.println("Extension not valid");
                return;
            }

            if (!checkExtensionExists(newExtension)){
                List<String> extensions = Config.fileTypes.get(selectedFileType);
                if (!extensions.contains(newExtension)){
                    extensions.add(newExtension);
                    extensionList.add(createExtensionItem(newExtension));
                    extensionTextField.clear();
                }
            } else{
                System.out.println("Extension already exists");
            }
        } else{
            System.out.println("No file type selected or no extension entered");
        }
    }

    private boolean checkExtensionExists(String newExtension){
        for (Map.Entry<String, List<String>> entry : Config.fileTypes.entrySet()){
            for (String extension : entry.getValue()){
                if (extension.equals(newExtension)){
                    System.out.println(newExtension + " exists in config");
                    return true;
                }
            }
        }
        return false;
    }


    @FXML
    protected void onCloseButtonClick(){
        Stage stage = (Stage) extensionTextField.getScene().getWindow();
        stage.close();
    }
}
