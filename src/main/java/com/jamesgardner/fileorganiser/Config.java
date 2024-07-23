package com.jamesgardner.fileorganiser;

import java.util.HashMap;
import java.util.List;

public class Config {
    public static String defaultPath = System.getProperty("user.dir");

    public static final HashMap<String, List<String>> fileTypes = new HashMap<>(){{
        put("Documents", List.of(".pdf", ".doc", ".docm", ".docx", ".dot", ".dotx", ".rtf", ".txt", ".xls", ".xlsx", ".xlsm", ".xlt", ".xltx", ".xltm", ".ppt", ".pptx", ".pptm", ".pps", ".ppsx", ".ppsm", ".pot", ".potx", ".potm", ".csv", ".dif", ".accdb", ".accde", ".accdr", ".accdt", ".mdb", ".pub", ".xps", ".eml", ".msg", ".vsd", ".vsdx", ".vsdm", ".vss", ".vssm", ".vst", ".vstm", ".vstx"));
        put("Images", List.of(".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tif", ".tiff", ".svg", ".psd", ".eps"));
        put("Videos", List.of(".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".mpeg", ".mpg", ".vob"));
        put("Music", List.of(".mp3", ".wav", ".flac", ".aac", ".m4a", ".mid", ".midi", ".wma"));
        put("Applications", List.of(".exe", ".msi", ".bat", ".jar"));
        put("Archives", List.of(".zip", ".rar", ".cab", ".iso"));
        put("System Files", List.of(".bin", ".dll", ".sys", ".ini", ".tmp"));
    }};
}
