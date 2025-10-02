package org.pwss.quarantineManager_aes.util;

import java.io.File;
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

            if ((!File.separator.equals("/")) && (dottedPath.contains(":"))) {
                dottedPath = ReplaceDriveLetterSuffixColonForWindowsPath(dottedPath);
            }

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

            if ((!File.separator.equals("/")) && (dottedPath.contains(":"))) {
                dottedPath = ReplaceDriveLetterSuffixColonForWindowsPath(dottedPath);
            }

            if (dottedPath.endsWith(".")) {
                dottedPath = dottedPath.substring(0, dottedPath.length() - 1);
            }
            return dottedPath;
        } catch (Exception exception) {
            throw new GeneralSecurityException("Could not transform a Path string into a dotted format");
        }

    }

    /**
     * Replaces the drive letter suffix colon in Windows paths.
     *
     * <p>
     * This method is used internally to replace the colon character found after
     * the drive letter in Windows file paths with "_drive__". This ensures that
     * path separators are consistently replaced by dots when converting paths to
     * dotted strings.
     * </p>
     *
     * @param windowsPath The Windows path string where the colon needs to be
     *                    replaced.
     * @return A modified string with the drive letter suffix colon replaced.
     * @throws MalformedParametersException If there is no single colon or more than
     *                                      one colon in the input
     *                                      string.
     */
    private static final String ReplaceDriveLetterSuffixColonForWindowsPath(String windowsPath) {
        if (hasSingleColon(windowsPath)) {
            return windowsPath.replace(":", "_drive__");
        } else
            throw new MalformedParametersException("No colons or > 1 colons");
    }

    /**
     * Checks if the input string contains exactly one colon.
     *
     * <p>
     * This method iterates through each character of the string and counts how many
     * colons are present.
     * If more than one colon is found, it returns false. Otherwise, it checks if
     * exactly one colon was
     * found and returns true or false accordingly.
     * </p>
     *
     * @param input The string to be checked for a single colon.
     * @return True if the string contains exactly one colon; false otherwise.
     */
    private static final boolean hasSingleColon(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                count++;
                if (count > 1) {
                    return false;
                }
            }

            return count == 1;
        }

        return false;
    }

}