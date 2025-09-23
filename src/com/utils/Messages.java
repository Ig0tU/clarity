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