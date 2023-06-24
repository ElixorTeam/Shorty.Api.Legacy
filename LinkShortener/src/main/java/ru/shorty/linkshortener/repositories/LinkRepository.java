package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.models.LinkModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkModel, UUID> {

    Boolean existsByUidAndUserUid(UUID uuid, UUID userUid);

    Boolean existsByInnerRef(String innerRef);

    List<LinkModel> findAllByUserUidOrderByCreateDtDesc(UUID userUid);

    Optional<LinkModel> findByUidAndUserUid(UUID uuid, UUID userUid);

    Optional<LinkModel> findFirstByInnerRef(String innerRef);

    void deleteByUidAndUserUid(UUID uuid, UUID userUid);

}
