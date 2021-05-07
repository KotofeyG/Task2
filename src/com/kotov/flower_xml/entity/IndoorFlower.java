package com.kotov.flower_xml.entity;

import java.time.LocalDate;

public class IndoorFlower extends Flower {
    private PotType potType;
    private Soil soil;
    private int watering;
    private double averageTemperature;
    private boolean photophilous;
    private Multiplying multiplying;

    public IndoorFlower() {
    }

    public IndoorFlower(String vendorCode, String name, Origin origin, LocalDate plantingDate, Color petalColor
            , PotType potType, Soil soil, int watering, double averageTemperature, boolean photophilous
            , Multiplying multiplying) {
        super(vendorCode, name, origin, plantingDate, petalColor);
        this.potType = potType;
        this.soil = soil;
        this.watering = watering;
        this.averageTemperature = averageTemperature;
        this.photophilous = photophilous;
        this.multiplying = multiplying;
    }

    public void setPotType(PotType potType) {
        this.potType = potType;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public void setWatering(int watering) {
        this.watering = watering;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public void setPhotophilous(boolean photophilous) {
        this.photophilous = photophilous;
    }

    public void setMultiplying(Multiplying multiplying) {
        this.multiplying = multiplying;
    }

    public PotType getPotType() {
        return potType;
    }

    public Soil getSoil() {
        return soil;
    }

    public int getWatering() {
        return watering;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public boolean isPhotophilous() {
        return photophilous;
    }

    public Multiplying getMultiplying() {
        return multiplying;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) {
            return false;
        }
        IndoorFlower other = (IndoorFlower) otherObject;
        if (potType == null) {
            if (other.potType != null) {
                return false;
            }
        } else if (!potType.equals(other.potType)) {
            return false;
        }
        if (soil == null) {
            if (other.soil != null) {
                return false;
            }
        } else if (!soil.equals(other.soil)) {
            return false;
        }
        if (watering != other.watering) {
            return false;
        }
        if (averageTemperature != other.averageTemperature) {
            return false;
        }
        if (photophilous != other.photophilous) {
            return false;
        }
        if (multiplying == null) {
            return other.multiplying == null;
        }
        return multiplying.equals(other.multiplying);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((potType == null) ? 0 : potType.hashCode());
        result = prime * result + ((soil == null) ? 0 : soil.hashCode());
        result = prime * result + Integer.hashCode(watering);
        result = prime * result + Double.hashCode(averageTemperature);
        result = prime * result + Boolean.hashCode(photophilous);
        result = prime * result + ((multiplying == null) ? 0 : multiplying.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("[ potType = ").append(potType);
        result.append(", soil = ").append(soil);
        result.append(", watering = ").append(watering);
        result.append(", averageTemperature = ").append(averageTemperature);
        result.append(", photophilous = ").append(photophilous);
        result.append(", multiplying = ").append(multiplying);
        result.append(" ]");
        return result.toString();
    }
}