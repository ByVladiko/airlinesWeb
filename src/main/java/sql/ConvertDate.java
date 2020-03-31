package sql;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {

    private ConvertDate(){}

    public static java.sql.Date convert(Date date) {
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        df.format(date);
        return new java.sql.Date(date.getTime());
    }

    public static Date convert(java.sql.Date date) {
        return new Date(date.getTime());
    }
}
