package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.shorty.linkshortener.exceptions.TitleIsNullException;

@Getter
@Setter
public class LinkUpdateDto {

    @NotNull
    private String title;

    public void setTitle(String title) {
        title = title.trim();
        this.title = title.substring(0, Math.min(title.length(), 64));
    }

    public String getTitle() {
        if (title.isEmpty()) throw new TitleIsNullException();
        return title;
    }
}
