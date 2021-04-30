package test.kotov.flower_xml.validator;

import com.kotov.flower_xml.validator.FlowerXmlValidator;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class FlowerXmlValidatorTest {
    @Test
    public void testIsValid() {
        boolean actual = FlowerXmlValidator
                .isValid(String.join(File.separator, "test_resources", "data", "testFlowers.xml")
                        , String.join(File.separator, "test_resources", "schema", "testSchema.xsd"));
        assertTrue(actual);
    }

    @Test
    public void testIsValidWithInvalidXmlFile() {
        boolean actual = FlowerXmlValidator
                .isValid(String.join(File.separator, "test_resources", "data", "invalidFlowerData.xml")
                        , String.join(File.separator, "test_resources", "schema", "testSchema.xsd"));
        assertFalse(actual);
    }
}