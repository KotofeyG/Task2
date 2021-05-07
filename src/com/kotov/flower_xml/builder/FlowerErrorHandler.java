package com.kotov.flower_xml.builder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class FlowerErrorHandler implements ErrorHandler {
    public static Logger logger = LogManager.getLogger();

    @Override
    public void warning(SAXParseException e) {
        logger.log(Level.WARN, findErrorPosition(e) + " - " + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) {
        logger.log(Level.ERROR, findErrorPosition(e) + " - " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) {
        logger.log(Level.FATAL, findErrorPosition(e) + " - " + e.getMessage());
    }

    private String findErrorPosition(SAXParseException e) {
        return "Line is " + e.getLineNumber() + ", column is " + e.getColumnNumber();
    }
}