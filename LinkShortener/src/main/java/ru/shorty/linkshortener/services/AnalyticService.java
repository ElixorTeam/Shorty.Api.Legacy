package ru.shorty.linkshortener.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.models.LinkRedirectModel;
import ru.shorty.linkshortener.repositories.LinkRepository;
import ru.shorty.linkshortener.repositories.RedirectRepository;
import ru.shorty.linkshortener.utils.UnsortedUtil;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.*;

@Service
@Transactional
public class AnalyticService {
    private final LinkRepository linkRepository;
    private final RedirectRepository redirectRepository;

    public AnalyticService(LinkRepository linkRepository,
                           RedirectRepository redirectRepository) {
        this.linkRepository = linkRepository;
        this.redirectRepository = redirectRepository;
    }

    public Map<String, String> getExternalRefByInner(String innerRef, String userAgent, String clientHeader) {
        LinkModel linkModel = linkRepository.findFirstByInnerRef(innerRef).orElseThrow(ExternalRefDoesNotExistsException::new);

        UUID clientUid = UnsortedUtil.getUidFromStringOrEmpty(clientHeader);

        Client c = new Parser().parse(userAgent);
        LinkRedirectModel linkRedirect = new LinkRedirectModel();

        linkRedirect.setLink(linkModel);
        linkRedirect.setClientUid(clientUid);

        linkRedirect.setOsType(c.os.family);
        linkRedirect.setDeviceType(c.device.family);
        linkRedirect.setBrowserType(c.userAgent.family);

        redirectRepository.save(linkRedirect);

        return Collections.singletonMap("externalRef", linkModel.getExternalRef());
    }

    public Map<String, Map<String, Long>> getBaseAnalytics(UUID linkUid) {
        LinkModel linkModel = linkRepository.findById(linkUid).orElseThrow(ExternalRefDoesNotExistsException::new);
        Map<String, Map<String, Long>> jsonMap = new HashMap<>();
        Map<String, Long> osType = getGroupJson(
            redirectRepository.getGroupByOsType(linkUid)
        );
        Map<String, Long> deviceType = getGroupJson(
            redirectRepository.getGroupByDeviceType(linkUid)
        );
        Map<String, Long> browserType = getGroupJson(
            redirectRepository.getGroupByBrowserType(linkUid)
        );

        Map<String, Long> views = new HashMap<>();
        views.put("total", redirectRepository.countByLinkUid(linkUid));
        views.put("unique", redirectRepository.countUnique(linkUid));
        views.put("avg_day", Math.round(redirectRepository.countAvgPerDay(linkUid)));


        jsonMap.put("os", osType);
        jsonMap.put("devices", deviceType);
        jsonMap.put("browsers", browserType);
        jsonMap.put("views", views);
        return jsonMap;
    }

    private Map<String, Long> getGroupJson(List<Object[]> groups) {
        Map<String, Long> readyGroups = new HashMap<>();
        for (Object[] group : groups)
            readyGroups.put((String) group[0], (Long) group[1]);
        return readyGroups;
    }

}
