package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.models.LinkModel;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkModel, UUID> {

    Boolean existsByUid(UUID uuid);

    Boolean existsByTitle(String title);

    Boolean existsByInnerRef(String innerRef);

    Optional<LinkModel> findByUid(UUID uuid);

    Optional<LinkModel> findFirstByInnerRef(String innerRef);

    void deleteByUid(UUID uuid);

}
