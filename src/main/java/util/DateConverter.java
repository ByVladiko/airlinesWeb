package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateConverter() {
    }

    public static String convert(Date date) {
        return dateFormat.format(date);
    }

    public static Date convert(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
