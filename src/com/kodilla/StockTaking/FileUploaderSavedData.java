package com.kodilla.StockTaking;

import java.nio.file.Paths;
import java.util.Scanner;

public class FileUploaderSavedData {

    private final String teams;
    private final String wmStock;
    private final String pnData;
    private final String uoM;
    private final String language;
    private final String savedSettings;

    public FileUploaderSavedData(String path) {

        this.savedSettings = path;

        Scanner in = null;

        try {
            in = new Scanner(Paths.get(path));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.teams = in.nextLine();
        this.wmStock = in.nextLine();
        this.pnData = in.nextLine();
        this.uoM = in.nextLine();
        this.language = in.nextLine();
    }

    public String getPnData() {
        return pnData;
    }

    public String getUoM() {
        return uoM;
    }

    public String getLanguage() {
        return language;
    }

    public String getTeams() {
        return teams;
    }

    public String getWmStock() {
        return wmStock;
    }

    public String getSavedSettings() {
        return savedSettings;
    }
}
