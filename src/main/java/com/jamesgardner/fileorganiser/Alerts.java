package com.jamesgardner.fileorganiser;

import javafx.scene.control.Alert;

/**
 * Alerts
 * @author James Gardner
 * This class provides static methods to display differnt types of alerts such as information or error messages.
 */
public class Alerts {

    //constants
    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";

    /**
     * Info Alert
     * This function calls the displayAlert function to create and display an information alert.
     * The title of the alert is always set to 'Success'.
     * @param header The header text of the alert window.
     * @param content The content text of the alert window.
     */
    public static void infoAlert(String header, String content){
        displayAlert(Alert.AlertType.INFORMATION, SUCCESS, header, content);
    }

    /**
     * Error Alert
     * This function calls the displayAlert function to create and display an error alert.
     * The title of the alert is always set to 'Error'.
     * @param header The header text of the alert window.
     * @param content The content text of the alert window.
     */
    public static void errorAlert(String header, String content){
        displayAlert(Alert.AlertType.ERROR, ERROR, header, content);
    }

    /**
     * Display Alert
     * This function creates and displays an alert with a specified type, title, header and content.
     * The alert waits for the user to close it before allowing the user to continue in the application.
     * @param alertType The type of alert to display (Information or Error).
     * @param title The title of the alert window.
     * @param header The header text of the alert window.
     * @param content The content text of the alert window.
     */
    private static void displayAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
