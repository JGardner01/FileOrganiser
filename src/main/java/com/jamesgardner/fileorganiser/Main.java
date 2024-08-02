package com.jamesgardner.fileorganiser;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main
 * The main class is the main driver for file organiser application.
 * Sets up the Java fx application, manages the primary stage and handles the system tray background functionality.
 */
public class Main extends Application {
    private Stage primaryStage;
    private TrayIcon trayIcon;

    /**
     * Start
     * Loads the Main.fxml and sets it on the stage, starting the JavaFX application.
     * Handles on close requests.
     * @param stage The primary stage for the application.
     * @throws IOException If there is an issue loading the FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("File Organiser");
        stage.setScene(scene);

        javafx.application.Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            if (AppSettings.getRunInBackground()){
                minimiseToTray();
            } else{
                Platform.exit();
                System.exit(0);
            }
        });

        stage.show();
    }

    /**
     * Minimise To Tray
     * Minimises the application to the system tray.
     *
     */
    private void minimiseToTray() {
        //check system tray is supported
        if (!SystemTray.isSupported()){
            System.out.println("System tray is not supported");
            return; //return from function if not supported
        }

        //get system tray, assign icon and tool tip text
        SystemTray systemTray = SystemTray.getSystemTray();

        trayIcon = new TrayIcon(new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB), "File Organiser"); //example icon for now TEMPORARY
        trayIcon.setToolTip("File Organiser");

        //add action listener to reopen application
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reopenApp();
            }
        });

        //add tray icon to system tray
        try{
            systemTray.add(trayIcon);
        } catch (AWTException e){
            System.out.println("Tray icon could not be added");
            return;
        }

        //create new pop up menu
        PopupMenu popupMenu = new PopupMenu();

        //Menu items
        //menu item - open application
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reopenApp();
            }
        });

        //menu item - exit application
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        //add menu and items
        popupMenu.add(openItem);
        popupMenu.add(exitItem);
        trayIcon.setPopupMenu(popupMenu);

        primaryStage.hide();    //remove primary stage
    }

    /**
     * Reopen App
     * Reopens the application from the system tray.
     */
    private void reopenApp(){
        Platform.runLater(() -> {
            primaryStage.show();
            primaryStage.setIconified(false);
            primaryStage.requestFocus();
            SystemTray.getSystemTray().remove(trayIcon);
            trayIcon = null;
        });
    }

    /**
     * Main
     * The main function that launches javafx application.
     * @param args Command line args.
     */
    public static void main(String[] args) {
        launch();
    }
}