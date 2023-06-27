package ru.shorty.linkshortener.dto.common;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.shorty.linkshortener.dto.rules.ValidationRules;
import ru.shorty.linkshortener.dto.rules.ViewAccess;
import ru.shorty.linkshortener.exceptions.common.ExternalRefIsNotValidException;
import ru.shorty.linkshortener.utils.UnsortedUtil;

import java.util.Date;
import java.util.UUID;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    @JsonView(value = ViewAccess.View.class)
    @Null(groups = {ValidationRules.Create.class, ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.View.class})
    UUID uid;

    @JsonView(value = ViewAccess.Create.class)
    @Length(max = 64)
    @NotNull
    String title;

    @JsonView(value = ViewAccess.Create.class)
    @Length(max = 10)
    @Null(groups = {ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.Create.class, ValidationRules.View.class})
    String innerRef;

    @JsonView(value = ViewAccess.Create.class)
    @Length(max = 250)
    @NotBlank
    @Null(groups = {ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.Create.class, ValidationRules.View.class})
    String externalRef;

    @JsonView(value = ViewAccess.View.class)
    @Null(groups = {ValidationRules.Create.class, ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.View.class})
    Date createDt;

    public String getInnerRef() {
        if (innerRef.isEmpty())
            innerRef = UnsortedUtil.getRandomString(5);
        return innerRef;
    }


    public String getTitle() {
        if (!title.isEmpty())
            return title;

        if (getExternalRef().isEmpty())
            throw new ExternalRefIsNotValidException();

        try {
            setTitle(UnsortedUtil.getTitleFromUrl(getExternalRef()));
        } catch (Exception exception) {
            setTitle(getInnerRef());
        }
        return title;
    }


    public void setTitle(String title) {
        title = title.trim();
        this.title = title.substring(0, Math.min(title.length(), 64));
    }

}
