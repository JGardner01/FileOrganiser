package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML


    protected void onOrganiseButtonClick() {
        System.out.println("Organising " + Config.defaultPath);


        FileOrganiser fileOrganiser = new FileOrganiser();

        fileOrganiser.createFolders();

        boolean organised = fileOrganiser.organiseFiles();

        if (organised){
            System.out.print("Files were organised");
        } else {
            System.out.println("Error organising files");
        }

    }


}