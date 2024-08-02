package com.jamesgardner.fileorganiser;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * About Controller
 * @author James Gardner
 * This class is responsible for handling actions related to the About page of the application.
 * This includes opening external links in a web browser to my GitHub profile and portfolio website.
 */
public class AboutController {

    /**
     * On GitHub Link Click
     * This function is triggered when the user clicks the 'GitHub' hyperlink on the About page.
     * It attempts to open my GitHub profile in a web browser. If an error occurs, it prints the stack trace to console.
     */
    @FXML
    protected void onGitHubLinkClick()  {
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/JGardner01"));
        } catch  (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * On Portfolio Link Click
     * This function is triggered when the user clicks the 'Portfolio' hyperlink on the About page.
     * It attempts to open my portfolio website in a web browser. If an error occurs, it prints the stack trace to console.
     */
    @FXML
    protected void onPortfolioLinkClick(){
        try{
            Desktop.getDesktop().browse(new URI("https://jgardner01.github.io/"));
        } catch  (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}