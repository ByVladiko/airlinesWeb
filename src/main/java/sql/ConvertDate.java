package sql;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    private ConvertDate(){}

    public static String convert(Date date) {
        DateFormat sqlFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        return sqlFormat.format(date);
    }

    public static Date convert(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
