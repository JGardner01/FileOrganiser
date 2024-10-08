package com.jamesgardner.fileorganiser;

import com.jamesgardner.fileorganiser.enums.FileType;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

/**
 * Config
 * @author James Gardner
 * This class contains the static variable default path and the static hash map containing each file
 * type and their corresponding file type extensions.
 */
public class Config {

    //assign default path to downloads directory initially, until changed by the user
    public static String defaultPath = Paths.get(System.getProperty("user.home"), "Downloads").toString();

    //hash map containing common file type extensions mapped to the relevant file type
    public static final HashMap<FileType, List<String>> fileTypes = new HashMap<>(){{
        put(FileType.DOCUMENTS, new ArrayList<>(List.of(".pdf", ".doc", ".docm", ".docx", ".dot", ".dotx", ".rtf", ".txt", ".xls", ".xlsx", ".xlsm", ".xlt", ".xltx", ".xltm", ".ppt", ".pptx", ".pptm", ".pps", ".ppsx", ".ppsm", ".pot", ".potx", ".potm", ".csv", ".dif", ".accdb", ".accde", ".accdr", ".accdt", ".mdb", ".pub", ".xps", ".eml", ".msg", ".vsd", ".vsdx", ".vsdm", ".vss", ".vssm", ".vst", ".vstm", ".vstx")));
        put(FileType.IMAGES, new ArrayList<>(List.of(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tif", ".tiff", ".svg", ".psd", ".eps")));
        put(FileType.VIDEOS, new ArrayList<>(List.of(".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".mpeg", ".mpg", ".vob")));
        put(FileType.MUSIC, new ArrayList<>(List.of(".mp3", ".wav", ".flac", ".aac", ".m4a", ".mid", ".midi", ".wma")));
        put(FileType.APPLICATIONS, new ArrayList<>(List.of(".exe", ".msi", ".bat", ".jar")));
        put(FileType.ARCHIVES, new ArrayList<>(List.of(".zip", ".rar", ".cab", ".iso")));
        put(FileType.SYSTEM_FILES, new ArrayList<>(List.of(".bin", ".dll", ".sys", ".ini", ".tmp")));
    }};
}
