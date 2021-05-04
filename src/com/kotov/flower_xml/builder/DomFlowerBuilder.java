package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.entity.*;
import com.kotov.flower_xml.exception.FlowerException;
import com.kotov.flower_xml.validator.FileValidator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

import static com.kotov.flower_xml.builder.FlowerXmlTag.*;

public class DomFlowerBuilder extends FlowerBuilder {
    private static final int FIRST_ITEM = 0;
    private DocumentBuilder builder;

    public DomFlowerBuilder() throws FlowerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new FlowerException("Unable to create DocumentBuilder object: ", e);
        }
    }

    @Override
    public void buildFlowersFromXml(String pathToFile) throws FlowerException {
        if (!FileValidator.isFileValid(pathToFile)) {
            throw new FlowerException("Wrong argument. Unable to read " + pathToFile);
        }
        Document document;
        try {
            document = builder.parse(pathToFile);
            Element root = document.getDocumentElement();
            NodeList cutFlowerList = root.getElementsByTagName(CUT_FLOWER.getName());
            for (int index = 0; index < cutFlowerList.getLength(); index++) {
                Element cutFlowerElement = (Element) cutFlowerList.item(index);
                Flower flower = createFlower(cutFlowerElement);
                flowers.add(flower);
            }
            NodeList indoorFlowerList = root.getElementsByTagName(INDOOR_FLOWER.getName());
            for (int index = 0; index < indoorFlowerList.getLength(); index++) {
                Element indoorFlowerElement = (Element) indoorFlowerList.item(index);
                Flower flower = createFlower(indoorFlowerElement);
                flowers.add(flower);
            }

        } catch (IOException | SAXException e) {
            throw new FlowerException("Unable to parse content of given XML document: " + e);
        }
    }

    private Flower createFlower(Element currentFlower) {
        Flower flower = null;
        if (CUT_FLOWER.getName().equals(currentFlower.getTagName())) {
            flower = new CutFlower();
        } else if (INDOOR_FLOWER.getName().equals(currentFlower.getTagName())) {
            flower = new IndoorFlower();
        }
        String code = currentFlower.getAttribute(VENDOR_CODE.getName());
        flower.setVendorCode(code);

        LocalDate plantingDate = LocalDate.parse(currentFlower.getAttribute(PLANTING_DATE.getName()));
        flower.setPlantingDate(plantingDate);

        String title = getElementDataContent(currentFlower, TITLE.getName());
        flower.setTitle(title);

        Origin origin = Origin.valueOf(modifyIntoEnumName(getElementDataContent(currentFlower, ORIGIN.getName())));
        flower.setOrigin(origin);

        Color color = Color.valueOf(modifyIntoEnumName(getElementDataContent(currentFlower, PETAL_COLOR.getName())));
        flower.setPetalColor(color);

        if (flower.getClass() == CutFlower.class) {
            CutFlower temp = (CutFlower) flower;
            LocalDate cutDate = LocalDate.parse(currentFlower.getAttribute(CUT_DATE.getName()));
            temp.setCutDate(cutDate);
            if (currentFlower.hasAttribute(LEAVES.getName())) {
                boolean leaves = Boolean.parseBoolean(currentFlower.getAttribute(LEAVES.getName()));
                temp.setLeaves(leaves);
            } else {
                temp.setLeaves(DEFAULT_LEAVES);
            }
            double length = Double.parseDouble(getElementDataContent(currentFlower, STEM_LENGTH.getName()));
            temp.setStemLength(length);

            BudState state = BudState
                    .valueOf(modifyIntoEnumName(getElementDataContent(currentFlower, BUD_STATE.getName())));
            temp.setBudState(state);
        } else if (flower.getClass() == IndoorFlower.class) {
            IndoorFlower temp = (IndoorFlower) flower;
            PotType type = PotType.valueOf(modifyIntoEnumName(getElementDataContent(currentFlower
                    , POT_TYPE.getName())));
            temp.setPotType(type);

            Soil soil = Soil.valueOf(modifyIntoEnumName(getElementDataContent(currentFlower, SOIL.getName())));
            temp.setSoil(soil);

            int watering = Integer.parseInt(modifyIntoEnumName(getElementDataContent(currentFlower
                    , WATERING.getName())));
            temp.setWatering(watering);

            double temperature = Double.parseDouble(modifyIntoEnumName(getElementDataContent(currentFlower
                    , AVERAGE_TEMPERATURE.getName())));
            temp.setAverageTemperature(temperature);

            boolean photophilous = Boolean.parseBoolean(modifyIntoEnumName(getElementDataContent(currentFlower
                    , PHOTOPHILOUS.getName())));
            temp.setPhotophilous(photophilous);

            Multiplying multiplying = Multiplying.valueOf(modifyIntoEnumName(getElementDataContent(currentFlower
                    , MULTIPLYING.getName())));
            temp.setMultiplying(multiplying);
        }
        return flower;
    }

    private String getElementDataContent(Element currentFlower, String elementName) {
        NodeList nodeList = currentFlower.getElementsByTagName(elementName);
        Node node = nodeList.item(FIRST_ITEM);
        String data = node.getTextContent();
        return data;
    }
}