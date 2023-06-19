package ru.shorty.linkshortener.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import ru.shorty.linkshortener.oauth2.providers.common.AuthProvider;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "USERS", schema = "dbo", uniqueConstraints = {@UniqueConstraint(columnNames = "EMAIL")})
public class UserModel {

    @Id
    @UuidGenerator()
    @Column(name = "UUID", unique = true)
    private UUID uid;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 75)
    private String name;

    @Email
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @NotNull
    @Column(name = "PROVIDER_ID", length = 50)
    private String providerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", length = 50)
    private AuthProvider provider;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DT", nullable = false)
    private Date createDt;

    @PrePersist
    private void onCreate() {
        createDt = new Date();
    }
}
