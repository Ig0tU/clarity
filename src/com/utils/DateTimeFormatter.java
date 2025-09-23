package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.GregorianCalendar;

/**
 * Utility class for formatting dates and times according to the current locale.
 * Provides consistent date/time formatting throughout the application.
 *
 * @author Daniel Batres
 * @version 1.0.0
 * @since 2024
 */
public class DateTimeFormatter {

    // Standard date format patterns for English locale
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final String DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm";
    private static final String TIME_PATTERN = "HH:mm";
    private static final String FULL_DATE_PATTERN = "EEEE, MMMM dd, yyyy";
    private static final String MONTH_YEAR_PATTERN = "MMMM yyyy";
    private static final String SHORT_DATE_PATTERN = "MM/dd/yy";

    /**
     * Formats a date using the default date pattern (MM/dd/yyyy)
     *
     * @param date the date to format
     * @return formatted date string
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats a date and time using the default datetime pattern
     *
     * @param date the date to format
     * @return formatted datetime string
     */
    public static String formatDateTime(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats just the time portion of a date
     *
     * @param date the date to format
     * @return formatted time string
     */
    public static String formatTime(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats a date with full day and month names (e.g., "Monday, January 15, 2024")
     *
     * @param date the date to format
     * @return formatted full date string
     */
    public static String formatFullDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(FULL_DATE_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats a date showing only month and year (e.g., "January 2024")
     *
     * @param date the date to format
     * @return formatted month-year string
     */
    public static String formatMonthYear(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(MONTH_YEAR_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats a date using short format (MM/dd/yy)
     *
     * @param date the date to format
     * @return formatted short date string
     */
    public static String formatShortDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(SHORT_DATE_PATTERN, Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Formats the current date and time
     *
     * @return formatted current datetime string
     */
    public static String formatCurrentDateTime() {
        return formatDateTime(new Date());
    }

    /**
     * Formats the current date
     *
     * @return formatted current date string
     */
    public static String formatCurrentDate() {
        return formatDate(new Date());
    }

    /**
     * Formats the current time
     *
     * @return formatted current time string
     */
    public static String formatCurrentTime() {
        return formatTime(new Date());
    }

    /**
     * Creates a date from day, month, year integers
     *
     * @param day day of month (1-31)
     * @param month month (1-12)
     * @param year full year (e.g., 2024)
     * @return Date object or null if invalid
     */
    public static Date createDate(int day, int month, int year) {
        try {
            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            calendar.setLenient(false); // Strict date validation
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates a date with time from integers
     *
     * @param day day of month (1-31)
     * @param month month (1-12)
     * @param year full year (e.g., 2024)
     * @param hour hour (0-23)
     * @param minute minute (0-59)
     * @return Date object or null if invalid
     */
    public static Date createDateTime(int day, int month, int year, int hour, int minute) {
        try {
            Calendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute);
            calendar.setLenient(false); // Strict date validation
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the localized name of a month
     *
     * @param month month number (1-12)
     * @return localized month name
     */
    public static String getMonthName(int month) {
        if (month < 1 || month > 12) return "";

        String[] monthKeys = {
            "month.january", "month.february", "month.march", "month.april",
            "month.may", "month.june", "month.july", "month.august",
            "month.september", "month.october", "month.november", "month.december"
        };

        return Messages.get(monthKeys[month - 1]);
    }

    /**
     * Gets the localized short name of a month
     *
     * @param month month number (1-12)
     * @return localized short month name
     */
    public static String getShortMonthName(int month) {
        if (month < 1 || month > 12) return "";

        String[] monthKeys = {
            "month.short.jan", "month.short.feb", "month.short.mar", "month.short.apr",
            "month.short.may", "month.short.jun", "month.short.jul", "month.short.aug",
            "month.short.sep", "month.short.oct", "month.short.nov", "month.short.dec"
        };

        return Messages.get(monthKeys[month - 1]);
    }

    /**
     * Gets the localized name of a day of the week
     *
     * @param dayOfWeek day of week (1=Sunday, 2=Monday, ..., 7=Saturday)
     * @return localized day name
     */
    public static String getDayName(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) return "";

        String[] dayKeys = {
            "day.sunday", "day.monday", "day.tuesday", "day.wednesday",
            "day.thursday", "day.friday", "day.saturday"
        };

        return Messages.get(dayKeys[dayOfWeek - 1]);
    }

    /**
     * Validates if a date string matches the expected format
     *
     * @param dateString the date string to validate
     * @param pattern the expected pattern
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString, String pattern) {
        if (dateString == null || dateString.trim().isEmpty()) return false;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
            formatter.setLenient(false);
            formatter.parse(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates if a date string matches the default date format
     *
     * @param dateString the date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        return isValidDate(dateString, DATE_PATTERN);
    }

    /**
     * Gets a relative time description (e.g., "Today", "Yesterday", "2 days ago")
     *
     * @param date the date to compare
     * @return relative time description
     */
    public static String getRelativeTime(Date date) {
        if (date == null) return "";

        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        cal.setTime(date);

        // Check if same day
        if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return Messages.get("date.hoy");
        }

        // Check if yesterday
        today.add(Calendar.DAY_OF_YEAR, -1);
        if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return Messages.get("date.ayer");
        }

        // Check if tomorrow
        today.add(Calendar.DAY_OF_YEAR, 2);
        if (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return Messages.get("date.manana");
        }

        // Otherwise return formatted date
        return formatDate(date);
    }
}