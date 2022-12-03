package vn.noron.utils;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;

public class DecimalConverter {
    public static String base36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encodeBase36(Long id) {
        Long d = id;
        StringBuilder hex = new StringBuilder();
        long digit = d % 36;
        hex.insert(0, base36.charAt((int) digit));
        d = d / 36;
        do {
            digit = d % 36;
            d = d / 36;
            hex.insert(0, base36.charAt((int) digit));
        } while (d != 0);
        return hex.toString().toLowerCase();
    }

    public static Long decodeBase36(String s) {
        s = s.toUpperCase();
        long val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = base36.indexOf(c);
            val = 36 * val + d;
        }
        return val;
    }

    public static String normalPostId(String objectId) {
        if (objectId != null && !(isDigits(objectId) && objectId.length() > 8)) {
            return decodeBase36(objectId).toString();
        } else {
            return objectId;
        }
    }
}
