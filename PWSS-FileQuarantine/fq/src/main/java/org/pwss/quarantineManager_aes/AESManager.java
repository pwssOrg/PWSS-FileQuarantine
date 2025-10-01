package org.pwss.quarantineManager_aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.UUID;

/**
 * Manager class for performing AES encryption and decryption operations.
 */
final class AESManager {

     /**
     * Holds the instance count. The maximum instances allowed is 1.
     */
    private static int instanceCount = 0;

    /**
     * The algorithm used for encryption and decryption (AES).
     */
    private final String ALGORITHM = "AES";

    /**
     * The transformation used in the Cipher instance (AES/CBC/PKCS5Padding).
     */
    private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * The size of the initialization vector (IV) in bytes.
     */
    private final int IV_SIZE = 16;

    /**
     * The size of the key used for encryption and decryption in bits.
     */
    private final int KEY_SIZE = 256;

   AESManager(){
     instanceCount++;
        if (instanceCount > 1) {
            System.exit(2);
            // It is only allowed to have one instance of this class
   }
}



    /**
     * Generates a new AES secret key with the specified key size.
     *
     * @return A newly generated SecretKey object.
     * @throws Exception If an error occurs during key generation.
     */
    final SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    /**
     * Encrypts a file using AES encryption with the given secret key.
     *
     * @param input  The path to the input file that will be encrypted.
     * @param output The path to the output file where the encrypted data will be written.
     * @param key    The SecretKey used for encryption.
     * @throws Exception If an error occurs during encryption.
     */
    final void encryptFile(Path input, Path output, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        try (FileOutputStream fileOut = new FileOutputStream(output.toFile());
             CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)) {
            fileOut.write(iv);  // Save IV to start of file
            Files.copy(input, cipherOut);
        }
    }

    /**
     * Decrypts a file using AES decryption with the given secret key.
     *
     * @param input  The path to the input file that will be decrypted.
     * @param output The path to the output file where the decrypted data will be written.
     * @param key    The SecretKey used for decryption.
     * @throws Exception If an error occurs during decryption.
     */
    final void decryptFile(Path input, Path output, SecretKey key) throws Exception {
        try (FileInputStream fileIn = new FileInputStream(input.toFile())) {
            byte[] iv = new byte[IV_SIZE];
            if (fileIn.read(iv) != IV_SIZE) throw new IOException("Invalid IV length");

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            try (CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
                 FileOutputStream fileOut = new FileOutputStream(output.toFile())) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = cipherIn.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    /**
     * Saves the given secret key to a specified path.
     *
     * @param key      The SecretKey that will be saved.
     * @param keyPath  The Path where the key will be written.
     * @throws IOException If an error occurs while writing the key file.
     */
    final void saveKey(SecretKey key, Path keyPath) throws IOException {
        Files.write(keyPath, key.getEncoded());
    }

    /**
     * Loads a secret key from a specified path.
     *
     * @param keyPath The Path where the key is stored.
     * @return A SecretKeySpec created from the loaded key bytes.
     * @throws IOException If an error occurs while reading the key file.
     */
    final SecretKey loadKey(Path keyPath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(keyPath);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * Generates a unique name for storing keys.
     *
     * @return A string representing the unique name with ".key" extension.
     */
    final String generateUniqueKeyName() {
        return UUID.randomUUID().toString() + ExtensionConstant.KEY.getExtension();
    }
}
