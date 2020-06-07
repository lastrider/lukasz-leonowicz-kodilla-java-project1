package com.kodilla.StockTaking;

public class PNData {
    private final String partNumber;
    private final String basicUnitOfMeasure;
    private final String descriptionInEnglish;
    private final String storageLocation;
    private final String logisticsHandlingGroup;

    public PNData(String partNumber, String basicUnitOfMeasure, String descriptionInEnglish, String storageLocation, String logisticsHandlingGroup) {
        this.partNumber = partNumber;
        this.basicUnitOfMeasure = basicUnitOfMeasure;
        this.descriptionInEnglish = descriptionInEnglish;
        this.storageLocation = storageLocation;
        this.logisticsHandlingGroup = logisticsHandlingGroup;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getBasicUnitOfMeasure() {
        return basicUnitOfMeasure;
    }

    public String getDescriptionInEnglish() {
        return descriptionInEnglish;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getLogisticsHandlingGroup() {
        return logisticsHandlingGroup;
    }
}
