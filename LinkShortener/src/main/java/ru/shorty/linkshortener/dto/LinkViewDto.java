package ru.shorty.linkshortener.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LinkViewDto extends LinkCreateDto {

    private UUID uid;

    private Date createDt;

}
