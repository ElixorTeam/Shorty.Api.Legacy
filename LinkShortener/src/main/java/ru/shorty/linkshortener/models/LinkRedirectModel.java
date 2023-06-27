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
@Table(name = "LINKS_REDIRECT")
public class LinkRedirectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", unique = true)
    UUID uid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LINK_UID", nullable = false)
    LinkModel link;

    @Column(name = "CLIENT_UID", nullable = false)
    UUID clientUid;

    @Column(name = "OS_TYPE", nullable = false, length = 25)
    String osType;

    @Column(name = "DEVICE_TYPE", nullable = false, length = 25)
    String deviceType;

    @Column(name = "BROWSER_TYPE", nullable = false, length = 25)
    String browserType;

    @CreationTimestamp
    @Column(name = "CREATE_DT", nullable = false)
    Date createDt;

}
