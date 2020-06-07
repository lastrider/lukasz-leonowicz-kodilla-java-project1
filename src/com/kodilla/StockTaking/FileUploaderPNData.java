package com.kodilla.StockTaking;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class FileUploaderPNData {

    private final Map<String, PNData> PNSData = new TreeMap<>();

    public void processFile(String path) {

        Scanner in = null;
        try {
            in = new Scanner(Paths.get(path));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        for (int i = 0; i < 8; i++) {
            if (in.hasNext()) {
                in.nextLine();
            }
        }

        while (in.hasNext()) {

            String[] lineArray = in.nextLine().split("\\|");
            if (!lineArray[0].contains("-----")) {
                PNSData.put(lineArray[0].trim(), new PNData(
                        lineArray[0].trim(),
                        lineArray[1].trim(),
                        lineArray[2].trim(),
                        lineArray[3].trim(),
                        lineArray[4].trim()));

            }
        }
    }

    public Map<String, PNData> getPNSData() {
        return PNSData;
    }
}
