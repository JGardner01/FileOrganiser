package com.jamesgardner.fileorganiser;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;

public class Config {

    public static String defaultPath = Paths.get(System.getProperty("user.home"), "Downloads").toString();

    public static final HashMap<String, List<String>> fileTypes = new HashMap<>(){{
        put("Documents", new ArrayList<>(List.of(".pdf", ".doc", ".docm", ".docx", ".dot", ".dotx", ".rtf", ".txt", ".xls", ".xlsx", ".xlsm", ".xlt", ".xltx", ".xltm", ".ppt", ".pptx", ".pptm", ".pps", ".ppsx", ".ppsm", ".pot", ".potx", ".potm", ".csv", ".dif", ".accdb", ".accde", ".accdr", ".accdt", ".mdb", ".pub", ".xps", ".eml", ".msg", ".vsd", ".vsdx", ".vsdm", ".vss", ".vssm", ".vst", ".vstm", ".vstx")));
        put("Images", new ArrayList<>(List.of(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tif", ".tiff", ".svg", ".psd", ".eps")));
        put("Videos", new ArrayList<>(List.of(".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".mpeg", ".mpg", ".vob")));
        put("Music", new ArrayList<>(List.of(".mp3", ".wav", ".flac", ".aac", ".m4a", ".mid", ".midi", ".wma")));
        put("Applications", new ArrayList<>(List.of(".exe", ".msi", ".bat", ".jar")));
        put("Archives", new ArrayList<>(List.of(".zip", ".rar", ".cab", ".iso")));
        put("System Files", new ArrayList<>(List.of(".bin", ".dll", ".sys", ".ini", ".tmp")));
    }};
}
