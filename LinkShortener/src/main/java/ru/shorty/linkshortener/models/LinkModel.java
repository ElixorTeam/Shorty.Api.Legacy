package ru.shorty.linkshortener.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "LINKS")
public class LinkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", unique = true)
    private UUID uid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_UID", nullable = false)
    private UserModel user;

    @Column(name = "TITLE", nullable = false, length = 64)
    private String title;

    @Column(name = "INNER_REF", unique = true, nullable = false, length = 10)
    private String innerRef;

    @Column(name = "EXTERNAL_REF", nullable = false, length = 250)
    private String externalRef;

    @CreationTimestamp
    @Column(name = "CREATE_DT", nullable = false)
    private Date createDt;

}
