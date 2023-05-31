package ru.shorty.linkshortener.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.jsoup.Jsoup;

import java.io.IOException;


public class UnsortedUtil {

    public static String getRandomString(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }

    public static String getTitleFromUrl(String url) throws IOException {
        return Jsoup.connect(url).get().title();
    }

}
