package com.kotov.flower_xml.entity;

import java.time.LocalDate;

public class CutFlower extends Flower {
    private LocalDate cutDate;
    private double stemLength;
    private BudState budState;
    private boolean leaves;

    public CutFlower() {
    }

    public CutFlower(String vendorCode, String name, Origin origin, LocalDate plantingDate, Color petalColor
            , LocalDate cutDate, double stemLength, BudState budState, boolean leaves) {
        super(vendorCode, name, origin, plantingDate, petalColor);
        this.cutDate = cutDate;
        this.stemLength = stemLength;
        this.budState = budState;
        this.leaves = leaves;
    }

    public void setCutDate(LocalDate cutDate) {
        this.cutDate = cutDate;
    }

    public void setStemLength(double stemLength) {
        this.stemLength = stemLength;
    }

    public void setBudState(BudState budState) {
        this.budState = budState;
    }

    public void setLeaves(boolean leaves) {
        this.leaves = leaves;
    }

    public LocalDate getCutDate() {
        return cutDate;
    }

    public double getStemLength() {
        return stemLength;
    }

    public BudState getBudState() {
        return budState;
    }

    public boolean hasLeaves() {
        return leaves;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) {
            return false;
        }
        CutFlower other = (CutFlower) otherObject;
        if (cutDate == null) {
            if (other.cutDate != null) {
                return false;
            }
        } else if (!cutDate.equals(other.cutDate)) {
            return false;
        }
        if (stemLength != other.stemLength) {
            return false;
        }
        if (budState == null) {
            if (other.budState != null) {
                return false;
            }
        } else if (!budState.equals(other.budState)) {
            return false;
        }
        return leaves == other.leaves;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((cutDate == null) ? 0 : cutDate.hashCode());
        result = prime * result + Double.hashCode(stemLength);
        result = prime * result + ((budState == null) ? 0 : budState.hashCode());
        result = prime * result + Boolean.hashCode(leaves);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("[ cutDate = ").append(cutDate);
        result.append(", stemLength = ").append(stemLength);
        result.append(", budState = ").append(budState);
        result.append(", leaves = ").append(leaves);
        result.append(" ]");
        return result.toString();
    }
}