package com.jamesgardner.fileorganiser;

import java.io.File;
import java.util.ArrayList;

public class AutomationManager {
    private final ArrayList<Thread> automationThreads = new ArrayList<>();

    public void automateDirectory(String path, ArrayList<String> selectedFileTypes){
        System.out.println("Automating " + path);

        File directory = new File(path);
        //check directory is valid
        if (!directory.exists() || !directory.isDirectory()){
            System.out.println("Invalid Directory");
            return;
        }

        if (selectedFileTypes.isEmpty()){
            System.out.println("No file types selected");
            return;
        }


        DirectoryAutomator directoryAutomator = new DirectoryAutomator(path, selectedFileTypes);
        Thread automatorThread = new Thread(directoryAutomator);
        automatorThread.setDaemon(true);
        automationThreads.add(automatorThread);
        automatorThread.start();
        System.out.println("Automator thread started on path" + path);
    }
}
