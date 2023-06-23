package ru.shorty.linkshortener.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LinkUpdateDto {

    @NotNull
    private String title;

    public void setTitle(String title) {
        title = title.trim();
        this.title = title.substring(0, Math.min(title.length(), 64));
    }

}
