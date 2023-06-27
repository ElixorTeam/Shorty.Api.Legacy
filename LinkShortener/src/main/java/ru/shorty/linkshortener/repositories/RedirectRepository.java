package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shorty.linkshortener.models.LinkRedirectModel;

import java.util.List;
import java.util.UUID;

public interface RedirectRepository extends JpaRepository<LinkRedirectModel, UUID> {

    Long countByLinkUid(UUID linkUid);

    @Query("SELECT lr.deviceType AS deviceType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.deviceType")
    List<Object[]> getGroupByDeviceType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.osType AS osType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.osType")
    List<Object[]> getGroupByOsType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.browserType AS browserType, COUNT(lr) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY lr.browserType")
    List<Object[]> getGroupByBrowserType(@Param("linkUid") UUID linkUid);
    @Query("SELECT COUNT(DISTINCT lr.clientUid) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid AND lr.clientUid NOT IN (SELECT lr2.clientUid FROM LinkRedirectModel lr2 WHERE lr2.link.uid = :linkUid GROUP BY lr2.clientUid HAVING COUNT(DISTINCT lr2.link.uid) > 1)")
    Long countUnique(@Param("linkUid") UUID linkUid);

    @Query("SELECT COALESCE(AVG(countPerDay), 0) FROM (SELECT COUNT(lr) AS countPerDay FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid GROUP BY FUNCTION('DATE', lr.createDt))")
    Double countAvgPerDay(@Param("linkUid") UUID linkUid);


}
