package org.pwss.quarantineManager_aes;

/**
 * Enum representing different file extensions used in the application.
 */
enum ExtensionConstant {
   /**
     * Represents the ".key" extension.
     */
    KEY(".key"),

    /**
     * Represents the ".original" extension.
     */
    ORIGINAL(".original"),

    /**
     * Represents the ".enc" extension.
     */
    ENC(".enc");

    /**
     * The string representation of the file extension associated with this enum constant.
     */
    private final String extension;


 /**
     * Constructs an ExtensionConstant with the specified string value.
     *
     * @param extension The string representation of the file extension.
     */
    ExtensionConstant(String extension) {
        this.extension = extension;
    }

    /**
     * Gets the string representation of the file extension.
     *
     * @return The string representing the file extension.
     */
    final String getExtension() {
        return extension;
    }
}