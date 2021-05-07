package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.entity.Flower;
import com.kotov.flower_xml.exception.FlowerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public abstract class FlowerBuilder {
    public static Logger logger = LogManager.getLogger();
    public static final boolean DEFAULT_LEAVES = true;
    public static final String HYPHEN = "-";
    public static final String UNDERSCORE = "_";
    protected Set<Flower> flowers;

    public FlowerBuilder() {
        flowers = new HashSet<>();
    }

    public FlowerBuilder(Set<Flower> flowers) {
        this.flowers = flowers;
    }

    public void setFlowers(Set<Flower> flowers) {
        this.flowers = flowers;
    }

    public Set<Flower> getFlowers() {
        return flowers;
    }

    public static String modifyIntoEnumName(String name) {
        return name.replaceAll(HYPHEN, UNDERSCORE).toUpperCase();
    }

    public abstract void buildFlowersFromXml(String pathToXmlFile) throws FlowerException;
}