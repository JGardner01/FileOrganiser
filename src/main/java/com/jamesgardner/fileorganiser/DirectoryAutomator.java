package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.DateFrequency;
import com.jamesgardner.fileorganiser.enums.FileType;

import java.io.File;
import java.nio.file.*;
import java.util.List;


public class DirectoryAutomator implements  Runnable{
    private final String path;
    private final FileOrganiser fileOrganiser;

    public DirectoryAutomator(String path, List<FileType> selectedFileTypes){
        this.path = path;
        this.fileOrganiser = new FileOrganiser(path, selectedFileTypes);

        this.fileOrganiser.organiseFiles();
    }

    public DirectoryAutomator(String path, DateFrequency dateFrequency){
        this.path = path;
        this.fileOrganiser = new FileOrganiser(path, dateFrequency);

        this.fileOrganiser.organiseFiles();
    }

    public void run(){
        try(WatchService watchService = FileSystems.getDefault().newWatchService()){
            Path directory = Paths.get(path);
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
            System.out.println("Watch service active in directory: " + path);

            while(true){
                WatchKey watchKey;
                try{
                    watchKey = watchService.take();
                } catch (Exception e){
                    System.out.println("Exception " + e);
                    return;
                }

                for (WatchEvent<?> event : watchKey.pollEvents()){
                    WatchEvent.Kind<?> kind = event.kind();

                    if(kind == StandardWatchEventKinds.OVERFLOW){
                        continue;
                    }

                    WatchEvent<Path> eve = (WatchEvent<Path>) event;
                    Path fileName = eve.context();
                    File newFile = directory.resolve(fileName).toFile();

                    if(newFile.isFile()){
                        System.out.println("Detected new file: " + newFile.getAbsolutePath());
                        Thread.sleep(10);
                        if (!fileOrganiser.organiseFiles()){
                            Thread.sleep(1000);
                        }
                    }
                }

                boolean valid = watchKey.reset();
                if(!valid){
                    break;
                }

            }
        } catch (Exception e){
            System.out.println("Error setting up directory watcher: " + e.getMessage());
            Alerts.errorAlert("Automating Directory", ("Error occurred setting up directory watcher: " + e.getMessage() + "."));
        }
    }
}
