package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shorty.linkshortener.models.LinkRedirectModel;

import java.util.List;
import java.util.UUID;

public interface RedirectRepository extends JpaRepository<LinkRedirectModel, UUID> {

    @Query("SELECT lr.deviceType AS deviceType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.deviceType")
    List<Object[]> getGroupByDeviceType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.osType AS osType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.osType")
    List<Object[]> getGroupByOsType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.browserType AS browserType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.browserType")
    List<Object[]> getGroupByBrowserType(@Param("linkUid") UUID linkUid);

}
