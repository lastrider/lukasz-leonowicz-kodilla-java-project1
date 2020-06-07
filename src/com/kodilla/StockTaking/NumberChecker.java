package com.kodilla.StockTaking;

public class NumberChecker {

    static boolean intCheck(String string) {
        try {
            int number = Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean doubleCheck(String string) {
        boolean result = false;
        double number;
        try {
            number = Double.parseDouble(string);
            result = true;
        } catch (Exception e) {

        }

        return result;
    }
}
