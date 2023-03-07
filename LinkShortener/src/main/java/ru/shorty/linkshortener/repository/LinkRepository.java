package ru.shorty.linkshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shorty.linkshortener.model.LinkModel;

import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkModel, UUID> {

}
