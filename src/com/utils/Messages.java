package com.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * Utility class for managing internationalization messages.
 * Provides access to localized text resources throughout the application.
 *
 * @author Daniel Batres
 * @version 1.0.0
 * @since 2024
 */
public class Messages {

    private static final String BUNDLE_NAME = "resources.messages";
    private static ResourceBundle resourceBundle;

    static {
        // Set default locale to English
        Locale.setDefault(Locale.ENGLISH);
        loadBundle();
    }

    /**
     * Loads the resource bundle for the current locale
     */
    private static void loadBundle() {
        try {
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
        } catch (MissingResourceException e) {
            System.err.println("Warning: Could not load resource bundle: " + BUNDLE_NAME);
            System.err.println("Falling back to English locale");
            try {
                resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
            } catch (MissingResourceException ex) {
                System.err.println("Error: Could not load any resource bundle for messages");
                resourceBundle = null;
            }
        }
    }

    /**
     * Gets a localized message for the given key
     *
     * @param key the message key
     * @return the localized message or the key itself if not found
     */
    public static String get(String key) {
        if (resourceBundle == null) {
            return key;
        }

        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("Warning: Missing message key: " + key);
            return key;
        }
    }

    /**
     * Gets a localized message for the given key with parameter substitution
     *
     * @param key the message key
     * @param params parameters to substitute in the message
     * @return the localized message with parameters substituted
     */
    public static String get(String key, Object... params) {
        String message = get(key);

        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                message = message.replace("{" + i + "}", String.valueOf(params[i]));
            }
        }

        return message;
    }

    /**
     * Sets the application locale and reloads the resource bundle
     *
     * @param locale the new locale to use
     */
    public static void setLocale(Locale locale) {
        Locale.setDefault(locale);
        loadBundle();
    }

    /**
     * Gets the current application locale
     *
     * @return the current locale
     */
    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    /**
     * Gets a localized message with proper plural form handling
     *
     * @param key the base message key
     * @param count the count to determine singular/plural form
     * @param params additional parameters to substitute in the message
     * @return the localized message with proper plural form and parameters substituted
     */
    public static String getPlural(String key, int count, Object... params) {
        String pluralKey = key;

        // Determine plural form based on English rules
        if (count == 1) {
            pluralKey = key + ".singular";
        } else {
            pluralKey = key + ".plural";
        }

        // Try to get the plural-specific key first
        String message;
        if (hasKey(pluralKey)) {
            message = get(pluralKey);
        } else {
            // Fallback to base key if plural-specific key doesn't exist
            message = get(key);
        }

        // Create parameters array with count as first parameter
        Object[] allParams = new Object[params.length + 1];
        allParams[0] = count;
        System.arraycopy(params, 0, allParams, 1, params.length);

        // Substitute parameters
        for (int i = 0; i < allParams.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(allParams[i]));
        }

        return message;
    }

    /**
     * Gets a localized message for counts with automatic singular/plural handling
     *
     * @param key the base message key (without .singular/.plural suffix)
     * @param count the count to display and use for plural determination
     * @return the localized message with count and proper plural form
     */
    public static String getCount(String key, int count) {
        return getPlural(key, count);
    }

    /**
     * Gets a formatted message for displaying counts of items
     *
     * @param itemKey the key for the item name
     * @param count the count of items
     * @return formatted message like "5 patients" or "1 patient"
     */
    public static String getItemCount(String itemKey, int count) {
        String itemName = get(itemKey);
        if (count == 1) {
            return count + " " + itemName;
        } else {
            // Simple English pluralization - add 's' if not already plural
            if (!itemName.endsWith("s")) {
                itemName += "s";
            }
            return count + " " + itemName;
        }
    }

    /**
     * Checks if a message key exists in the resource bundle
     *
     * @param key the message key to check
     * @return true if the key exists, false otherwise
     */
    public static boolean hasKey(String key) {
        if (resourceBundle == null) {
            return false;
        }

        try {
            resourceBundle.getString(key);
            return true;
        } catch (MissingResourceException e) {
            return false;
        }
    }
}