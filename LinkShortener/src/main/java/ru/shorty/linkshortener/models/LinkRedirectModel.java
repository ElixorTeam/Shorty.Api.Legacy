package ru.shorty.linkshortener.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "LINKS_REDIRECT")
public class LinkRedirectModel {

    @Id
    @UuidGenerator()
    @Column(name = "UUID", unique = true)
    private UUID uid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LINK_UID", nullable = false)
    private LinkModel link;

    @NonNull
    @Column(name = "CLIENT_UID", nullable = false)
    private UUID clientUid;

    @NonNull
    @Column(name = "OS_TYPE", nullable = false, length = 25)
    private String osType;

    @NonNull
    @Column(name = "DEVICE_TYPE", nullable = false, length = 25)
    private String deviceType;

    @NonNull
    @Column(name = "BROWSER_TYPE", nullable = false, length = 25)
    private String browserType;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT", nullable = false)
    private Date createDt;

    @PrePersist
    private void onCreate() {
        createDt = new Date();
    }
}
