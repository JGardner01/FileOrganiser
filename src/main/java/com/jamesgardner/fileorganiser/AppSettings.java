package com.jamesgardner.fileorganiser;

/**
 * App Settings
 * @author James Gardner
 */
public class AppSettings {
    //setting variables
    private static boolean runInBackground;
    private static boolean runAtStartUp;

    /**
     * Constructor
     * By default, the application is set to run in the background and at start up.
     */
    public AppSettings(){
        runInBackground = true;
        runAtStartUp = true;
    }

    /**
     * Get Run In Background
     * Gets the current setting for running the application in the background.
     * @return true if application is set to run in background, else false.
     */
    public static boolean getRunInBackground(){
        return runInBackground;
    }

    /**
     * Set Run In Background
     * Sets the setting for whether the application should run in the background.
     * @param backgroundSetting true if the run in background check box was clicked, else false.
     */
    public static void setRunInBackground(boolean backgroundSetting){
        runInBackground = backgroundSetting;
    }

    /**
     * Get Run At Start Up
     * Gets the current setting for running the application at startup.
     * @return true if application is set to run at startup, else false.
     */
    public static boolean getRunAtStartUp(){
        return runAtStartUp;
    }

    /**
     * Set Run At Start Up
     * Sets the setting for whether the application should run at start up.
     * @param startUpSetting true if the run at start up check box was clicked, else false.
     */
    public static void setRunAtStartUp(boolean startUpSetting){
        runAtStartUp = startUpSetting;
    }

}
