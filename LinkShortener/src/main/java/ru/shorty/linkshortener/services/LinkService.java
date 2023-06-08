package ru.shorty.linkshortener.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shorty.linkshortener.dto.LinkCreateDto;
import ru.shorty.linkshortener.dto.LinkUpdateDto;
import ru.shorty.linkshortener.dto.LinkViewDto;
import ru.shorty.linkshortener.exceptions.LinkDoesNotExistsException;
import ru.shorty.linkshortener.exceptions.LinkRouteRefAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.LinkTitleAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.models.LinkModel;
import ru.shorty.linkshortener.repositories.LinkRepository;

import java.util.*;

@Service
@Transactional
public class LinkService {

    //region Properties && constructor

    private final ModelMapper modelMapper;

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.linkRepository = linkRepository;
    }

    //endregion

    //region Rest methods

    private List<LinkModel> getAll() {
        return linkRepository.findAll(Sort.by(Sort.Direction.DESC, "createDt"));
    }

    public Map<String, List<LinkViewDto>>  getAllDtoCast() {
        List<LinkViewDto> dtosList = getAll().stream()
            .map(this::convertLinkModelToViewDto).toList();
        return Collections.singletonMap("data", dtosList);
    }

    public LinkViewDto getByUid(UUID link_uid) {
        LinkModel model = linkRepository.findByUid(link_uid).orElseThrow(LinkDoesNotExistsException::new);
        return convertLinkModelToViewDto(model);
    }

    public void deleteByUid(UUID link_uid) {
        if (!linkRepository.existsByUid(link_uid))
            throw new LinkDoesNotExistsException();
        linkRepository.deleteByUid(link_uid);
    }

    public Map<String, String> getExternalRefByInner(String innerRef) {
        LinkModel model = linkRepository.findFirstByInnerRef(innerRef).orElseThrow(LinkDoesNotExistsException::new);
        LinkViewDto dtoModel = convertLinkModelToViewDto(model);
        return Collections.singletonMap("externalRef", dtoModel.getExternalRef());
    }

    public void createLink(LinkCreateDto dto) {
        if (linkRepository.existsByTitle(dto.getTitle()))
            throw new LinkRouteRefAlreadyExistsException();
        if (linkRepository.existsByInnerRef(dto.getInnerRef()))
            throw new LinkRouteRefAlreadyExistsException();
        LinkModel model = convertLinkCreateDtoToModel(dto);
        linkRepository.save(model);
    }

    public void updateLink(UUID link_uid, LinkUpdateDto dto) {
        LinkModel model = linkRepository.findByUid(link_uid).orElseThrow(LinkDoesNotExistsException::new);
        if (linkRepository.existsByTitle(dto.getTitle()))
            throw new LinkTitleAlreadyExistsException();
        model.setTitle(dto.getTitle());
        linkRepository.save(model);
    }

    //endregion

    //region Other

    public LinkModel convertLinkCreateDtoToModel(LinkCreateDto dto) {
        return modelMapper.map(dto, LinkModel.class);
    }

    public LinkViewDto convertLinkModelToViewDto(LinkModel model) {
        return modelMapper.map(model, LinkViewDto.class);
    }

    //endregion
}
