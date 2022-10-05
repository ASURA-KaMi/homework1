package ru.liga.internship.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;



public class DateUtils {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    private static final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d.M.yyyy");

    public static Date dateFromString(String date) {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValid(String dateInString){
        try {
            LocalDate.parse(dateInString, inputFormat);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }
    public static LocalDate localDateFromString(String date) {
        return LocalDate.parse(date, dateTimeFormatter);
    }

    public static String outputStringFromLocalDate(LocalDate date) {
        return date.format(outputFormat);
    }
    public static String stringFromLocalDate(LocalDate date) {
        return date.format(dateTimeFormatter);
    }

    public static LocalDate dateFromInputString(String date){
        return LocalDate.parse(date, inputFormat);
    }
}
