package com.kodilla.StockTaking;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class FileUploaderLanguage {

    private final Map<String, String> languageMap = new TreeMap<>();

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
                languageMap.put(lineArray[1].trim(), lineArray[5].trim());

/*                System.out.print(lineArray[1].trim());
                System.out.println(lineArray[5].trim());*/
            }
        }
    }

    public Map<String, String> getLanguageMap() {
        return languageMap;
    }
}