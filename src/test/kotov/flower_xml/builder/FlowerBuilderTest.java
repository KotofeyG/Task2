package test.kotov.flower_xml.builder;

import com.kotov.flower_xml.builder.FlowerBuilder;
import com.kotov.flower_xml.builder.FlowerBuilderFactory;
import com.kotov.flower_xml.entity.*;
import com.kotov.flower_xml.exception.FlowerException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

public class FlowerBuilderTest {
    Set<Flower> expected;

    @BeforeClass
    public void createTestObjects() {
        expected = new HashSet<>();

        StringBuilder vendorCodeCF = new StringBuilder("A00000");
        StringBuilder titleCF = new StringBuilder("CutFlower");
        Origin originCF = Origin.EUROPE;
        LocalDate plantingDateCF = LocalDate.parse("2001-02-03");
        Color petalColorCF = Color.RED;
        LocalDate cutDate = LocalDate.parse("2002-04-05");
        double stemLength = 30;
        BudState budState = BudState.OPEN;
        boolean leaves = false;

        StringBuilder vendorCodeIF = new StringBuilder("B00000");
        StringBuilder titleIF = new StringBuilder("IndoorFlower");
        Origin originIF = Origin.ASIA;
        LocalDate plantingDateIF = LocalDate.parse("2003-06-07");
        Color petalColorIF = Color.WHITE;
        PotType potType = PotType.POT_HOLDER;
        Soil soil = Soil.TURF;
        int watering = 300;
        double averageTemperature = 15;
        boolean photophilous = true;
        Multiplying multiplying = Multiplying.SEED;

        for (int index = 1; index <= 8; index++) {
            CutFlower cutFlower = new CutFlower(vendorCodeCF.toString(), titleCF.toString(), originCF, plantingDateCF
                    , petalColorCF, cutDate, stemLength, budState, leaves);

            vendorCodeCF.replace(vendorCodeCF.length() - 1, vendorCodeCF.length(), Integer.toString(index));
            titleCF.append((char) (74 + index));
            originCF = Origin.values()[(index < 6) ? index : (index % 5)];
            plantingDateCF = plantingDateCF.plusDays(index << 1);
            petalColorCF = Color.values()[(index < 5) ? index : (index % 4)];
            cutDate = cutDate.plusDays(index << 1);
            stemLength += index;
            budState = BudState.values()[(index < 3) ? index : (index % 2)];
            leaves = !leaves;

            IndoorFlower indoorFlower = new IndoorFlower(vendorCodeIF.toString(), titleIF.toString(), originIF
                    , plantingDateIF, petalColorIF, potType, soil, watering, averageTemperature, photophilous
                    , multiplying);

            vendorCodeIF.replace(vendorCodeCF.length() - 1, vendorCodeCF.length(), Integer.toString(index));
            titleIF.append((char) (64 + index));
            originIF = Origin.values()[(index < 6) ? index : (index % 5)];
            plantingDateIF = plantingDateCF.plusDays(index << 1);
            petalColorIF = Color.values()[(index < 5) ? index : (index % 4)];
            potType = PotType.values()[(index < 3) ? index : (index % 2)];
            soil = Soil.values()[(index < 3) ? index : (index % 2)];
            watering += 50;
            averageTemperature += 2.5;
            photophilous = !photophilous;
            multiplying = Multiplying.values()[(index < 3) ? index : (index % 2)];

            expected.add(cutFlower);
            expected.add(indoorFlower);

        }
    }

    @DataProvider(name = "all-builders")
    public Object[][] builderDataProvider() throws FlowerException {
        String pathToFile = String.join(File.separator, "test_resources", "data", "testFlowers.xml");

        return new Object[][]{
                {FlowerBuilderFactory.createFlowerBuilder("DOM"), String.join(File.separator, pathToFile)},
                {FlowerBuilderFactory.createFlowerBuilder("SAX"), String.join(File.separator, pathToFile)},
                {FlowerBuilderFactory.createFlowerBuilder("STAX"), String.join(File.separator, pathToFile)},
                {FlowerBuilderFactory.createFlowerBuilder("ESTAX"), String.join(File.separator, pathToFile)}
        };
    }

    @Test(dataProvider = "all-builders")
    public void testFlowerBuilder(FlowerBuilder builder, String pathToFile) throws FlowerException {
        builder.buildFlowersFromXml(pathToFile);
        Set<Flower> actual = builder.getFlowers();
        assertEquals(actual, expected);
    }
}