package org.pwss.quarantineManager_aes.util;

import java.lang.reflect.MalformedParametersException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.GeneralSecurityException;

/**
 * Utility class for converting file paths into dotted strings.
 *
 * <p>
 * This utility provides methods to transform both Path objects and String
 * representations
 * of file paths into a format where file separators are replaced with dots.
 * This is useful for
 * creating consistent, human-readable identifiers from file paths.
 * </p>
 */
public final class PathUtil {

    /**
     * Converts a Path object into a dotted string representation.
     *
     * <p>
     * This method replaces the default file separator characters in the path with
     * dots,
     * effectively converting the hierarchical file path structure into a single,
     * dot-separated
     * string. If the resulting string ends with a dot, that trailing character is
     * removed.
     * </p>
     *
     * @param path The Path object to be converted.
     * @return A string representation of the path where separators are replaced by
     *         dots.
     * @throws MalformedParametersException If an error occurs during conversion.
     */
    public static final String convertPathToDottedString(Path path) {
        try {
            String pathString = path.toString();
            String dottedPath = pathString.replace(FileSystems.getDefault().getSeparator(), ".");

            if (dottedPath.endsWith(".")) {
                dottedPath = dottedPath.substring(0, dottedPath.length() - 1);
            }

            return dottedPath;
        } catch (Exception exception) {
            throw new MalformedParametersException("Could not convert the Path object into a dot-separated string");
        }
    }

    /**
     * Converts a String representation of a path into a dotted string.
     *
     * <p>
     * This method replaces the default file separator characters in the string with
     * dots,
     * creating a consistent, dot-separated format for file paths. It also removes
     * any trailing
     * period from the resulting string. This can be useful when working with file
     * path strings
     * that need to be processed or displayed in a more readable way.
     * </p>
     *
     * @param path The String representation of the path to be converted.
     * @return A string where all file separators have been replaced by dots, with
     *         any trailing period removed.
     * @throws GeneralSecurityException If an error occurs during conversion.
     */
    public static final String convertPathToDottedString(String path) throws GeneralSecurityException {
        try {
            String dottedPath = path.replace(FileSystems.getDefault().getSeparator(), ".");
            if (dottedPath.endsWith(".")) {
                dottedPath = dottedPath.substring(0, dottedPath.length() - 1);
            }
            return dottedPath;
        } catch (Exception exception) {
            throw new GeneralSecurityException("Could not transform a Path string into a dotted format");
        }

    }
}
