package ru.shorty.linkshortener.utils;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.RandomStringUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.UUID;


public class UnsortedUtil {

    public static String getRandomString(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }

    public static String getTitleFromUrl(String url) throws IOException {
        return Jsoup.connect(url).get().title();
    }

    public static UUID getUidFromStringOrEmpty(String uuid) {
        UUID uidEmpty = UUID.fromString("00000000-0000-0000-0000-000000000000");
        if (uuid == null)
            return uidEmpty;
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return uidEmpty;
        }

    }

}
