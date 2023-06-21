package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LinkUpdateDto {

    @NotNull
    private String title;

    public void setTitle(String title) {
        this.title = title.trim();
    }

}
