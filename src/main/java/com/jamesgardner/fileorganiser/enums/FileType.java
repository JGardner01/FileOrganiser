package com.jamesgardner.fileorganiser.enums;

public enum FileType {
    DOCUMENTS,
    IMAGES,
    VIDEOS,
    MUSIC,
    APPLICATIONS,
    ARCHIVES,
    SYSTEM_FILES;


    public String toFormattedString() {
        String string = this.name().toLowerCase();
        string = string.replace("_", " ");
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
