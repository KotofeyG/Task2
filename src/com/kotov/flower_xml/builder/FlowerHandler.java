package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.kotov.flower_xml.builder.FlowerBuilder.modifyIntoEnumName;
import static com.kotov.flower_xml.builder.FlowerXmlTag.*;

public class FlowerHandler extends DefaultHandler {
    public static Logger logger = LogManager.getLogger();
    private Flower currentFlower;
    private Set<Flower> flowers;
    private FlowerXmlTag currentXmlTag;
    private EnumSet<FlowerXmlTag> tagsWithData;

    public FlowerHandler() {
        flowers = new HashSet<>();
        tagsWithData = EnumSet.range(TITLE, MULTIPLYING);
    }

    public Set<Flower> getFlowers() {
        return flowers;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (CUT_FLOWER.getName().equals(qName)) {
            currentFlower = new CutFlower();
            logger.log(Level.DEBUG, "CutFlower is created");
            setFlowerAttributes(attributes);
            setDefaultFlowerAttribute(attributes);
        } else if (INDOOR_FLOWER.getName().equals(qName)) {
            currentFlower = new IndoorFlower();
            logger.log(Level.DEBUG, "IndoorFlower is created");
            setFlowerAttributes(attributes);
        } else {
            FlowerXmlTag temp = FlowerXmlTag.valueOf(modifyIntoEnumName(qName));
            if (tagsWithData.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case TITLE -> currentFlower.setTitle(data);
                case ORIGIN -> currentFlower.setOrigin(Origin.valueOf(modifyIntoEnumName(data)));
                case PETAL_COLOR -> currentFlower.setPetalColor(Color.valueOf(modifyIntoEnumName(data)));
                case STEM_LENGTH -> {
                    CutFlower temp = (CutFlower) currentFlower;
                    temp.setStemLength(Double.parseDouble(data));
                }
                case BUD_STATE -> {
                    CutFlower temp = (CutFlower) currentFlower;
                    temp.setBudState(BudState.valueOf(modifyIntoEnumName(data)));
                }
                case POT_TYPE -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setPotType(PotType.valueOf(modifyIntoEnumName(data)));
                }
                case SOIL -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setSoil(Soil.valueOf(modifyIntoEnumName(data)));
                }
                case WATERING -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setWatering(Integer.parseInt(data));
                }
                case AVERAGE_TEMPERATURE -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setAverageTemperature(Double.parseDouble(data));
                }
                case PHOTOPHILOUS -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setPhotophilous(Boolean.parseBoolean(data));
                }
                case MULTIPLYING -> {
                    IndoorFlower temp = (IndoorFlower) currentFlower;
                    temp.setMultiplying(Multiplying.valueOf(modifyIntoEnumName(data)));
                }
                default -> throw new EnumConstantNotPresentException(currentXmlTag.getDeclaringClass()
                        , currentXmlTag.name());
            }
            logger.log(Level.DEBUG, currentXmlTag + " for " + currentFlower.getClass().getSimpleName() + " with ID "
                    + currentFlower.getVendorCode() + " is " + data);
        }
        currentXmlTag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (CUT_FLOWER.getName().equals(qName) || INDOOR_FLOWER.getName().equals(qName)) {
            logger.log(Level.DEBUG, currentFlower.getClass().getSimpleName() + " with ID "
                    + currentFlower.getVendorCode() + " is filled and added into Set<Flower>");
            flowers.add(currentFlower);
        }
    }

    private void setFlowerAttributes(Attributes attrs) {
        for (int index = 0; index < attrs.getLength(); index++) {
            FlowerXmlTag currentAttribute = FlowerXmlTag.valueOf(modifyIntoEnumName(attrs.getQName(index)));
            switch (currentAttribute) {
                case VENDOR_CODE -> currentFlower.setVendorCode(attrs.getValue(index));
                case PLANTING_DATE -> currentFlower.setPlantingDate(LocalDate.parse(attrs.getValue(index)));
                case CUT_DATE -> {
                    CutFlower temp = (CutFlower) currentFlower;
                    temp.setCutDate(LocalDate.parse(attrs.getValue(index)));
                }
                case LEAVES -> {
                    CutFlower temp = (CutFlower) currentFlower;
                    temp.setLeaves(Boolean.parseBoolean(attrs.getValue(index)));
                }
                default -> throw new EnumConstantNotPresentException(currentAttribute.getDeclaringClass()
                        , currentAttribute.name());
            }
            logger.log(Level.DEBUG, "Attribute " + currentAttribute + " for " + currentFlower.getClass().getSimpleName()
                    + " with ID " + currentFlower.getVendorCode() + " is " + attrs.getValue(index));
        }
    }

    private void setDefaultFlowerAttribute(Attributes attrs) {
        String defaultAttrType = attrs.getType(LEAVES.getName());
        if (defaultAttrType == null) {
            CutFlower temp = (CutFlower) currentFlower;
            temp.setLeaves(FlowerBuilder.DEFAULT_LEAVES);
            logger.log(Level.DEBUG, "Attribute LEAVES is added by default for flower with ID "
                    + currentFlower.getVendorCode());
        }
    }
}