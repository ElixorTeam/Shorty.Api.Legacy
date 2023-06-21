package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.shorty.linkshortener.exceptions.DefaultTitleCanNotSetException;
import ru.shorty.linkshortener.exceptions.ExternalRefIsNotValidException;
import ru.shorty.linkshortener.utils.UnsortedUtil;

@Data
public class LinkCreateDto {

    private String title = "";

    @Size(max = 10)
    private String innerRef = "";

    @Size(max = 100)
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
            throw new ExternalRefIsNotValidException();

        try {
            setTitle(UnsortedUtil.getTitleFromUrl(getExternalRef()));
            return title;
        } catch (Exception exception) {
            throw new DefaultTitleCanNotSetException();
        }

    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    //endregion
}
