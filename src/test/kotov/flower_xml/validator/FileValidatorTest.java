package test.kotov.flower_xml.validator;

import com.kotov.flower_xml.validator.FileValidator;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class FileValidatorTest {
    @Test
    public void testIsFileValid() {
        String invalidPathToFile = String.join(File.separator, "test_resources", "testFlowers.xml");
        boolean actual = FileValidator.isFileValid(invalidPathToFile);
        assertTrue(actual);
    }

    @Test
    public void testIsFileValidWithNull() {
        assertFalse(FileValidator.isFileValid(null));
    }

    @Test
    public void testIsFileValidWithInvalidFilePath() {
        String invalidPathToFile = String.join(File.separator, "test_resources", "nonexistentFile.xml");
        boolean actual = FileValidator.isFileValid(invalidPathToFile);
        assertFalse(actual);
    }

    @Test
    public void testIsFileValidWithEmptyFile() {
        String pathToEmptyFile = String.join(File.separator, "test_resources", "emptyTestFile.xml");
        boolean actual = FileValidator.isFileValid(pathToEmptyFile);
        assertFalse(actual);
    }

    @Test
    public void testIsFileValidWithDirectory() {
        String pathToDirectory = String.join(File.separator, "test_resources", "testDirectory");
        boolean actual = FileValidator.isFileValid(pathToDirectory);
        assertFalse(actual);
    }
}