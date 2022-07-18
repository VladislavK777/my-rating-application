package ru.myrating.application.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,10}$";
    public static final String UPPER_CYRILLIC_LITTERS = "^[А-Я]*$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "ru";
    public static final String WEBSOCKET_QUEUE = "/topic/result";

    private Constants() {}
}
