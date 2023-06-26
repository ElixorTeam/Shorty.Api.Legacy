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

    public Map<String, List<Map<String, Long>>> getBaseAnalytics(UUID linkUid) {
        LinkModel link = linkRepository.findById(linkUid).orElseThrow(ExternalRefDoesNotExistsException::new);

        Map<String, List<Map<String, Long>>> jsonMap = new HashMap<>();
        List<Map<String, Long>> osType = getGroupJson(redirectRepository.getGroupByOsType(linkUid));
        List<Map<String, Long>> deviceType = getGroupJson(redirectRepository.getGroupByDeviceType(linkUid));
        List<Map<String, Long>> browserType = getGroupJson(redirectRepository.getGroupByBrowserType(linkUid));

        jsonMap.put("os", osType);
        jsonMap.put("devices", deviceType);
        jsonMap.put("browsers", browserType);
        jsonMap.put("views", createViewsMap(linkUid));

        return jsonMap;
    }

    private List<Map<String, Long>> createViewsMap(UUID linkUid) {
        return List.of(
            Map.of("total", redirectRepository.countByLinkUid(linkUid)),
            Map.of("unique", redirectRepository.countUnique(linkUid)),
            Map.of("avg_day", Math.round(redirectRepository.countAvgPerDay(linkUid)))
        );
    }

    private List<Map<String, Long>> getGroupJson(List<Object[]> groups) {
        List<Map<String, Long>> data = new ArrayList<>();
        for (Object[] group : groups) {
            Map<String, Long> readyGroup = new HashMap<>();
            readyGroup.put((String) group[0], (Long) group[1]);
            data.add(readyGroup);
        }
        return data;
    }

}
