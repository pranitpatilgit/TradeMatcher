package com.pranitpatil.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    /**
     * Format number to comma separated string in format 000,000,000 with padding.
     */
    public static final String formatNumberToString(int number){
        StringBuilder builder = new StringBuilder();
        String numberString = NumberFormat.getNumberInstance(Locale.ENGLISH).format(number);

        for (int i = 0; i < 11 - numberString.length(); i++) {
            builder.append(" ");
        }
        builder.append(numberString);

        return builder.toString();
    }
    
    public static String addPaddingToNumber(int number){
        StringBuilder builder = new StringBuilder();
        String numberString = String.valueOf(number);

        for (int i = 0; i < 6 - numberString.length(); i++) {
            builder.append(" ");
        }
        builder.append(numberString);

        return builder.toString();
    }
}
