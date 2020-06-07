package com.kodilla.StockTaking;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileUploaderUnitsOfMeasure {

    private final Map<String, Set<String>> unitsOfMeasureMap = new HashMap<>();

    public void processFile(String path) {
        Scanner in = null;

        try {
            in = new Scanner(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++) {
            if (in.hasNext()) in.nextLine();
        }

        while (in.hasNext()) {
            String lineArray[] = in.nextLine().split("\\|");
            if (!lineArray[0].contains("-------")) {
                makeInput(lineArray[1].trim(), lineArray[4].trim(), lineArray[6].trim());
/*                System.out.print(lineArray[1] + " ");
                System.out.println(lineArray[4] + " ");*/
            }
        }
    }

    public Map<String, Set<String>> getUnitsOfMeasureMap() {
        return unitsOfMeasureMap;
    }

    private void makeInput(String index, String unit, String baseUnit) {
        if (unitsOfMeasureMap.containsKey(index)) {
            unitsOfMeasureMap.get(index).add(baseUnit);
        } else {
            Set<String> set = new TreeSet<>();
            set.add(unit);
            unitsOfMeasureMap.put(index, set);
        }
    }
}