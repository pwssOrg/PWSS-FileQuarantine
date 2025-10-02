package org.pwss.quarantineManager_aes;

import javax.crypto.SecretKey;

import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

/**
 * This class provides functionality for quarantining (isolating) suspicious
 * files by encrypting them,
 * storing their keys separately, and tracking their metadata. It also supports
 * unquarantining the files
 * by decrypting them using their corresponding keys.
 */
public final class FileQuarantineManager implements FileQuarantine {

    /**
     * Logger instance used for logging messages within this manager.
     */
    private final org.slf4j.Logger log;

    /**
     * The directory where quarantined (encrypted) files will be stored.
     */
    private final String QUARANTINE_DIR = "quarantine";
    /**
     * The directory where the encryption keys for quarantined files are stored.
     */
    private final String KEY_DIR = "keys";

    /**
     * The properties file used to store metadata about quarantined files.
     */
    private final String METADATA_FILE = "quarantine_metadata.properties";

    /**
     * An instance of AESManager used for cryptographic operations within this
     * manager.
     */
    private final AESManager aesUtil;

    /**
     * Constructs a new instance of FileQuarantineManager.
     *
     * <p>
     * Note: This class is designed to ensure that only one instance of the nested
     * classes within FileQuarantineManager can exist at any given time. As such, if
     * this
     * class is the sole running instance, it guarantees that no other instances of
     * its
     * nested classes will be in operation concurrently as the application will
     * exist :)
     * </p>
     */
    public FileQuarantineManager() {

        aesUtil = new AESManager();
        this.log = LoggerFactory.getLogger(FileQuarantineManager.class);
    }

    @Override
    public final void quarantine(Path fileToQuarantine) throws Exception {
        Files.createDirectories(Paths.get(QUARANTINE_DIR));
        Files.createDirectories(Paths.get(KEY_DIR));

        String originalFileName = fileToQuarantine.getFileName().toString();

        String encryptedFileName = originalFileName + ExtensionConstant.ENC.getExtension();
        Path encryptedPath = Paths.get(QUARANTINE_DIR, encryptedFileName);

        // Generate and store AES key
        SecretKey key = aesUtil.generateKey();
        String keyFileName = aesUtil.generateUniqueKeyName();
        Path keyPath = Paths.get(KEY_DIR, keyFileName);
        aesUtil.saveKey(key, keyPath);

        // Encrypt file
        aesUtil.encryptFile(fileToQuarantine, encryptedPath, key);

        // Save metadata
        saveMetadata(encryptedFileName, fileToQuarantine.toAbsolutePath().toString(), keyPath.toString());

        // Delete original file (simulate quarantine)
        Files.delete(fileToQuarantine);

        log.info("File quarantined and encrypted: {}", encryptedFileName);
    }

    @Override
    public final void unquarantine(String encryptedFileName) throws Exception {
        Path encryptedPath = Paths.get(QUARANTINE_DIR, encryptedFileName);

        if (!Files.exists(encryptedPath)) {
            log.error("Encrypted file not found.");
            return;
        }

        // Load metadata
        Properties props = loadMetadata();

        String originalPathStr = props.getProperty(encryptedFileName + ExtensionConstant.ORIGINAL.getExtension());

        String keyPathStr = props.getProperty(encryptedFileName + ExtensionConstant.KEY.getExtension());

        if (originalPathStr == null || keyPathStr == null) {
            log.error("Metadata missing for: {}", encryptedFileName);
            return;
        }

        Path originalPath = Paths.get(originalPathStr);
        SecretKey key = aesUtil.loadKey(Paths.get(keyPathStr));

        // Decrypt
        aesUtil.decryptFile(encryptedPath, originalPath, key);

        // Cleanup
        Files.delete(encryptedPath);
        Files.delete(Paths.get(keyPathStr));
        removeMetadata(encryptedFileName);

        log.info("File unquarantined and decrypted to: {}", originalPath);
    }

    // ---------- Metadata Handling Methods ----------
    /**
     * Saves metadata about the quarantined file, including its original path and
     * key path.
     *
     * @param encryptedName The name of the encrypted (quarantined) file.
     * @param originalPath  The original path of the unencrypted file.
     * @param keyPath       The path where the encryption key is stored.
     * @throws IOException If an error occurs while writing to the metadata file.
     */
    private final void saveMetadata(String encryptedName, String originalPath, String keyPath) throws IOException {
        Properties props = loadMetadata();

        props.setProperty(encryptedName + ExtensionConstant.ORIGINAL.getExtension(), originalPath);

        props.setProperty(encryptedName + ExtensionConstant.KEY.getExtension(), keyPath);
        try (FileOutputStream fos = new FileOutputStream(METADATA_FILE)) {
            props.store(fos, null);
        }
    }

    /**
     * Loads metadata about quarantined files from the properties file.
     *
     * @return The Properties object containing the loaded metadata.
     * @throws IOException If an error occurs while reading the metadata file.
     */
    private final Properties loadMetadata() throws IOException {
        Properties props = new Properties();
        File metadataFile = new File(METADATA_FILE);
        if (metadataFile.exists()) {
            try (FileInputStream fis = new FileInputStream(metadataFile)) {
                props.load(fis);
            }
        }
        return props;
    }

    /**
     * Removes metadata about a quarantined file after it has been unquarantined.
     *
     * @param encryptedName The name of the encrypted (quarantined) file whose
     *                      metadata is to be removed.
     * @throws IOException If an error occurs while writing to the metadata file.
     */
    private final void removeMetadata(String encryptedName) throws IOException {
        Properties props = loadMetadata();

        props.remove(encryptedName + ExtensionConstant.ORIGINAL.getExtension());

        props.remove(encryptedName + ExtensionConstant.KEY.getExtension());
        try (FileOutputStream fos = new FileOutputStream(METADATA_FILE)) {
            props.store(fos, null);
        }
    }
}