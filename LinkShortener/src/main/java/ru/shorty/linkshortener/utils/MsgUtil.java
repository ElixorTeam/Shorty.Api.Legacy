package ru.shorty.linkshortener.utils;

import java.util.Map;

public class MsgUtil {

    public static Map<String, String> createSuccess(String message) {
        return Map.of("msg", message);
    }

    public static Map<String, String> createError(String message) {
        return Map.of("error", message);
    }

    public static Map<String, String> getSuccess() {
        return createSuccess("Success");
    }

}
