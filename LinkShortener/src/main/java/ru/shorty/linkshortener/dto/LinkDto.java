package ru.shorty.linkshortener.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LinkDto {

    private UUID uid;

    private String ref;

    private String refRoute;

    private String title;

    private Date createDt;

    private Date expirationDt;

    private boolean active;

    public String getRefRoute() {
        if (refRoute != null && !refRoute.equals(""))
            return refRoute;
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
