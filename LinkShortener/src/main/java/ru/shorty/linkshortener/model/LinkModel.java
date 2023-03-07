package ru.shorty.linkshortener.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "LINKS")
public class LinkModel {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID uid;

    @Column(name="NAME")
    private String Name;

    @Column(name = "CREATED_AT")
    @Nullable()
    private Date createdAt;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name="URL")
    private String url;

    public void setId(UUID uid) {
        this.uid = uid;
    }

    public UUID getId() {
        return uid;
    }

}
