package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.exception.FlowerException;
import com.kotov.flower_xml.validator.FileValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxFlowerBuilder extends FlowerBuilder {
    public static Logger logger = LogManager.getLogger();
    private XMLReader reader;
    private FlowerHandler handler;

    public SaxFlowerBuilder() throws FlowerException {
        handler = new FlowerHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            throw new FlowerException("Enable to create SAX reader: ", e);
        }
        reader.setErrorHandler(new FlowerErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildFlowersFromXml(String pathToFile) throws FlowerException {
        if (!FileValidator.isFileValid(pathToFile)) {
            throw new FlowerException("Wrong argument. Unable to read " + pathToFile);
        }
        try {
            reader.parse(pathToFile);
        } catch (IOException | SAXException e) {
            throw new FlowerException("Unable to parse XML file " + pathToFile + ": ", e);
        }
        flowers.addAll(handler.getFlowers());
        logger.log(Level.INFO, "Construction of flower objects by SaxFlowerBuilder is successful");
    }
}