package com.kotov.flower_xml.builder;

import static com.kotov.flower_xml.builder.FlowerBuilder.HYPHEN;
import static com.kotov.flower_xml.builder.FlowerBuilder.UNDERSCORE;

public enum FlowerXmlTag {
    FLOWERS,
    CUT_FLOWER,
    INDOOR_FLOWER,
    VENDOR_CODE,
    PLANTING_DATE,
    CUT_DATE,
    LEAVES,
    TITLE,
    ORIGIN,
    PETAL_COLOR,
    STEM_LENGTH,
    BUD_STATE,
    POT_TYPE,
    SOIL,
    WATERING,
    AVERAGE_TEMPERATURE,
    PHOTOPHILOUS,
    MULTIPLYING;

    public String getName() {
        return name().replaceAll(UNDERSCORE, HYPHEN).toLowerCase();
    }
}