package ru.shorty.linkshortener.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS", uniqueConstraints  = {@UniqueConstraint(columnNames = "EMAIL")})
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Email
    @NotNull
    @Column(name = "EMAIL", nullable = false)
    private String email;
}
