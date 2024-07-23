package com.jamesgardner.fileorganiser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

public class FileOrganiser {
private final List<String> selectedFileTypes;

    /**
     * Constructor
     */
    public FileOrganiser(List<String> selectedFileTypes){
        this.selectedFileTypes = selectedFileTypes;
    }

    public void createFolders(String path){
        for (String folder : selectedFileTypes) {
            File folderPath = new File(path, folder);
            if (!folderPath.exists()) {
                folderPath.mkdir();
                //throw error if false mkdir
            }
        }
    }

    public boolean organiseFiles(String path){
        try{
            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files == null){
                System.out.println("No files found");
                return true;
            }

            for (File file : files){
                if (file.isFile()) {
                    String fileName = file.getName().toLowerCase();

                    for (String fileType : selectedFileTypes) {
                        List<String> extensions = Config.fileTypes.get(fileType);
                        for (String ext : extensions) {
                            if (fileName.endsWith(ext)) {
                                File destFolder = new File(path, fileType);
                                File currentFile = new File(destFolder, file.getName());
                                try {
                                    Files.move(file.toPath(), currentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException e) {
                                    System.err.println("Error moving file " + fileName + ": " + e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                }
            }
            return true;

        } catch (Exception e){
            System.err.println("Error organising files");
            return false;
        }
    }
}
