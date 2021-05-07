package test.kotov.flower_xml.validator;

import com.kotov.flower_xml.validator.FlowerXmlValidator;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class FlowerXmlValidatorTest {
    @Test
    public void testIsXmlFileValid() {
        boolean actual = FlowerXmlValidator
                .isXmlFileValid(String.join(File.separator, "test_resources", "testFlowers.xml")
                        , String.join(File.separator, "test_resources", "testSchema.xsd"));
        assertTrue(actual);
    }

    @Test
    public void testIsXmlFileValidWithInvalidXmlFile() {
        boolean actual = FlowerXmlValidator
                .isXmlFileValid(String.join(File.separator, "test_resources", "invalidFlowerData.xml")
                        , String.join(File.separator, "test_resources", "testSchema.xsd"));
        assertFalse(actual);
    }
}