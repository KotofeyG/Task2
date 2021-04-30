package com.kotov.flower_xml.validator;

import com.kotov.flower_xml.builder.FlowerErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class FlowerXmlValidator {
    public static Logger logger = LogManager.getLogger();

    public static boolean isValid(String pathToXml, String pathToXsd) {
        if (!(FileValidator.isValid(pathToXml) && FileValidator.isValid(pathToXsd))) {
            logger.log(Level.INFO, "File " + pathToXml + " is not valid. Result is " + false);
            return false;
        }
        boolean result = true;
        String schemaNameSpace = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(schemaNameSpace);
        Source xmlSource = new StreamSource(pathToXml);
        Source xsdSource = new StreamSource(pathToXsd);
        try {
            Schema schema = factory.newSchema(xsdSource);
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new FlowerErrorHandler());
            validator.validate(xmlSource);
        } catch (SAXException e) {
            result = false;
        } catch (IOException e) {
            logger.log(Level.WARN, "XML file reading error: ", e);
            result = false;
        }
        logger.log(Level.INFO, "Result of " + pathToXml + " validation is " + result);
        return result;
    }
}