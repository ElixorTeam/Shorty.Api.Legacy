package ru.shorty.linkshortener.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.jsoup.Jsoup;
import ru.shorty.linkshortener.exceptions.LinkDtoNullException;

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
        if (refRoute != null && !refRoute.trim().isEmpty())
            return refRoute;
        refRoute = RandomStringUtils.randomAlphanumeric(5);
        return refRoute;
    }

    public String getTitle() {
        if (title != null && !title.trim().isEmpty())
            return title;
        if (getRef() == null)
            throw new LinkDtoNullException();
        try {
            setTitle(Jsoup.connect(getRef()).get().title());
        } catch (Exception exception) {
            throw new LinkDtoNullException();
        }
        return title;
    }
}
