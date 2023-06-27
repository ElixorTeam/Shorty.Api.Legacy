package ru.shorty.linkshortener.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.shorty.linkshortener.oauth2.providers.common.AuthProvider;

import java.util.Date;
import java.util.UUID;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "USERS", uniqueConstraints = {
    @UniqueConstraint(name = "UQ_USER_PROVIDERS", columnNames = {"PROVIDER", "PROVIDER_ID"})
})
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID", unique = true)
    UUID uid;

    @Column(name = "NAME", nullable = false, length = 75)
    String name;

    @Email
    @Column(name = "EMAIL", length = 50)
    String email;

    @Column(name = "PROVIDER_ID", length = 50)
    String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", length = 50)
    AuthProvider provider;

    @CreationTimestamp
    @Column(name = "CREATE_DT", nullable = false)
    Date createDt;

}
