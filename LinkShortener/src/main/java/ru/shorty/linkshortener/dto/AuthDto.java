package ru.shorty.linkshortener.dto;

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
