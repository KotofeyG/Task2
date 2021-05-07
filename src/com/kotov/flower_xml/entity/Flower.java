package com.kotov.flower_xml.entity;

import java.time.LocalDate;

public abstract class Flower {
    private String vendorCode;
    private String title;
    private Origin origin;
    private LocalDate plantingDate;
    private Color petalColor;

    public Flower() {
    }

    public Flower(String vendorCode, String title, Origin origin, LocalDate plantingDate, Color petalColor) {
        this.vendorCode = vendorCode;
        this.title = title;
        this.origin = origin;
        this.plantingDate = plantingDate;
        this.petalColor = petalColor;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }

    public void setPetalColor(Color petalColor) {
        this.petalColor = petalColor;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getTitle() {
        return title;
    }

    public Origin getOrigin() {
        return origin;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public Color getPetalColor() {
        return petalColor;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        Flower other = (Flower) otherObject;
        if (vendorCode == null) {
            if (other.vendorCode != null) {
                return false;
            }
        } else if (!vendorCode.equals(other.vendorCode)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        if (origin == null) {
            if (other.origin != null) {
                return false;
            }
        } else if (!origin.equals(other.origin)) {
            return false;
        }
        if (plantingDate == null) {
            if (other.plantingDate != null) {
                return false;
            }
        } else if (!plantingDate.equals(other.plantingDate)) {
            return false;
        }
        if (petalColor == null) {
            return other.petalColor == null;
        }
        return petalColor.equals(other.petalColor);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vendorCode == null) ? 0 : vendorCode.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + ((plantingDate == null) ? 0 : plantingDate.hashCode());
        result = prime * result + ((petalColor == null) ? 0 : petalColor.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName());
        result.append("[ vendorCode = ").append(vendorCode);
        result.append(", title = ").append(title);
        result.append(", origin = ").append(origin);
        result.append(", plantingDate = ").append(plantingDate);
        result.append(", petalColor = ").append(petalColor);
        result.append(" ]");
        return result.toString();
    }
}