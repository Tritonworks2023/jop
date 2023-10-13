package com.triton.johnsonapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunction {
    public static String nullPointer(String value) {
        if (value != null && !value.equalsIgnoreCase("null")
                && !value.equalsIgnoreCase("") && !value.isEmpty()) {
            return value;
        } else {
            return "";
        }
    }

    public static Boolean nullPointerValidator(String value) {
        if (value != null && !value.equalsIgnoreCase("null")
                && !value.equalsIgnoreCase("") && !value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isValidQRCode(String qrCode) {
        Pattern pattern;
        /** both pattens are works fine */
//        String qrCodePatten = "^[a-zA-Z0-9]+$";
        String qrCodePatten = "\\p{Alnum}*";
        pattern = Pattern.compile(qrCodePatten);
        Matcher matcher = pattern.matcher(qrCode);
        return matcher.matches();
    }
}
