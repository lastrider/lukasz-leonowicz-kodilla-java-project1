package com.kodilla.StockTaking;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileUploaderTeams {

    private final Map<String, Set<String>> teams = new TreeMap<>();

    public void processFile(String path) {
        Scanner in = null;

        try {
            in = new Scanner(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (in.hasNext()) {
            String[] lineArray = in.nextLine().split("\\|");
            makeInput(lineArray[1].trim(), lineArray[0].trim());
/*            System.out.print(lineArray[1] + " ");
            System.out.println(lineArray[0] + " ");*/

        }
    }

    private void makeInput(String address, String index) {
        if (teams.containsKey(address)) {
            teams.get(address).add(index);
        } else {
            Set<String> set = new HashSet<>();
            set.add(index);
            teams.put(address, set);
        }
    }

    public Map<String, Set<String>> getTeams() {
        return teams;
    }
}