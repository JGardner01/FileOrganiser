package com.jamesgardner.fileorganiser;

public class AppSettings {
    private static boolean runInBackground;
    private static boolean runAtStartUp;

    public AppSettings(){
        runInBackground = true;
        runAtStartUp = true;
    }

    public static boolean getRunInBackground(){
        return runInBackground;
    }

    public static void setRunInBackground(boolean backgroundSetting){
        runInBackground = backgroundSetting;
    }

    public static boolean getRunAtStartUp(){
        return runAtStartUp;
    }

    public static void setRunAtStartUp(boolean startUpSetting){
        runAtStartUp = startUpSetting;
    }

}
