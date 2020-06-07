package com.kodilla.StockTaking;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PNDatabase {

    private final Map<String, PartNumber> partNumberDataBase = new TreeMap<>();

    public void create(FileUploaderSavedData savedData) {

        FileUploaderLanguage languageFile = new FileUploaderLanguage();
        languageFile.processFile(savedData.getLanguage());
        Map<String, String> languageMap = languageFile.getLanguageMap();

        FileUploaderUnitsOfMeasure unitsOfMeasureFile = new FileUploaderUnitsOfMeasure();
        unitsOfMeasureFile.processFile(savedData.getUoM());
        Map<String, Set<String>> unitsMap = unitsOfMeasureFile.getUnitsOfMeasureMap();

        FileUploaderPNData pnData = new FileUploaderPNData();
        pnData.processFile(savedData.getPnData());

        for (Map.Entry<String, PNData> item : pnData.getPNSData().entrySet()) {

            String pn = item.getKey();
            String language = "";
            Set<String> uom = new HashSet<>();

            if (languageMap.containsKey(pn)) {
                language = languageMap.get(pn);
            }

            if (unitsMap.containsKey(pn)) {
                uom = unitsMap.get(pn);
            }

            PartNumber partNumber = new PartNumber(item.getValue(), language, uom);
            partNumberDataBase.put(pn, partNumber);
        }
    }

    public PartNumber getPartNumberData(String partNumber) {
        return partNumberDataBase.get(partNumber);
    }

    public boolean containsPN(String pn) {
        return (partNumberDataBase.containsKey(pn));
    }

}

