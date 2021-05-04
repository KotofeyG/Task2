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
    public void setUpTestFlowers() {
        expected = new HashSet<>();
        Flower flower1 = new CutFlower("A00001", "Rosa Aida", Origin.EUROPE, LocalDate.parse("2020-02-01")
                , Color.PINK, LocalDate.parse("2021-04-27"), 70, BudState.OPEN, true);
        Flower flower2 = new CutFlower("A00002", "Rosa Duftwolke", Origin.EUROPE, LocalDate.parse("2020-05-10")
                , Color.RED, LocalDate.parse("2021-04-27"), 72, BudState.HALF_OPEN, false);
        Flower flower3 = new CutFlower("A00003", "Rosa Captain Harry Stebbings", Origin.NORTH_AMERICA
                , LocalDate.parse("2019-07-09"), Color.RED, LocalDate.parse("2021-04-27"), 60, BudState.OPEN, true);
        Flower flower4 = new IndoorFlower("B00001", "Anigozanthos", Origin.AUSTRALIA, LocalDate.parse("2017-01-10")
                , Color.RED, PotType.SOLID, Soil.TURF, 1200, 25, true, Multiplying.SEED);
        Flower flower5 = new IndoorFlower("B00002", "Hippeastrum", Origin.SOUTH_AMERICA, LocalDate.parse("2016-12-30")
                , Color.PINK, PotType.DRAINAGE, Soil.HUMUS, 750, 22, true, Multiplying.SEED);
        Flower flower6 = new IndoorFlower("B00003", "Viola odorata", Origin.ASIA, LocalDate.parse("2017-06-08")
                , Color.BLUE, PotType.SOLID, Soil.SODDY_PODZOLIC, 350, 21, false, Multiplying.LEAF);
        expected.add(flower1);
        expected.add(flower2);
        expected.add(flower3);
        expected.add(flower4);
        expected.add(flower5);
        expected.add(flower6);
    }

    @DataProvider(name = "all-builders")
    public Object[][] builderDataProvider() throws FlowerException {
        String pathToFile = String.join(File.separator, "test_resources", "testFlowers.xml");
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