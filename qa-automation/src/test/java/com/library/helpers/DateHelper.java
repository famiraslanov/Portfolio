package com.library.helpers;

import com.library.Find;
import com.library.Verify;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DateHelper
{
    public static String dtInsert()
    {
        return dtInsert("yyyyMMddHHmmss");
    }

    public static String dtInsert(String pattern)
    {
        return DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.of("UTC"))
                .format(Instant.now());
    }

    public static String toFormat(Date date, String pattern)
    {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String dateInsertAsChars()
    {
        String startString = dtInsert();
        String[] returnArray = new String[startString.length()];
        for (int i = 0; i < startString.length(); i++) {
            returnArray[i] = String.valueOf((char) ((int) (startString.charAt(i)) + 65));
        }

        return String.join("", returnArray);
    }

    public static Date getTodayDate()
    {
        return Calendar.getInstance().getTime();
    }

    public static Date changeDate(Date date, int year, int month, int day)
    {
        Calendar cal = getCalendarInstance(date);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static int getPreviousYear()
    {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        return prevYear.get(Calendar.YEAR);
    }

    public static String getYear(Date date)
    {
        return String.valueOf(getCalendarInstance(date).get(Calendar.YEAR));
    }

    public static String getMonth(Date date)
    {
        return getCalendarInstance(date).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }

    public static Date addMillis(Date date, int milliseconds)
    {
        Calendar calendar = getCalendarInstance(date);
        calendar.add(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }

    private static Calendar getCalendarInstance(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static void verifyThatSortedByDateDesc(By elementsLocators)
    {
        List<String> dates = Find.getTexts(elementsLocators);
        List<LocalDate> parsedDatesFromTheUI = dates.stream()
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)))
                .collect(Collectors.toList());

        List<LocalDate> sortedByDate = parsedDatesFromTheUI.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Verify.isTrue(parsedDatesFromTheUI.equals(sortedByDate), "Column is not sorted by DESC");
    }
}
