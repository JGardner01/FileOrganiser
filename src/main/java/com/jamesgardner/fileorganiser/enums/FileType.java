package com.jamesgardner.fileorganiser.enums;

/**
 * File Type
 * @author James Gardner
 * This enum defines the avaliable categories of files that can be used for organising files.
 */
public enum FileType {
    DOCUMENTS,
    IMAGES,
    VIDEOS,
    MUSIC,
    APPLICATIONS,
    ARCHIVES,
    SYSTEM_FILES;

    /**
     * To Formatted String
     * This function converts the enum name to a string where only the first letter is capitalised and '_' are replaced
     * by spaces.
     * @return The formatted string representation of the enum.
     */
    public String toFormattedString() {
        String string = this.name().toLowerCase();
        string = string.replace("_", " ");
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
