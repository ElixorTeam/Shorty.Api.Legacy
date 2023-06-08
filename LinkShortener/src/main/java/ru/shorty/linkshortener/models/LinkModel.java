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

    @NonNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @NonNull
    @Column(name = "INNER_REF", unique = true, nullable = false)
    private String innerRef;

    @NonNull
    @Column(name = "EXTERNAL_REF", nullable = false)
    private String externalRef;


    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT", nullable = false)
    private Date createDt;

    @PrePersist
    private void onCreate() {
        createDt = new Date();
    }

}
