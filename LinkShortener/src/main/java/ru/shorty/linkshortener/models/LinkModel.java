package ru.shorty.linkshortener.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "LINKS")
public class LinkModel {

    @Id
    @UuidGenerator()
    @Column(name = "UUID", unique = true)
    private UUID uid;

    @Column(name = "TITLE", unique = true, nullable = false)
    private String title;

    @NonNull
    @Column(name = "REF", nullable = false)
    private String ref;

    @NonNull
    @Column(name = "REF_ROUTE", unique = true, nullable = false)
    private String refRoute;

    @Column(name = "IS_ACTIVE")
    private boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT", nullable = false)
    private Date createDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DT")
    private Date expirationDt;

    @PrePersist
    private void onCreate() {
        createDt = new Date();
    }

}
