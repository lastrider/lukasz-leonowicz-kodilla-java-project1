package com.kodilla.StockTaking;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PartNumber {
    private final String partNumber;
    private final String basicUnitOfMeasure;
    private final String logisticsHandlingGroup;
    private final String descriptionInEnglish;
    private final String descriptionInPolish;
    private final Set<String> unitsOfMeasure;
    private final String storageLocation;

    public PartNumber(PNData pnData,String descriptionInPolish, Set<String> unitsOfMeasure) {
        this.partNumber = pnData.getPartNumber();
        this.basicUnitOfMeasure = pnData.getBasicUnitOfMeasure();
        this.logisticsHandlingGroup = pnData.getLogisticsHandlingGroup();
        this.descriptionInEnglish = pnData.getDescriptionInEnglish();
        this.descriptionInPolish = descriptionInPolish;
        this.unitsOfMeasure = unitsOfMeasure;
        this.storageLocation = pnData.getStorageLocation();
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getBasicUnitOfMeasure() {
        return basicUnitOfMeasure;
    }

    public String getLogisticsHandlingGroup() {
        return logisticsHandlingGroup;
    }

    public String getDescriptionInEnglish() {
        return descriptionInEnglish;
    }

    public String getDescriptionInPolish() {
        return descriptionInPolish;
    }

    public Set<String> getUnitsOfMeasure() {
        return unitsOfMeasure;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public List<String> getUnitsList() {
        return new ArrayList<>(unitsOfMeasure);
    }

}