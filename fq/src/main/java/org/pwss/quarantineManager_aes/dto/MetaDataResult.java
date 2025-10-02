package org.pwss.quarantineManager_aes.dto;

/**
 * A record to hold the result of a metadata operation, indicating whether it
 * was successful
 * and storing the name of the key associated with that operation.
 *
 * <p>
 * This record is typically used in the context of file quarantine and
 * unquarantine operations,
 * where it provides information about the success or failure of such operations
 * along with the
 * relevant encryption key name.
 * </p>
 */
public record MetaDataResult(boolean succesful, String keyName) {
    /**
     * Constructs a new MetaDataResult.
     *
     * @param successful indicates whether the operation was successful.
     * @param keyName    the name of the key associated with this result.
     */
    public MetaDataResult {
        if (keyName == null || keyName.isBlank()) {
            throw new IllegalArgumentException("Key name must not be null or blank");
        }
    }
}
