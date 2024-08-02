package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.DateFrequency;
import com.jamesgardner.fileorganiser.enums.FileType;

import java.io.File;
import java.nio.file.*;
import java.util.List;


/**
 * Directory Automator
 * @author James Gardner
 * This class implements Runnable to automate the organisation of files in a given directory.
 * It monitors the directory for new or modified files using a watch service and then organises them based on the users
 * given criteria.
 */
public class DirectoryAutomator implements  Runnable{
    private final String path;
    private final FileOrganiser fileOrganiser;

    /**
     * Constructor
     * Creates a DirectoryAutomator with a given path and specific file types to organise files by.
     * Mode organise by file type. Completes initial organisation of files.
     * @param path The directory path to be monitored and organised.
     * @param selectedFileTypes The list of file types to organise the files into.
     */
    public DirectoryAutomator(String path, List<FileType> selectedFileTypes){
        //assign variables
        this.path = path;
        this.fileOrganiser = new FileOrganiser(path, selectedFileTypes);

        //complete initial organisation of directory
        this.fileOrganiser.organiseFiles();
    }

    /**
     * Constructor
     * Creates a DirectoryAutomator with a give path and specific date frequency to organise files by.
     * Mode organise by date. Completes initial organisation of files.
     * @param path The directory path to be monitored and organised.
     * @param dateFrequency The list of file types to organise the files into.
     */
    public DirectoryAutomator(String path, DateFrequency dateFrequency){
        //assign variables
        this.path = path;
        this.fileOrganiser = new FileOrganiser(path, dateFrequency);

        //complete initial organisation of directory
        this.fileOrganiser.organiseFiles();
    }

    /**
     * Run
     * Runs the directory automation process. A watch service is set up to monitor the directory for new or modified
     * files and organises the directory when these events are detected.
     */
    public void run(){
        try(WatchService watchService = FileSystems.getDefault().newWatchService()){    //create watch service object
            Path directory = Paths.get(path);
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);   //register trigger event types with watch service
            System.out.println("Watch service active in directory: " + path);

            while(true){
                //create watch key
                WatchKey watchKey;
                try{
                    watchKey = watchService.take();
                } catch (Exception e){
                    System.out.println("Exception " + e);
                    return;
                }

                for (WatchEvent<?> event : watchKey.pollEvents()){
                    WatchEvent.Kind<?> kind = event.kind();

                    //handle overflow
                    if(kind == StandardWatchEventKinds.OVERFLOW){
                        continue;
                    }

                    //process new or modified files
                    WatchEvent<Path> eve = (WatchEvent<Path>) event;
                    Path fileName = eve.context();
                    File newFile = directory.resolve(fileName).toFile();

                    if(newFile.isFile()){
                        System.out.println("Detected new file: " + newFile.getAbsolutePath());
                        Thread.sleep(10);   //small delay to ensure file is ready for processing
                        if (!fileOrganiser.organiseFiles()){
                            //retry after delay if initial organisation fails due to file still moving
                            Thread.sleep(1000);
                        }
                    }
                }

                //reset the watch key and break if it is no longer valid
                boolean valid = watchKey.reset();
                if(!valid){
                    break;
                }

            }
        } catch (Exception e){
            //handle error setting up
            System.out.println("Error setting up directory watcher: " + e.getMessage());
            Alerts.errorAlert("Automating Directory", ("Error occurred setting up directory watcher: " + e.getMessage() + "."));
        }
    }
}
