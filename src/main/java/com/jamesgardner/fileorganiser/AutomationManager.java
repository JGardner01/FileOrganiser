package com.jamesgardner.fileorganiser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Automation Manager
 * @author James Gardner
 * Manages the automation of directories using DirectoryAutomators.
 * This class is responsible for starting and stopping automation threads for a give directory.
 */
public class AutomationManager {
    private final HashMap<String, Thread> automationThreads = new HashMap<>();  //hash map to store directory path, automation thread

    /**
     * Automate Directory
     * This function creates the automation thread to organise files in a given directory path.
     * @param path The path of the desired automated directory
     * @param directoryAutomator The DirectoryAutomator object responsible for organising files.
     * @return true if the directory is now automatically organised, else false.
     */
    public boolean automateDirectory(String path, DirectoryAutomator directoryAutomator){
        System.out.println("Automating " + path);   //debug message

        //check if the path is already being automated
        if (automationThreads.get(path) != null) {
            Alerts.errorAlert("Automating Directory", "Directory path is empty.");
            return false;
        }

        //check directory is valid
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            Alerts.errorAlert("Automating Directory", "The selected directory does not exist or is not a valid directory.");
            return false;
        }

        //create the automation thread
        Thread automatorThread = new Thread(directoryAutomator);
        automatorThread.setDaemon(true);
        automationThreads.put(path, automatorThread);               //add to hashmap
        automatorThread.start();
        System.out.println("Automator thread started on path" + path);
        return true;
    }


    /**
     * End Automation
     * This function ends the automatic organisation for a given directory.
     * The thread is interrupted and removed from the hashmap.
     * @param path The path of the directory where automation is to be ended.
     */
    public void endAutomation(String path){
       Thread automatorThread = automationThreads.get(path);
       if (automatorThread != null) {               //check there is an automation thread for the given directory
           //end automation thread
           automatorThread.interrupt();
           automationThreads.remove(path);
           System.out.println("Organised organisation removed for: " + path);
       } else {
           System.out.print("No automation found: " + path);
           Alerts.errorAlert( "Removing Automation", ("No automation found at " + path + "."));
       }
    }
}
