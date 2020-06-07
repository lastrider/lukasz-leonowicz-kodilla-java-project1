package com.kodilla.StockTaking;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Record implements Comparable<Record> {

    private final StringProperty storageLocation;
    private final StringProperty partNumber;
    private final StringProperty basicUnitOfMeasure;
    private final StringProperty address;
    private final StringProperty logisticsHandlingGroup;
    private final StringProperty descriptionInEnglish;
    private final StringProperty descriptionInPolish;
    private final ChoiceBox<String> unitOfMeasure;
    private final boolean createByUser;
    private TextField count;
    private String remarks;

    public Record(String address, String partNumber, PNDatabase pnDatabase, boolean createByUser) {
        PartNumber pn = pnDatabase.getPartNumberData(partNumber);
        this.storageLocation = new SimpleStringProperty(pn.getStorageLocation());
        this.partNumber = new SimpleStringProperty(pn.getPartNumber());

        TextField numbers = new TextField();
        numbers.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,10}([\\,]\\d{0,2})?")) ? change : null));
        this.count = numbers;

        this.basicUnitOfMeasure = new SimpleStringProperty(pn.getBasicUnitOfMeasure());

        ChoiceBox<String> units = new ChoiceBox<>();
        units.getItems().addAll(pn.getUnitsList());
        units.setFocusTraversable(false);
        units.setValue(pn.getBasicUnitOfMeasure());
        this.unitOfMeasure = units;

        this.address = new SimpleStringProperty(address);
        this.logisticsHandlingGroup = new SimpleStringProperty(pn.getLogisticsHandlingGroup());
        this.descriptionInEnglish = new SimpleStringProperty(pn.getDescriptionInEnglish());
        this.descriptionInPolish = new SimpleStringProperty(pn.getDescriptionInPolish());
        this.createByUser = createByUser;
        this.remarks = "";
    }


    public boolean isCreateByUser() {
        return createByUser;
    }

    public String getStorageLocation() {
        return storageLocation.get();
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation.set(storageLocation);
    }

    public StringProperty storageLocationProperty() {
        return storageLocation;
    }

    public String getPartNumber() {
        return partNumber.get();
    }

    public void setPartNumber(String partNumber) {
        this.partNumber.set(partNumber);
    }

    public StringProperty partNumberProperty() {
        return partNumber;
    }

    public TextField getCount() {
        return count;
    }

    public void setCount(TextField count) {
        this.count = count;
    }

    public ChoiceBox<String> getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getBasicUnitOfMeasure() {
        return basicUnitOfMeasure.get();
    }

    public void setBasicUnitOfMeasure(String basicUnitOfMeasure) {
        this.basicUnitOfMeasure.set(basicUnitOfMeasure);
    }

    public StringProperty basicUnitOfMeasureProperty() {
        return basicUnitOfMeasure;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getLogisticsHandlingGroup() {
        return logisticsHandlingGroup.get();
    }

    public void setLogisticsHandlingGroup(String logisticsHandlingGroup) {
        this.logisticsHandlingGroup.set(logisticsHandlingGroup);
    }

    public StringProperty logisticsHandlingGroupProperty() {
        return logisticsHandlingGroup;
    }

    public String getDescriptionInEnglish() {
        return descriptionInEnglish.get();
    }

    public void setDescriptionInEnglish(String descriptionInEnglish) {
        this.descriptionInEnglish.set(descriptionInEnglish);
    }

    public StringProperty descriptionInEnglishProperty() {
        return descriptionInEnglish;
    }

    public String getDescriptionInPolish() {
        return descriptionInPolish.get();
    }

    public void setDescriptionInPolish(String descriptionInPolish) {
        this.descriptionInPolish.set(descriptionInPolish);
    }

    public StringProperty descriptionInPolishProperty() {
        return descriptionInPolish;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Override
    public int compareTo(Record record) {
        return this.getAddress().compareTo(record.getAddress());
    }

    public boolean isEqual(Record record) {
        return (this.getPartNumber().equals(record.getPartNumber()) && this.getAddress().equals(record.getAddress()));
    }

    @Override
    public String toString() {
        return
                storageLocation.getValue() + "|" +
                        partNumber.getValue() +"|" +
                        count.getText() +"|" +
                        unitOfMeasure.getValue() +"|" +
                        basicUnitOfMeasure.getValue() +"|" +
                        address.getValue() +"|" +
                        logisticsHandlingGroup.getValue() +"|" +
                        descriptionInEnglish.getValue() +"|" +
                        descriptionInPolish.getValue() +"|" +
                        remarks;
    }
}
