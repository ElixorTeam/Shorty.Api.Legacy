package ru.shorty.linkshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shorty.linkshortener.models.LinkRedirectModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RedirectRepository extends JpaRepository<LinkRedirectModel, UUID> {

    Long countByLinkUid(UUID linkUid);

    @Query("SELECT lr.deviceType AS name, COUNT(lr) AS value FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid " +
        "GROUP BY lr.deviceType")
    List<Map<String, Object>> getGroupByDeviceType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.osType AS name, COUNT(lr) AS value FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid " +
        "GROUP BY lr.osType")
    List<Map<String, Object>> getGroupByOsType(@Param("linkUid") UUID linkUid);

    @Query("SELECT lr.browserType AS name, COUNT(lr) AS value FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid " +
        "GROUP BY lr.browserType")
    List<Map<String, Object>>  getGroupByBrowserType(@Param("linkUid") UUID linkUid);
    @Query("SELECT COUNT(DISTINCT lr.clientUid) AS count FROM LinkRedirectModel lr WHERE lr.link.uid = :linkUid " +
        "AND lr.clientUid NOT IN (SELECT lr2.clientUid FROM LinkRedirectModel lr2 WHERE lr2.link.uid = :linkUid " +
        "GROUP BY lr2.clientUid HAVING COUNT(DISTINCT lr2.link.uid) > 1)")
    Long countUnique(@Param("linkUid") UUID linkUid);

    @Query("SELECT COALESCE(AVG(countPerDay), 0) FROM (SELECT COUNT(lr) AS countPerDay FROM LinkRedirectModel lr " +
        "WHERE lr.link.uid = :linkUid GROUP BY FUNCTION('DATE', lr.createDt))")
    Double countAvgPerDay(@Param("linkUid") UUID linkUid);

    @Query("SELECT DATE(l.createDt) AS date, COUNT(l) AS count FROM LinkRedirectModel l WHERE l.link.uid = :linkUid " +
        "AND Date(l.createDt) >= :startDate GROUP BY DATE(l.createDt) ORDER BY DATE(l.createDt) ASC")
    List<Map<String, Object>> getGroupByDays(@Param("linkUid") UUID linkUid, @Param("startDate") LocalDate startDate);

}
