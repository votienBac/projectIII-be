package vn.noron.utils.time;

import java.text.Format;
import java.text.SimpleDateFormat;

public abstract class DateToString {
    public static <T> String convert(T date){
        Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String str = formatter.format(date);
        return str;
    }
}
