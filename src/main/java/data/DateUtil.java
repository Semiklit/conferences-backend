package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String formatDate(Date date){
        return dateFormat.format(date);
    }

    public static Date parseDate (String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e){
            e.printStackTrace();
            return new Date();
        }
    }
}
