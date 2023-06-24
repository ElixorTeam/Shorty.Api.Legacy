package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.models.UserModel;
import ru.shorty.linkshortener.oauth2.providers.common.AuthProvider;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUid(UUID uid);
    Optional<UserModel> findByProviderIdAndProvider(String providerId, AuthProvider provider);
}
