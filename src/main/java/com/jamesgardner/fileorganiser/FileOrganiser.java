package com.jamesgardner.fileorganiser;

import java.io.File;

public class FileOrganiser {
    //config
    private final String path;

    /**
     * Constructor
     */
    public FileOrganiser(){
        this.path = Config.defaultPath;
    }

    public void createFolders(){
        for (String folder : Config.fileTypes.keySet()) {
            File folderPath = new File(path, folder);
            if (!folderPath.exists()) {
                folderPath.mkdir();
            }
        }
    }

    public boolean organiseFiles(){
        return true;
    }
}
