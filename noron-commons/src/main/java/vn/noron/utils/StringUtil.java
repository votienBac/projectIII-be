package vn.noron.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {


    public static String removeAccent(String s) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(s)) return "";
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("")
                .replaceAll("đ", "d")
                .replaceAll("Đ", "D")
                .replaceAll("`", "")
                .replaceAll("´", "")
                .replaceAll("\\^", "");
    }
    public static String concatListStringToStringWithAccent(List<String> strings, String delimiter) {
        if (strings.isEmpty()) {
            return null;
        } else {

            return String.join(delimiter, strings);
        }
    }

    public static List<String> splitStringToListStringWithAccent(String string, String delimiter) {
        if (string.isEmpty()) {
            return new ArrayList<>();
        } else {

            return Stream.of(string.split(delimiter)).collect(Collectors.toList());

        }
    }

}
