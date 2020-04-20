package com.jlyang.hadoop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String toLowerCase(String origin) {
        return origin.toLowerCase();
    }

    public static boolean isNumeric(String origin) {
        if (origin == null || origin.trim().equals("")) {
            return false;
        }
        final String regex = "(^[1-9][0-9]*)|(^[1-9][0-9]*\\.[0]+)";
        return match(origin, regex);
    }

    private static boolean match(String origin, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(origin);
        return isNum.matches();
    }
}
