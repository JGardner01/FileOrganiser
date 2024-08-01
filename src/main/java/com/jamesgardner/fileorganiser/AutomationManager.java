package com.jamesgardner.fileorganiser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AutomationManager {
    private final HashMap<String, Thread> automationThreads = new HashMap<>();

    public boolean automateDirectory(String path, DirectoryAutomator directoryAutomator){
        System.out.println("Automating " + path);

        if (automationThreads.get(path) != null) {
            Alerts.errorAlert("Automating Directory", "Directory path is empty.");
            return false;
        }

        File directory = new File(path);
        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            Alerts.errorAlert("Automating Directory", "The selected directory does not exist or is not a valid directory.");
            return false;
        }

        Thread automatorThread = new Thread(directoryAutomator);
        automatorThread.setDaemon(true);
        automationThreads.put(path, automatorThread);
        automatorThread.start();
        System.out.println("Automator thread started on path" + path);
        return true;
    }


    public void endAutomation(String path){
       Thread automatorThread = automationThreads.get(path);
       if (automatorThread != null) {
           automatorThread.interrupt();
           automationThreads.remove(path);
           System.out.println("Organised organisation removed for: " + path);
       } else {
           System.out.print("No automation found: " + path);
           Alerts.errorAlert( "Removing Automation", ("No automation found at " + path + "."));
       }
    }
}
