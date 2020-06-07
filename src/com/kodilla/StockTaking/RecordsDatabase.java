package com.kodilla.StockTaking;

import java.util.*;

public class RecordsDatabase {

    List<Record> recordsList = new ArrayList<>();
    ArrayList<String> addressesList = new ArrayList<>();

    public void create(PNDatabase pnDatabase, FileUploaderSavedData savedData, String teamNumber) {

        Set<String> setOfAddresses = new HashSet<>();

        FileUploaderTeams teamsFile = new FileUploaderTeams();
        teamsFile.processFile(savedData.getTeams());
        Map<String, Set<String>> teams = teamsFile.getTeams();

        FileUploaderWMAddresses addressesFile = new FileUploaderWMAddresses();
        addressesFile.processFile(savedData.getWmStock());
        Map<String, Set<String>> addresses = addressesFile.getAddresses();

        for (String address : teams.get(teamNumber)) {
            if (addresses.containsKey(address)) {

                for (String partNumber : addresses.get(address)) {
                    Record record = new Record(address, partNumber, pnDatabase, false);
                    recordsList.add(record);
                    setOfAddresses.add(address);
                }
            }
        }
        recordsList.sort(Record::compareTo);
        addressesList.addAll(setOfAddresses);
        addressesList.sort(String::compareTo);
    }

    public List<Record> getRecordsList() {
        return recordsList;
    }

    public List<String> getStringRecordList(){
        List<String> list = new ArrayList<>();
        recordsList.forEach(record -> list.add(record.toString()));
        return list;
    }

    public boolean containAddress(String address) {
        return addressesList.contains(address);
    }

    public void addRecord(Record record) {
        recordsList.add(record);
    }

    public void remove(Record record) {
        recordsList.remove(record);
    }

    public boolean contains(Record record) {
        return recordsList.stream().anyMatch(record1 -> record1.isEqual(record));
    }
}