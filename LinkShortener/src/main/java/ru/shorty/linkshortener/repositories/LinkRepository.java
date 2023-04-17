package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.models.LinkModel;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkModel, UUID> {

    public Boolean existsByUid(UUID uuid);

    public Boolean existsByTitle(String title);

    public Boolean existsByRefRoute(String refRoute);

    public Optional<LinkModel> findByUid(UUID uuid);

    public Optional<LinkModel> findByRefRoute(String refRoute);

    public void deleteByUid(UUID uuid);

}
