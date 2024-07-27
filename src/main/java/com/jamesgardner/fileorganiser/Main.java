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

public class Main extends Application {

    private Stage primaryStage;
    private TrayIcon trayIcon;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("File Organiser");
        stage.setScene(scene);


        javafx.application.Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            //change to if allowed in the background with setting
            minimiseToTray();
        });


        stage.show();
    }

    private void minimiseToTray() {
        if (!SystemTray.isSupported()){
            System.out.println("System tray is not supported");
            return;
        }

        SystemTray systemTray = SystemTray.getSystemTray();

        trayIcon = new TrayIcon(new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB), "File Organiser"); //example icon for now TEMPORARY
        trayIcon.setToolTip("File Organiser");

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reopenApp();
            }
        });

        try{
            systemTray.add(trayIcon);
        } catch (AWTException e){
            System.out.println("Tray icon could not be added");
            return;
        }

        PopupMenu popupMenu = new PopupMenu();

        //Menu items
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reopenApp();
            }
        });

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

        primaryStage.hide();
    }

    private void reopenApp(){
        Platform.runLater(() -> {
            primaryStage.show();
            primaryStage.setIconified(false);
            primaryStage.requestFocus();
            SystemTray.getSystemTray().remove(trayIcon);
            trayIcon = null;
        });
    }

    public static void main(String[] args) {
        launch();
    }
}