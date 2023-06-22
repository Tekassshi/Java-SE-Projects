package util;

import data.Color;
import data.Country;

import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

/** Static methods container for formatting data relying on country code or resource bundle
 * **/
public class CellDataFormatter {
    public static String formatLong(String countryCode, Long value){
        Locale locale = new Locale(countryCode);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.format(value);
    }

    public static String formatInteger(String countryCode, Integer value){
        Locale locale = new Locale(countryCode);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.format(value);
    }

    public static String formatFloat(String countryCode, Float value){
        Locale locale = new Locale(countryCode);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.format(value);
    }

    public static String formatDouble(String countryCode, Double value){
        Locale locale = new Locale(countryCode);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.format(value);
    }

    public static String formatZonedDateTime(String countryCode, ZonedDateTime value){
        Locale locale = new Locale(countryCode);
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(locale);
        return value.format(f);
    }

    public static String formatEnum(ResourceBundle bundle, Enum value){
        return bundle.getString(String.valueOf(value));
    }
}
