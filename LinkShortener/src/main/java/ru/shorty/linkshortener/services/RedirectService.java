package ru.shorty.linkshortener.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.models.LinkRedirectModel;
import ru.shorty.linkshortener.properties.AppProperties;
import ru.shorty.linkshortener.repositories.LinkRepository;
import ru.shorty.linkshortener.repositories.RedirectRepository;
import ru.shorty.linkshortener.utils.UnsortedUtil;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RedirectService {
    private final LinkRepository linkRepository;
    private final RedirectRepository redirectRepository;
    private final AppProperties appProperties;
    public RedirectService(LinkRepository linkRepository,
                           RedirectRepository redirectRepository, AppProperties appProperties) {
        this.linkRepository = linkRepository;
        this.redirectRepository = redirectRepository;
        this.appProperties = appProperties;
    }

    public Map<String, String> getExternalRefByInner(String innerRef, String userAgent, String clientHeader) {
        LinkModel linkModel = linkRepository.findFirstByInnerRef(innerRef).orElseThrow(ExternalRefDoesNotExistsException::new);

        UUID clientUid = UnsortedUtil.getUidFromStringOrEmpty(clientHeader);

        Optional<LinkRedirectModel> redirectModel = redirectRepository.findByClientUidAndLinkUid(linkModel.getUid(),
            clientUid);

        if (redirectModel.isEmpty()) {
            Client c = new Parser().parse(userAgent);
            LinkRedirectModel linkRedirect = new LinkRedirectModel();

            linkRedirect.setLink(linkModel);
            linkRedirect.setClientUid(clientUid);

            linkRedirect.setOsType(c.os.family);
            linkRedirect.setDeviceType(c.device.family);
            linkRedirect.setBrowserType(c.userAgent.family);

            redirectRepository.save(linkRedirect);
        }

        String external_url = appProperties.getFrontRedirectUrl() + "/" + linkModel.getInnerRef();

        return Collections.singletonMap("externalRef", external_url);
    }
}
