package ru.shorty.linkshortener.dto;

import lombok.Getter;
import lombok.Setter;
import ru.shorty.linkshortener.exceptions.LinkDtoNullException;
import ru.shorty.linkshortener.utils.UnsortedUtil;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LinkDto {

    private UUID uid;
    private String ref = "";
    private String title = "";
    private String refRoute = "";
    private Date createDt;
    private Date expirationDt;
    private boolean active;

    //region Getters / Setters

    public String getRefRoute() {
        if (refRoute.isEmpty())
            refRoute = UnsortedUtil.getRandomString(5);
        return refRoute;
    }

    public void setRefRoute(String refRoute) {
        this.refRoute = refRoute.trim();
    }

    public String getTitle() {
        if (!title.isEmpty())
            return title;

        if (getRef().isEmpty())
            throw new LinkDtoNullException();

        try {
            setTitle(UnsortedUtil.getTitleFromUrl(getRef()));
        } catch (IOException exception) {
            throw new LinkDtoNullException();
        }

        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    //endregion
}
