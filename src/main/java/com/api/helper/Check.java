package com.api.helper;

public final class Check {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFname(String str) {
        return str.length() >= 1 && str.matches("[a-zA-Z]+");
    }

    public static boolean isLname(String str) {
        return str.length() >= 1 && str.matches("[a-zA-Z ]+");
    }

    public static boolean isValidPassword(String str) {
        return str.length() >= 8;
    }

    public static boolean isValidPhoneNumber(String str) {
        return str.length() >= 8 && str.length() <= 12 && isNumeric(str);
    }

    // public static boolean isValidEmailAddress(String email) {
    //     boolean result = true;
    //     try {
    //        InternetAddress emailAddr = new InternetAddress(email);
    //        emailAddr.validate();
    //     } catch (AddressException ex) {
    //        result = false;
    //     }
    //     return result;
    //  }
}
