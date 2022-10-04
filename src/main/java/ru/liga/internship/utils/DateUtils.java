package ru.liga.internship.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


public class DateUtils {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy");

    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");

    public static Date dateFromString(String date) {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate localDateFromString(String date) {
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public static String stringFromLocalDate(LocalDate date) {
        return date.format(outputFormat);
    }
}
