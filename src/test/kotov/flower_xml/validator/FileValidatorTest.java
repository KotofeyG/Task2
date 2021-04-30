package test.kotov.flower_xml.validator;

import com.kotov.flower_xml.validator.FileValidator;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class FileValidatorTest {
    @Test
    public void testIsValidWithNull() {
        assertFalse(FileValidator.isValid(null));
    }

    @Test
    public void testIsValidWithInvalidFilePath() {
        String invalidPathToFile = String.join(File.separator, "test_resources", "data", "nonexistentFile.xml");
        boolean actual = FileValidator.isValid(invalidPathToFile);
        assertFalse(actual);
    }

    @Test
    public void testIsValidWithEmptyFile() {
        String pathToEmptyFile = String.join(File.separator, "test_resources", "data", "emptyTestFile.xml");
        boolean actual = FileValidator.isValid(pathToEmptyFile);
        assertFalse(actual);
    }

    @Test
    public void testIsValidWithDirectory() {
        String pathToDirectory = String.join(File.separator, "test_resources", "testDirectory");
        boolean actual = FileValidator.isValid(pathToDirectory);
        assertFalse(actual);
    }
}