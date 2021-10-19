package cl.ciisa.todoapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String DATE_STRING_FORMAT = "%d-%02d-%02d";
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static Date unsafeParse(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date subDays(Date date, int days) {
       return addDays(date, -days);
    }
}
