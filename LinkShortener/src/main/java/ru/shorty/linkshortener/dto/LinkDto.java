package ru.shorty.linkshortener.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LinkDto {

    private UUID uid;

    private String ref;

    private String title;

    private Date createDt;

    private Date expirationDt;

    private boolean active;

}
