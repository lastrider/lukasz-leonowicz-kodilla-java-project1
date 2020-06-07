package com.kodilla.StockTaking;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class FileUploaderWMAddresses {

    private final Map<String, Set<String>> addresses = new TreeMap<>();

    public void processFile(String path) {
        Scanner in = null;

        try {
            in = new Scanner(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            if (in.hasNext()) in.nextLine();
        }

        while (in.hasNext()) {
            String lineArray[] = in.nextLine().split("\\|");
            if (!lineArray[0].contains("-------")) {
                String address = lineArray[13].trim();
                String pn = lineArray[4].trim();

/*                System.out.print(address + " ");
                System.out.println(pn + " ");*/
                if (addresses.containsKey(address)) {
                    addresses.get(address).add(pn);
                } else {
                    Set<String> pnSet = new HashSet<>();
                    pnSet.add(pn);
                    addresses.put(address, pnSet);
                }
            }
        }
    }

    public Map<String, Set<String>> getAddresses() {
        return addresses;
    }

}