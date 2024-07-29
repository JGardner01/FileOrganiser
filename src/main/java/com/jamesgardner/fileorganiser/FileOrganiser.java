package com.jamesgardner.fileorganiser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class FileOrganiser {
    private String path;
    private String mode;
    private List<String> selectedFileTypes;
    private String dateFrequency;

    /**
     * Constructor
     */
    public FileOrganiser(String path, List<String> selectedFileTypes){
        this.path = path;
        this.mode = "fileType";
        this.selectedFileTypes = selectedFileTypes;

        createFolders();
    }

    public FileOrganiser(String path, String dateFrequency){
        this.path = path;
        this.mode = "date";
        this.dateFrequency = dateFrequency;
    }

    public void createFolders(){
        for (String folder : selectedFileTypes) {
            File folderPath = new File(path, folder);
            if (!folderPath.exists()) {
                folderPath.mkdir();
                //throw error if false mkdir
            }
        }
    }

    public boolean organiseFiles(){
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

                    if (mode.equals("fileType")){
                        for (String folder : selectedFileTypes) {
                            List<String> extensions = Config.fileTypes.get(folder);
                            for (String ext : extensions) {
                                if (fileName.endsWith(ext)) {
                                    File destFolder = new File(path, folder);
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
                    } else if (mode.equals("date")) {
                        FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                        LocalDateTime dateTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());

                        String folderName;
                        if (dateFrequency.equals("Yearly")){
                            folderName = String.valueOf(dateTime.getYear());
                        } else if (dateFrequency.equals("Monthly")) {
                            folderName = dateTime.getYear() + "/" + String.format("%02d", dateTime.getMonthValue());
                        } else {
                            throw new Exception("Could not find date frequency");
                        }

                        File folderPath = new File(path, folderName);
                        if (!folderPath.exists()){
                            folderPath.mkdir();
                        }

                        File currentFile = new File(folderPath, file.getName());
                        try {
                            Files.move(file.toPath(), currentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e){
                            System.err.println("Error moving file");
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
