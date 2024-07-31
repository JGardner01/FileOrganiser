package com.jamesgardner.fileorganiser;

public enum FileType {
    DOCUMENTS,
    IMAGES,
    VIDEOS,
    MUSIC,
    APPLICATIONS,
    ARCHIVES,
    SYSTEM_FILES;


    public String toFormattedString() {
        String lowerCaseName = this.name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }

}
