package org.pwss.quarantineManager_aes;

import java.nio.file.Path;

 interface FileQuarantine {

    /**
     * Quarantines a file by encrypting it, generating and storing an AES key,
     * deleting the original file, and saving metadata.
     *
     * @param fileToQuarantine The path of the file to be quarantined.
     * @throws Exception If an error occurs during encryption or file operations.
     */
    void quarantine(Path fileToQuarantine) throws Exception;

    /**
     * Unquarantines a file by decrypting it using the corresponding key,
     * restoring the original file, deleting the encrypted file and its key,
     * and removing metadata.
     *
     * @param encryptedFileName The name of the encrypted (quarantined) file.
     * @throws Exception If an error occurs during decryption or file operations.
     */
    void unquarantine(String encryptedFileName) throws Exception;

}