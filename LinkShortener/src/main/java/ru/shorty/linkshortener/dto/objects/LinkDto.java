package ru.shorty.linkshortener.dto.objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.shorty.linkshortener.dto.rules.ValidationRules;
import ru.shorty.linkshortener.exceptions.ExternalRefIsNotValidException;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.utils.UnsortedUtil;

import java.util.Date;
import java.util.UUID;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDto {

    @Null(groups = {ValidationRules.Create.class, ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.View.class})
    private UUID uid;

    @Null(groups = {ValidationRules.Create.class})
    private UserModel user;

    @NotNull(groups = {ValidationRules.Create.class, ValidationRules.Update.class})
    private String title;

    @Length(max = 10)
    @NotNull(groups = {ValidationRules.Create.class})
    @Null(groups = {ValidationRules.Update.class})
    private String innerRef;

    @Length(max = 250)
    @NotBlank
    @NotNull(groups = {ValidationRules.Create.class})
    @Null(groups = {ValidationRules.Update.class})
    private String externalRef;

    @Null(groups = {ValidationRules.Create.class, ValidationRules.Update.class})
    @NotNull(groups = {ValidationRules.View.class})
    private Date createDt;

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
