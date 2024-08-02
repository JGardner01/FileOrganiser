package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.DateFrequency;
import com.jamesgardner.fileorganiser.enums.FileType;
import com.jamesgardner.fileorganiser.enums.OrganiseMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * File Organiser
 * This class is responsible for creating folders and organising files in a specified directory either by file type or
 * creation date.
 */
public class FileOrganiser {
    private String path;
    private OrganiseMode mode;
    private List<FileType> selectedFileTypes;
    private DateFrequency dateFrequency;

    /**
     * Constructor
     * Creates a file organiser that organises files by file type.
     * @param path The path to the directory to organise.
     * @param selectedFileTypes The list of file types to be organised.
     */
    public FileOrganiser(String path, List<FileType> selectedFileTypes){
        //assign variables
        this.path = path;
        this.mode = OrganiseMode.FILE_TYPE;
        this.selectedFileTypes = selectedFileTypes;

        createFolders();    //create folders based on the selected file types
    }

    /**
     * Constructor
     * Creates a file organiser that organises files by date frequency.
     * @param path The path to the directory to organise.
     * @param dateFrequency The date frequency (Monthly, Yearly)
     */
    public FileOrganiser(String path, DateFrequency dateFrequency){
        //assign variables
        this.path = path;
        this.mode = OrganiseMode.DATE;
        this.dateFrequency = dateFrequency;
    }

    /**
     * Create Folders
     * Creates folders in the given directory for each selected file type.
     */
    public void createFolders(){
        for (FileType folder : selectedFileTypes) {
            File folderPath = new File(path, folder.toFormattedString());
            if (!folderPath.exists()) {
                folderPath.mkdir();
            }
        }
    }

    /**
     * Organise Files
     * This function organises files in the given directory to the selected mode (file type or date).
     * Organises files into folders for file types.
     * Creates directories for the relevant dates and organises files into the directories.
     * @return true if the files were organised successfully, else false.
     */
    public boolean organiseFiles(){
        try{
            //create list of files
            File directory = new File(path);
            File[] files = directory.listFiles();

            //check there are files in the list
            if (files == null){
                System.out.println("No files found");
                return true;
            }

            //go through each file in the list
            for (File file : files){
                if (file.isFile()) {    //check file not directory
                    String fileName = file.getName().toLowerCase();

                    //organise file type mode
                    if (mode.equals(OrganiseMode.FILE_TYPE)){
                        //go through each file type
                        for (FileType folder : selectedFileTypes) {
                            //go through each file extension
                            List<String> extensions = Config.fileTypes.get(folder);
                            for (String ext : extensions) {
                                //if file is the extension
                                if (fileName.endsWith(ext)) {
                                    //move file to directory
                                    File destFolder = new File(path, folder.toFormattedString());
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
                    // organise date mode
                    } else if (mode.equals(OrganiseMode.DATE)) {
                        //get creation date
                        FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                        LocalDateTime dateTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());

                        //create directory
                        String folderName;
                        if (dateFrequency.equals(DateFrequency.YEARLY)){
                            folderName = String.valueOf(dateTime.getYear());
                        } else if (dateFrequency.equals(DateFrequency.MONTHLY)) {
                            folderName = dateTime.getYear() + "-" + String.format("%02d", dateTime.getMonthValue());
                        } else {
                            throw new Exception("Could not find date frequency");
                        }

                        //create directory for date
                        File folderPath = new File(path, folderName);
                        if (!folderPath.exists()){
                            folderPath.mkdir();
                        }

                        //move file to directory
                        File currentFile = new File(folderPath, file.getName());
                        try {
                            Files.move(file.toPath(), currentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e){
                            System.err.println("Error moving file");

                        }

                    }

                }
            }
            return true; //successful organisation

        } catch (Exception e){
            System.err.println("Error organising files");
            return false;
        }
    }
}
