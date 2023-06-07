package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkUpdateDto {

    @NotNull
    private String title;

    public void setTitle(String title) {
        this.title = title.trim();
    }

}
