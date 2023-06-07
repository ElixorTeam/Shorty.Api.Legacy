package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.shorty.linkshortener.exceptions.LinkDtoNullException;
import ru.shorty.linkshortener.utils.UnsortedUtil;

@Getter
@Setter
public class LinkCreateDto {

    private String title = "";

    @NotBlank
    private String innerRef = "";

    @NotBlank
    private String externalRef = "";

    //region Getters / Setters

    public String getInnerRef() {
        if (innerRef.isEmpty())
            innerRef = UnsortedUtil.getRandomString(5);
        return innerRef;
    }

    public void setInnerRef(String innerRef) {
        this.innerRef = innerRef.trim();
    }

    public String getTitle() {
        if (!title.isEmpty())
            return title;

        if (getExternalRef().isEmpty())
            throw new LinkDtoNullException();

        try {
            setTitle(UnsortedUtil.getTitleFromUrl(getExternalRef()));
            return title;
        } catch (Exception exception) {
            //IOException
            throw new LinkDtoNullException();
        }

    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    //endregion
}
