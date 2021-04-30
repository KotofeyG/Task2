package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.entity.*;
import com.kotov.flower_xml.exception.FlowerException;
import com.kotov.flower_xml.validator.FileValidator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.time.LocalDate;

import static com.kotov.flower_xml.builder.FlowerXmlTag.*;

public class StreamStaxFlowerBuilder extends FlowerBuilder {
    private XMLInputFactory factory;
    private XMLStreamReader reader;

    public StreamStaxFlowerBuilder() {
        factory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildFlowersFromXml(String pathToFile) throws FlowerException {
        if (!FileValidator.isValid(pathToFile)) {
            throw new FlowerException("Wrong argument. Unable to read " + pathToFile);
        }
        String tagName;
        try (FileInputStream input = new FileInputStream(pathToFile)) {
            reader = factory.createXMLStreamReader(input);
            while (reader.hasNext()) {
                reader.next();
                if (reader.isStartElement()) {
                    tagName = reader.getLocalName();
                    if (tagName.equals(CUT_FLOWER.getName())) {
                        Flower flower = buildFlower(new CutFlower());
                        flowers.add(flower);
                    } else if (tagName.equals(INDOOR_FLOWER.getName())) {
                        Flower flower = buildFlower(new IndoorFlower());
                        flowers.add(flower);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new FlowerException("XML file reading error: ", e);
        }
    }

    private Flower buildFlower(Flower flower) throws XMLStreamException {
        setAttributes(flower);
        setDefaultAttribute(flower);

        String elementName;
        while (reader.hasNext()) {
            int elementType = reader.next();
            switch (elementType) {
                case XMLStreamConstants.START_ELEMENT -> {
                    elementName = reader.getLocalName();
                    FlowerXmlTag currentTag = FlowerXmlTag.valueOf(modifyIntoEnumName(elementName));
                    switch (currentTag) {
                        case TITLE -> {
                            String title = getElementData(reader);
                            flower.setTitle(title);
                        }
                        case ORIGIN -> {
                            Origin origin = Origin.valueOf(modifyIntoEnumName(getElementData(reader)));
                            flower.setOrigin(origin);
                        }
                        case PETAL_COLOR -> {
                            Color color = Color.valueOf(modifyIntoEnumName(getElementData(reader)));
                            flower.setPetalColor(color);
                        }
                        case STEM_LENGTH -> {
                            CutFlower temp = (CutFlower) flower;
                            double length = Double.parseDouble(getElementData(reader));
                            temp.setStemLength(length);
                        }
                        case BUD_STATE -> {
                            CutFlower temp = (CutFlower) flower;
                            BudState state = BudState.valueOf(modifyIntoEnumName(getElementData(reader)));
                            temp.setBudState(state);
                        }
                        case POT_TYPE -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            PotType type = PotType.valueOf(modifyIntoEnumName(getElementData(reader)));
                            temp.setPotType(type);
                        }
                        case SOIL -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            Soil soil = Soil.valueOf(modifyIntoEnumName(getElementData(reader)));
                            temp.setSoil(soil);
                        }
                        case WATERING -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            int watering = Integer.parseInt(getElementData(reader));
                            temp.setWatering(watering);
                        }
                        case AVERAGE_TEMPERATURE -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            double temperature = Double.parseDouble(getElementData(reader));
                            temp.setAverageTemperature(temperature);
                        }
                        case PHOTOPHILOUS -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            boolean photophilous = Boolean.parseBoolean(getElementData(reader));
                            temp.setPhotophilous(photophilous);
                        }
                        case MULTIPLYING -> {
                            IndoorFlower temp = (IndoorFlower) flower;
                            Multiplying multiplying = Multiplying.valueOf(modifyIntoEnumName(getElementData(reader)));
                            temp.setMultiplying(multiplying);
                        }
                        default -> throw new EnumConstantNotPresentException(currentTag.getDeclaringClass()
                                , currentTag.name());
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    elementName = reader.getLocalName();
                    if (elementName.equals(CUT_FLOWER.getName())
                            || elementName.equals(INDOOR_FLOWER.getName())) {
                        return flower;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <flower>");
    }

    private void setAttributes(Flower flower) {
        for (int index = 0; index < reader.getAttributeCount(); index++) {
            FlowerXmlTag currentTag = FlowerXmlTag.valueOf(modifyIntoEnumName(reader.getAttributeLocalName(index)));
            switch (currentTag) {
                case VENDOR_CODE -> {
                    String code = reader.getAttributeValue(index);
                    flower.setVendorCode(code);
                }
                case PLANTING_DATE -> {
                    LocalDate date = LocalDate.parse(reader.getAttributeValue(index));
                    flower.setPlantingDate(date);
                }
                case CUT_DATE -> {
                    CutFlower temp = (CutFlower) flower;
                    LocalDate date = LocalDate.parse(reader.getAttributeValue(index));
                    temp.setCutDate(date);
                }
                case LEAVES -> {
                    CutFlower temp = (CutFlower) flower;
                    boolean leaves = Boolean.parseBoolean(reader.getAttributeValue(index));
                    temp.setLeaves(leaves);
                }
                default -> throw new EnumConstantNotPresentException(currentTag.getDeclaringClass()
                        , currentTag.name());
            }
        }
    }

    private void setDefaultAttribute(Flower flower) {
        if (flower.getClass() == CutFlower.class) {
            String attrValue = reader.getAttributeValue(null, LEAVES.getName());
            if (attrValue == null) {
                CutFlower temp = (CutFlower) flower;
                temp.setLeaves(DEFAULT_LEAVES);
            }
        }
    }

    private String getElementData(XMLStreamReader reader) throws XMLStreamException {
        String data = null;
        if (reader.hasNext()) {
            reader.next();
            data = reader.getText();
        }
        return data;
    }
}