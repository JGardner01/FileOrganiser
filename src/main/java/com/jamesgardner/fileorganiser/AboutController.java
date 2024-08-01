package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {
    @FXML
    protected void onGitHubLinkClick()  {
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/JGardner01"));
        } catch  (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onPortfolioLinkClick(){
        try{
            Desktop.getDesktop().browse(new URI("https://jgardner01.github.io/"));
        } catch  (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
