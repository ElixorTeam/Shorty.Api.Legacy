package ru.shorty.linkshortener;

import java.util.Map;

public class MsgUtil {

    public static Map<String, String> create(String message) {
        return Map.of("msg", message);
    }

    public static Map<String, String> success() {
        return create("Success");
    }

}
