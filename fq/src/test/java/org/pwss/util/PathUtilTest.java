package org.pwss.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.security.GeneralSecurityException;

import org.junit.jupiter.api.Test;
import org.pwss.quarantineManager_aes.util.PathUtil;

/**
 * Test class for {@link PathUtil} containing various test cases to ensure the
 * correctness of path conversion
 * methods.
 */
public class PathUtilTest {

    /**
     * Tests the transformation of Windows file paths into dotted strings.
     *
     * @throws GeneralSecurityException if there is a security-related issue during
     *                                  testing
     */
    @Test
    void testWindowsPathTransform() throws GeneralSecurityException {

        // Given
        String pathString = "C:\\Users\\JohnDoe\\Documents\\ab.txt";
        final String EXPECTED = "C_drive__.Users.JohnDoe.Documents.ab.txt";

        final String ACTUAL;

        // Windows System
        if (!File.separator.equals("/")) {

            // Then
            ACTUAL = PathUtil.convertPathToDottedString(pathString);

        }
        // Linux System
        else {
            String temp = pathString.replace("/", "\\");

            // Then
            ACTUAL = PathUtil.convertPathToDottedString(temp);
        }

        // Assertion
        assertEquals(EXPECTED, ACTUAL);
    }

    /**
     * Tests the transformation of Unix/Linux file paths into dotted strings.
     *
     * @throws GeneralSecurityException if there is a security-related issue during
     *                                  testing
     */
    @Test
    void testLinuxPathTransform() throws GeneralSecurityException {
        // Given
        String pathString = "/home/johndoe/documents/ab.txt";
        final String EXPECTED = ".home.johndoe.documents.ab.txt";

        final String ACTUAL;

        // Windows System
        if (!File.separator.equals("/")){

        // When Unix single slashes are replaced with Windows double backslashes
        // (avoiding PowerMock lib from 2020)
        String temp = pathString.replace("/", "\\");

        
        // Then
        ACTUAL = PathUtil.convertPathToDottedString(temp);
       
        }
         // Linux System
         else {

            // Then
            ACTUAL = PathUtil.convertPathToDottedString(pathString);

        }

        // Assertion
        assertEquals(EXPECTED, ACTUAL);
    }

}

