package ru.shorty.linkshortener.dto.objects;

import lombok.Data;

@Data
public class AuthDto {
    private String accessToken;
    private String tokenType;

    public AuthDto(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer ";
    }
}
