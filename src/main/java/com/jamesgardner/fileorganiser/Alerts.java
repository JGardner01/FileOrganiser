package com.jamesgardner.fileorganiser;

import javafx.scene.control.Alert;

public class Alerts {

    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";

    public static void infoAlert(String header, String content){
        displayAlert(Alert.AlertType.INFORMATION, SUCCESS, header, content);
    }

    public static void errorAlert(String header, String content){
        displayAlert(Alert.AlertType.ERROR, ERROR, header, content);
    }

    private static void displayAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
