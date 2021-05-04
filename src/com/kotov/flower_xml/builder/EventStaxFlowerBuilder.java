package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.entity.*;
import com.kotov.flower_xml.exception.FlowerException;
import com.kotov.flower_xml.validator.FileValidator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.*;
import java.time.LocalDate;

import static com.kotov.flower_xml.builder.FlowerXmlTag.*;

public class EventStaxFlowerBuilder extends FlowerBuilder {
    private XMLInputFactory factory;
    private XMLEventReader reader;

    public EventStaxFlowerBuilder() {
        factory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildFlowersFromXml(String pathToFile) throws FlowerException {
        if (!FileValidator.isFileValid(pathToFile)) {
            throw new FlowerException("Wrong argument. Unable to read " + pathToFile);
        }
        Flower flower = null;
        try (InputStream inputStream = new FileInputStream(pathToFile)) {
            reader = factory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    FlowerXmlTag currentTag = FlowerXmlTag.valueOf(modifyIntoEnumName(startElement.getName()
                            .getLocalPart()));
                    switch (currentTag) {
                        case CUT_FLOWER, INDOOR_FLOWER -> {
                            flower = (currentTag == CUT_FLOWER) ? new CutFlower() : new IndoorFlower();
                            setAttributes(flower, startElement);
                        }
                        case TITLE -> {
                            event = reader.nextEvent();
                            String title = event.asCharacters().getData();
                            flower.setTitle(title);
                        }
                        case ORIGIN -> {
                            event = reader.nextEvent();
                            Origin origin = Origin.valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            flower.setOrigin(origin);
                        }
                        case PETAL_COLOR -> {
                            event = reader.nextEvent();
                            Color color = Color.valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            flower.setPetalColor(color);
                        }
                        case STEM_LENGTH -> {
                            event = reader.nextEvent();
                            CutFlower temp = (CutFlower) flower;
                            double length = Double.parseDouble(event.asCharacters().getData());
                            temp.setStemLength(length);
                        }
                        case BUD_STATE -> {
                            event = reader.nextEvent();
                            CutFlower temp = (CutFlower) flower;
                            BudState state = BudState.valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            temp.setBudState(state);
                        }
                        case POT_TYPE -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            PotType type = PotType.valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            temp.setPotType(type);
                        }
                        case SOIL -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            Soil soil = Soil.valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            temp.setSoil(soil);
                        }
                        case WATERING -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            int watering = Integer.parseInt(event.asCharacters().getData());
                            temp.setWatering(watering);
                        }
                        case AVERAGE_TEMPERATURE -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            double temperature = Double.parseDouble(event.asCharacters().getData());
                            temp.setAverageTemperature(temperature);
                        }
                        case PHOTOPHILOUS -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            boolean photophilous = Boolean.parseBoolean(event.asCharacters().getData());
                            temp.setPhotophilous(photophilous);
                        }
                        case MULTIPLYING -> {
                            event = reader.nextEvent();
                            IndoorFlower temp = (IndoorFlower) flower;
                            Multiplying multiplying = Multiplying
                                    .valueOf(modifyIntoEnumName(event.asCharacters().getData()));
                            temp.setMultiplying(multiplying);
                        }
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String elementName = endElement.getName().getLocalPart();
                    if (CUT_FLOWER.getName().equals(elementName)
                            || INDOOR_FLOWER.getName().equals(elementName)) {
                        flowers.add(flower);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new FlowerException("XML file reading error: ", e);
        }
    }

    private void setAttributes(Flower flower, StartElement startElement) {
        Attribute vendorCode = startElement.getAttributeByName(new QName(VENDOR_CODE.getName()));
        flower.setVendorCode(vendorCode.getValue());
        Attribute plantingDate = startElement.getAttributeByName(new QName(PLANTING_DATE.getName()));
        flower.setPlantingDate(LocalDate.parse(plantingDate.getValue()));

        if (flower.getClass() == CutFlower.class) {
            CutFlower temp = (CutFlower) flower;
            Attribute cutDate = startElement.getAttributeByName(new QName(CUT_DATE.getName()));
            temp.setCutDate(LocalDate.parse(cutDate.getValue()));
            Attribute leaves = startElement.getAttributeByName(new QName(LEAVES.getName()));
            if (leaves != null) {
                temp.setLeaves(Boolean.parseBoolean(leaves.getValue()));
            } else {
                temp.setLeaves(DEFAULT_LEAVES);
            }
        }
    }
}