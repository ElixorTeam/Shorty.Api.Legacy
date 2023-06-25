package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.models.LinkRedirectModel;

import java.util.Optional;
import java.util.UUID;

public interface RedirectRepository extends JpaRepository<LinkRedirectModel, UUID> {
    Optional<LinkRedirectModel> findByClientUidAndLinkUid(UUID clientUid, UUID LinkUid);

}
