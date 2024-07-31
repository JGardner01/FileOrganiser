package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.FileType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ManageFileExtensionsController {

    @FXML
    private ComboBox<String> fileTypeComboBox;

    @FXML
    private ListView<BorderPane> extensionListView;

    @FXML
    private TextField extensionTextField;

    private ObservableList<BorderPane> extensionList;


    public void initialize(){
        ArrayList<String> fileTypesList = new ArrayList<>();
        //get correct order do manual
        fileTypesList.add(FileType.DOCUMENTS.toFormattedString());
        fileTypesList.add(FileType.IMAGES.toFormattedString());
        fileTypesList.add(FileType.VIDEOS.toFormattedString());
        fileTypesList.add(FileType.MUSIC.toFormattedString());
        fileTypesList.add(FileType.APPLICATIONS.toFormattedString());
        fileTypesList.add(FileType.ARCHIVES.toFormattedString());
        fileTypesList.add(FileType.SYSTEM_FILES.toFormattedString());
        fileTypeComboBox.setItems(FXCollections.observableArrayList(fileTypesList));
    }

    @FXML
    protected void onFileTypeSelected(){
        String selectedFileType = fileTypeComboBox.getValue();
        extensionList = FXCollections.observableArrayList();
        List<String> extensions = Config.fileTypes.get(getSelectedFileType(selectedFileType));
        for (String extension : extensions){
            extensionList.add(createExtensionItem(extension));
        }
        extensionListView.setItems(extensionList);
    }

    private BorderPane createExtensionItem(String extension){
        BorderPane borderPane = new BorderPane();
        Label extensionLabel = new Label(extension);
        Button removeButton = new Button("Remove");

        removeButton.setOnAction(event -> {
            extensionList.remove(borderPane);
            Config.fileTypes.get(getSelectedFileType(fileTypeComboBox.getValue())).remove(extension);
        });

        borderPane.setLeft(extensionLabel);
        borderPane.setRight(removeButton);
        return borderPane;
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
                List<String> extensions = Config.fileTypes.get(getSelectedFileType(selectedFileType));
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
        for (Map.Entry<FileType, List<String>> entry : Config.fileTypes.entrySet()){
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

    private FileType getSelectedFileType(String selectedFileType){
        return FileType.values()[fileTypeComboBox.getItems().indexOf(selectedFileType)];
    }
}
