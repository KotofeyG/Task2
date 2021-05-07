package com.kotov.flower_xml.builder;

import com.kotov.flower_xml.exception.FlowerException;

public class FlowerBuilderFactory {
    private enum ParserType {
        DOM, SAX, STAX, ESTAX
    }

    private FlowerBuilderFactory() {
    }

    public static FlowerBuilder createFlowerBuilder(String parserType) throws FlowerException {
        ParserType type = ParserType.valueOf(parserType.toUpperCase());
        return switch (type) {
            case DOM -> new DomFlowerBuilder();
            case SAX -> new SaxFlowerBuilder();
            case STAX -> new StreamStaxFlowerBuilder();
            case ESTAX -> new EventStaxFlowerBuilder();
            default -> throw new FlowerException("Parser with name " + parserType + " is not found");
        };
    }
}